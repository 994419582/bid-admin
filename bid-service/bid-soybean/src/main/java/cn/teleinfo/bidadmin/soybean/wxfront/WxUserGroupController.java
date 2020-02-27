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

import cn.teleinfo.bidadmin.soybean.entity.GroupLog;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
import cn.teleinfo.bidadmin.soybean.wrapper.UserGroupWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/usergroup")
@Api(value = "", tags = "用户群组接口")
public class WxUserGroupController extends BladeController {

	private IUserGroupService userGroupService;

	private IGroupService groupService;

	private IGroupLogService groupLogService;
	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userGroup")
	public R<UserGroupVO> detail(UserGroup userGroup) {
		UserGroup detail = userGroupService.getOne(Condition.getQueryWrapper(userGroup));
		return R.data(UserGroupWrapper.build().entityVO(detail));
	}

	/**
	* 用户加群
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userGroup")
	public R save(@Valid @RequestBody UserGroup userGroup) {
		//校验用户和群组
		userGroupService.checkUserGroup(userGroup);
		//添加日志
		groupLogService.addLog(userGroup.getGroupId(), userGroup.getUserId(), GroupLog.NEW_USER);
		return R.status(userGroupService.save(userGroup));
	}

	/**
	 * 用户退群
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入用户ID和群ID")
	public R remove(@Valid @RequestBody UserGroup userGroup) {
		//校验用户和群组
		//添加日志
		groupLogService.addLog(userGroup.getGroupId(), userGroup.getUserId(), GroupLog.DELETE_USER);
		//删除条件
		LambdaQueryWrapper<UserGroup> userGroupLambdaQueryWrapper = Wrappers.<UserGroup>lambdaQuery().
				eq(UserGroup::getUserId, userGroup.getUserId()).
				eq(UserGroup::getGroupId, userGroup.getGroupId());
		return R.status(userGroupService.remove(userGroupLambdaQueryWrapper));
	}


//	/**
//	 * 管理员删除群用户
//	 */
//	@PostMapping("/manager/remove")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入用户ID，群组ID，操作人ID")
//	public R save(@Valid @RequestBody UserGroupVO userGroup) {
//		return R.status(userGroupService.managerRemoveUser(userGroup));
//	}

}
