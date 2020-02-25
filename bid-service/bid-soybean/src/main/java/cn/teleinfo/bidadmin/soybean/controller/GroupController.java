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

import cn.teleinfo.bidadmin.soybean.service.IParentGroupService;
import cn.teleinfo.bidadmin.soybean.service.impl.GroupServiceImpl;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import cn.teleinfo.bidadmin.soybean.wrapper.GroupWrapper;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/group")
@Api(value = "群组管理接口", tags = "群组管理接口")
public class GroupController extends BladeController {

	private IGroupService groupService;

	private IParentGroupService parentGroupService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入group")
	public R<GroupVO> detail(Group group) {
		Group detail = groupService.detail(group);
		return R.data(GroupWrapper.build().entityVO(detail));
	}

	/**
	 * 群组列表
	 * @return
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "群组列表", notes = "查询所有群")
	public R<List<GroupVO>> select() {
		List<Group> groups = groupService.select();
		if (CollectionUtils.isEmpty(groups)) {
			return null;
		}
		return R.data(GroupWrapper.build().listVO(groups));
	}

	/**
	 * 群组列表
	 * @return
	 */
	@GetMapping("/select/noOneself")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "不包含某个ID的群组列表", notes = "不包含某个ID的群组列表")
	public R<List<GroupVO>> select(@RequestParam(name = "id") Integer id) {
		List<Group> groups = groupService.select();
		List<Group> filterGroupList = groups.stream().filter(group -> group.getId() != id).collect(Collectors.toList());
		return R.data(GroupWrapper.build().listVO(filterGroupList));
	}

	/**
	 * 树形下拉列表字典
	 */
	@GetMapping("/tree/children")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "树形下拉列表字典", notes = "带有children的下拉树")
	public R<List<HashMap>> treeChildren() {
		List<HashMap> tree = groupService.treeChildren();
		return R.data(tree);
	}

	/**
	 * 根据父群组查询子群组
	 * @return
	 */
	@GetMapping("/children")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "根据父群组查询子群组 ", notes = "传入group")
	public R<List<GroupVO>> children(Group group) {
		List<Group> groups = groupService.children(group);
		if (CollectionUtils.isEmpty(groups)) {
			return null;
		}
		return R.data(GroupWrapper.build().listVO(groups));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入group")
	public R<IPage<GroupVO>> list(Group group, Query query) {
		IPage<Group> pages = groupService.page(Condition.getPage(query), Condition.getQueryWrapper(group));
		return R.data(GroupWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入group")
	public R<IPage<GroupVO>> page(GroupVO group, Query query) {
		IPage<GroupVO> pages = groupService.selectGroupPage(Condition.getPage(query), group);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入group")
	public R save(@Valid @RequestBody Group group) {
		return R.status(groupService.saveGroupMiddleTable(group));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入group")
	public R update(@Valid @RequestBody Group group) {
		return R.status(groupService.updateGroupMiddleTable(group));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入group")
	public R submit(@Valid @RequestBody Group group) {
		GroupServiceImpl.modifyObject(group);
		return R.status(groupService.saveOrUpdateGroupMiddleTable(group));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(groupService.removeGroupMiddleTableById(Func.toIntList(ids)));
	}

}
