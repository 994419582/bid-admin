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

import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.ParentGroup;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IParentGroupService;
import cn.teleinfo.bidadmin.soybean.vo.*;
import cn.teleinfo.bidadmin.soybean.wrapper.GroupWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.*;
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

	private IClocklnService clocklnService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入groupId")
	public R<GroupVO> detail(Group group) {
		group.setStatus(Group.NORMAL);
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
	 * 根据父ID查询下一级子机构
	 * @return
	 */
	@GetMapping("/selectChildGroup")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "群组列表", notes = "查询所有群")
	public R<List<GroupVO>> selectChildGroup(Integer groupId) {
		//查询下一级子机构id
		LambdaQueryWrapper<ParentGroup> queryWrapper = Wrappers.<ParentGroup>lambdaQuery().eq(ParentGroup::getParentId, groupId);
		List<ParentGroup> parentGroups = parentGroupService.list(queryWrapper);
		if (CollectionUtils.isEmpty(parentGroups)) {
			return R.data(null);
		}
		List<Integer> ids = parentGroups.stream().map(ParentGroup::getGroupId).collect(Collectors.toList());
		//查询子机构
		LambdaQueryWrapper<Group> groupLambdaQueryWrapper = Wrappers.<Group>lambdaQuery().
				in(Group::getId, ids).eq(Group::getStatus, Group.NORMAL);
		List<Group> groupList = groupService.list(groupLambdaQueryWrapper);
		return R.data(GroupWrapper.build().listVO(groupList));
	}

	/**
	 * 模糊搜索
	 * @return
	 */
	@GetMapping("/search")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "模糊搜索", notes = "根据名称模糊搜索")
	public R<List<GroupVO>> search(@RequestParam String name) {
		List<Group> groups = groupService.list(Wrappers.<Group>lambdaQuery().
				like(Group::getFullName, name).eq(Group::getStatus, Group.NORMAL));
		if (CollectionUtils.isEmpty(groups)) {
			return R.data(null);
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
		List<Group> filterGroupList = groups.stream().filter(group -> group.getId().equals(id)).collect(Collectors.toList());
		return R.data(GroupWrapper.build().listVO(filterGroupList));
	}

	/**
	 * 树形下拉列表字典
	 */
	@GetMapping("/tree/children")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "树形下拉列表字典", notes = "带有children的下拉树")
	public R<List> treeChildren(Integer groupId, String name, Integer groupType,Integer topId) {
		if (!StringUtils.isEmpty(name) || groupType != null || topId != null) {
			String groupIdentify = null;
			if (topId != null) {
				Group group = groupService.getGroupById(topId);
				if (group != null) {
					groupIdentify = group.getGroupIdentify();
				}
			}
			LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().
					like(!StringUtils.isEmpty(name), Group::getName, name).
					eq(groupType != null, Group::getGroupType, groupType).
					eq(!StringUtils.isEmpty(groupIdentify), Group::getGroupIdentify, groupIdentify);
			//搜索
			List<Group> groupList = groupService.list(queryWrapper);
			return R.data(groupList);
		}
		List<GroupTreeVo> tree = groupService.treeChildren(groupId);
		return R.data(tree);
	}

	/**
	 * 包含顶级节点的下拉树
	 */
	@GetMapping("/tree/top")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "包含顶级节点的下拉树字典", notes = "包含顶级节点的下拉树")
	public R<List<GroupTreeVo>> treeTop() {
		Group topGroup = groupService.getGroupById(Group.TOP_PARENT_ID);
		GroupTreeVo groupTreeVo = new GroupTreeVo();
		BeanUtils.copyProperties(topGroup, groupTreeVo);
		List<GroupTreeVo> tree = groupService.treeChildren(Group.TOP_PARENT_ID);
		groupTreeVo.setChildren(tree);
		ArrayList<GroupTreeVo> groupTreeVos = new ArrayList<>();
		groupTreeVos.add(groupTreeVo);
		return R.data(groupTreeVos);
	}

	/**
	 * 根据父群组查询子群组
	 * @return
	 */
	@GetMapping("/children")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "根据父群组查询子群组 ", notes = "传入group")
	public R<List<GroupVO>> children(Group group) {
		ArrayList<Group> topGroups = new ArrayList<>();
		List<Group> groups = groupService.children(group);
		//点击一级节点时增加自身
		if (Group.TOP_PARENT_ID.equals(group.getId())) {
			Group topGroup = groupService.getGroupById(group.getId());
			topGroups.add(topGroup);
			topGroups.addAll(groups);
			groups = topGroups;
		}
		//用户不存在是返回本身
		if (CollectionUtils.isEmpty(groups)) {
			List<Group> oneSelf = groupService.list(Condition.getQueryWrapper(group));
			return R.data(GroupWrapper.build().listVO(oneSelf));
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
		if (group.getParentId() != null) {
			throw new ApiException("上级部门不能为空");
		}
		return R.status(groupService.saveGroup(group));
	}


	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入group")
	public R update(@Valid @RequestBody Group group) {
		if (group.getId() == null) {
			throw new ApiException("主键Id不能为空");
		}
		if (!groupService.existGroup(group.getId())) {
			throw new ApiException("部门不存在");
		}
		//不提供人数修改功能
		if (group.getUserAccount() != null) {
			throw new ApiException("部门人数不能更新");
		}
		return R.status(groupService.updateGroup(group));
	}

	/**
	 * 更新管理员
	 */
	@PostMapping("/updateManager")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改群管理员", notes = "修改群管理员")
	public R updateManager(@Valid @RequestBody GroupManagerVo managerVo) {
		Group group = new Group();
		List<Integer> managers = managerVo.getManagers();
		List<Integer> dataManagers = managerVo.getDataManagers();
		group.setId(managerVo.getId());
		group.setManagers(StringUtils.join(managers, ","));
		group.setDataManagers(StringUtils.join(dataManagers, ","));
		return R.status(groupService.updateById(group));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入group")
	public R submit(@Valid @RequestBody Group group) {
		return R.status(groupService.saveOrUpdateGroup(group));
	}


	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		//查询用户Id和创建人是否一致
		for (Integer id : Func.toIntList(ids)) {
			if (!groupService.existGroup(id)) {
				throw new ApiException("群不存在");
			}
		}
		return R.status(groupService.removeGroupByIds(ids));
	}
	/**
	 * 自定义分页
	 */
	@GetMapping("/census")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取统计页面所需统计数据", notes = "传入群ID和打卡日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "群组ID", paramType = "query", dataType = "int"),
	})
	public R  census(@RequestParam(name = "groupId") Integer groupId) {
		Date today = new Date(System.currentTimeMillis());


		if (groupId == null){
			return R.fail("部门ID不能为空");
		}
		Group group= groupService.getById(groupId);

		if (group==null){
			return R.fail("该部门不存在,请输入正确的部门ID");
		}
		List<Integer> ids=groupService.selectUserIdByParentId(groupId);
		List<Clockln> list =new ArrayList<>();
		if (ids.size() >0){
			list=clocklnService.selectClocklnByGroup(ids,today);
		}
		Map map= new HashMap();
		map.put("clockIn",list.size());
		map.put("unClockIn",ids.size()-list.size());

		return R.data(map);
	}


}
