/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.teleinfo.bidadmin.soybean.wxfront;

import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/usergroup")
@Api(value = "", tags = "微信用户群组接口")
public class WxUserGroupController extends BladeController {

	private IUserGroupService userGroupService;

	private IGroupService groupService;

	private IGroupLogService groupLogService;

	/**
	* 用户加群
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "用户加群", notes = "传入userGroup")
	public R save(@Valid @RequestBody UserGroup userGroup) {
		try {
			return R.status(userGroupService.saveUserGroup(userGroup));
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

	/**
	 * 用户退群
	 */
	@PostMapping("/quit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "用户退群", notes = "传入用户ID和群ID")
	public R remove(@Valid @RequestBody UserGroup userGroup) {
		try {
			return R.status(userGroupService.quitGroup(userGroup));
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}


	/**
	 * 管理员删除群用户
	 */
	@PostMapping("/manager/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "群管理员踢除用户", notes = "传入用户ID，群组ID，操作人ID")
	public R save(@Valid @RequestBody UserGroupVO userGroup) {
		try {

			//校验改群及其子群下是否有此用户
			List<Integer> groupUserIds = groupService.selectUserIdByParentId(userGroup.getGroupId());
			if (!groupUserIds.contains(userGroup.getManagerId())) {
				throw new ApiException("改组织下未发现ID等于" + userGroup.getManagerId() + "的用户");
			}
			//获取用户是管理员的群
			LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().eq(Group::getStatus, Group.NORMAL);
			List<Group> managerGroups = groupService.list(queryWrapper);
			managerGroups.removeIf(group -> {
				String managers = group.getManagers();
				if (Func.toIntList(managers).contains(userGroup.getUserId())) {
					return false;
				}
				Integer createUser = group.getCreateUser();
				if (createUser != null && createUser.equals(userGroup.getUserId())) {
					return false;
				}
				return true;
			});
			//获取用户管理的所有用户
			ArrayList<Integer> managerUserIds = new ArrayList<>();
			for (Group managerGroup : managerGroups) {
				List<Integer> userIds = groupService.selectUserIdByParentId(managerGroup.getId());
				managerUserIds.addAll(userIds);
			}
			//校验此用户是否有权限设置管理员
			if (managerUserIds.contains(userGroup.getUserId())) {
				// 删除
				userGroup.setStatus(UserGroup.DELETE);
				boolean flag = userGroupService.updateById(userGroup);
				if (flag) {
					Group group = groupService.getById(userGroup.getGroupId());
					int count = group.getUserAccount();
					count--;
					group.setUserAccount(count);
					return R.status(groupService.updateById(group));
				} else {
					return R.fail("群人数减1失败");
				}
			} else {
				// 不能删除
				return R.fail("没有删除权限");
			}

		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

}
