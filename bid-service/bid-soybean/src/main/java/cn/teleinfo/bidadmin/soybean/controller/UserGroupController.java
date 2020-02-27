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
package cn.teleinfo.bidadmin.soybean.controller;

import cn.teleinfo.bidadmin.soybean.entity.GroupLog;
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
import cn.teleinfo.bidadmin.soybean.wrapper.UserGroupWrapper;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/usergroup")
@Api(value = "", tags = "用户群组接口")
public class UserGroupController extends BladeController {

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
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userGroup")
	public R<IPage<UserGroupVO>> list(UserGroup userGroup, Query query) {
		IPage<UserGroup> pages = userGroupService.page(Condition.getPage(query), Condition.getQueryWrapper(userGroup));
		return R.data(UserGroupWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userGroup")
	public R<IPage<UserGroupVO>> page(UserGroupVO userGroup, Query query) {
		IPage<UserGroupVO> pages = userGroupService.selectUserGroupPage(Condition.getPage(query), userGroup);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userGroup")
	public R save(@Valid @RequestBody UserGroup userGroup) {
		//校验用户和群组
		userGroupService.checkAddUserGroup(userGroup);
		//添加日志
		groupLogService.addLog(userGroup.getGroupId(), userGroup.getUserId(), GroupLog.NEW_USER);
		return R.status(userGroupService.save(userGroup));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userGroup")
	public R update(@Valid @RequestBody UserGroup userGroup) {
		//校验用户和群组
		userGroupService.checkAddUserGroup(userGroup);
		groupLogService.addLog(userGroup.getGroupId(), userGroup.getUserId(), GroupLog.UPDATE_USER);
		return R.status(userGroupService.updateById(userGroup));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userGroup")
	public R submit(@Valid @RequestBody UserGroup userGroup) {
		//校验用户和群组
		userGroupService.checkAddUserGroup(userGroup);
		//添加日志
		if (userGroup.getId() == null) {
			groupLogService.addLog(userGroup.getGroupId(), userGroup.getUserId(), GroupLog.NEW_USER);
		} else {
			throw new ApiException("群组不允许修改");
		}
		return R.status(userGroupService.saveOrUpdate(userGroup));
	}

	
	/**
	* 删除
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userGroupService.removeByIds(Func.toIntList(ids)));
	}

	
}
