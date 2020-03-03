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

import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			return R.status(userGroupService.managerRemoveUser(userGroup));
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

}
