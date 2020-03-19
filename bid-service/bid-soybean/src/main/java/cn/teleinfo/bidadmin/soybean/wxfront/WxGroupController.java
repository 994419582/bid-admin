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
import cn.teleinfo.bidadmin.soybean.entity.ParentGroup;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IParentGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.vo.*;
import cn.teleinfo.bidadmin.soybean.wrapper.GroupWrapper;
import cn.teleinfo.bidadmin.soybean.wrapper.UserWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/group")
@Api(value = "", tags = "微信部门接口")
public class WxGroupController extends BladeController {

    private IGroupService groupService;

    private IParentGroupService parentGroupService;

    private IUserService userService;

    private IUserGroupService userGroupService;

    /**
     * 根据ID查看群组详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据ID查看群信息", notes = "传入主键Id,群创建人在用户表中被删除时，创建人姓名为空")
    public R<GroupVO> detail(Group group) {
        if (group.getId() == null) {
            throw new ApiException("部门id不能为空");
        }
        Group detail = groupService.getGroupById(group.getId());
        GroupVO data = GroupWrapper.build().entityVO(detail);
        //机构不存在返回null
        if (data == null) {
            return R.data(data);
        }
        //获取创建人姓名
        Integer createUser = data.getCreateUser();
        if (createUser == null) {
            return R.data(data);
        }
        //查询创建人姓名
        User user = userService.getById(createUser);
        if (user == null) {
            return R.data(data);
        }
        data.setCreaterName(user.getName());
        return R.data(data);
    }

    /**
     * 根据电话查看群组详情
     */
    @GetMapping("/phone")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据ID查看群信息", notes = "传入主键Id")
    public R<GroupVO> phone(String phone) {
        if (phone == null) {
            throw new ApiException("电话不能为空");
        }
        LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().
                eq(Group::getPhone, phone).eq(Group::getGroupType, Group.TYPE_PERSON);
        Group detail = groupService.getOne(queryWrapper, false);
        return R.data(GroupWrapper.build().entityVO(detail));
    }

    /**
     * 根据唯一码查看群组详情
     */
    @GetMapping("/groupCode")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据唯一码查看群信息", notes = "传入唯一码")
    public R<GroupVO> groupCode(String groupCode) {
        if (groupCode == null) {
            throw new ApiException("唯一码不能为空");
        }
        LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().eq(Group::getGroupCode, groupCode);
        Group detail = groupService.getOne(queryWrapper, false);
        return R.data(GroupWrapper.build().entityVO(detail));
    }

    /**
     * 根据机构ID，查看管理员用户信息
     */
    @GetMapping("/manager")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据机构ID，查看管理员用户信息", notes = "根据机构ID，查看管理员用户信息")
    public R<ManagerVo> manager(@ApiParam(name = "groupId", required = true) @RequestParam(name = "groupId", required = true) Integer groupId) {
        ManagerVo managerVo = new ManagerVo();
        List<UserVO> managerList = managerVo.getManagers();
        List<UserVO> dataManagerList = managerVo.getDataManagers();
        Group group = groupService.getGroupById(groupId);
        if (group == null) {
            return R.data(null);
        }
        //获取管理员用户
        String managers = group.getManagers();
        for (Integer userId : Func.toIntList(managers)) {
            User user = userService.getById(userId);
            if (user != null) {
                managerList.add(UserWrapper.build().entityVO(user));
            }
        }
        //获取统计管理员用户
        String dataManagers = group.getDataManagers();
        for (Integer userId : Func.toIntList(dataManagers)) {
            User user = userService.getById(userId);
            if (user != null) {
                dataManagerList.add(UserWrapper.build().entityVO(user));
            }
        }
        return R.data(managerVo);
    }

    /**
     * 查询此机构下拥有此手机号的用户信息
     */
    @GetMapping("/user/phone")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "查询此组织下拥有此手机号的用户信息", notes = "查询此组织下拥有此手机号的用户信息")
    public R<UserVO> phone(@ApiParam(value = "groupId", required = true) @RequestParam(name = "groupId", required = true) Integer groupId,
                           @ApiParam(value = "phone", required = true) @RequestParam(name = "phone", required = true) String phone) {
        //去手机号空格
        phone = phone.trim();
        //获取改组织下所有用户Id
        List<Integer> userIds = groupService.selectUserIdByParentId(groupId);
        if (CollectionUtils.isEmpty(userIds)) {
            return R.data(null);
        }
        //查询用户ID对应的用户
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().in(User::getId, userIds);
        List<User> userList = userService.list(queryWrapper);
        //过滤手机号
        String finalPhone = phone;
        List<User> users = userList.stream().filter(user -> {
            return finalPhone.equals(user.getPhone());
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(users)) {
            return R.data(null);
        }
        //返回用户信息
        User user = users.get(0);
        return R.data(UserWrapper.build().entityVO(user));
    }


    /**
     * 群组是否存在
     *
     * @return
     */
    @GetMapping("/exist")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "群组是否存在", notes = "传入群主键ID")
    public R<Boolean> exist(@RequestParam(name = "groupId") Integer groupId) {
        try {
            Group detail = groupService.getGroupById(groupId);
            if (detail == null) {
                throw new ApiException("部门不存在");
            }
            return R.data(true);
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 群组列表
     *
     * @return
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "群组列表", notes = "群查看接口，普通的列表（群广场使用）")
    public R<List<GroupVO>> select() {
        try {
            List<Group> groups = groupService.select();
            return R.data(GroupWrapper.build().listVO(groups));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 分页查询当前群及子群下所有用户
     *
     * @return
     */
    @GetMapping("/user/all")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "当前群及子群组列表", notes = "分页查询当前群及子群下所有用户")
    public R<IPage<UserVO>> selectUserPageByParentId(@RequestParam(name = "groupId", required = true) Integer groupId,
                                                     Query query) {
        try {
            return R.data(groupService.selectUserPageByParentId(groupId, Condition.getPage(query)));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 分页查询当前群下所有用户
     *
     * @return
     */
    @GetMapping("/user/current")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "当前群组列表", notes = "分页查询当前群下所有用户")
    public R<IPage<UserVO>> selectUserByParentId(@RequestParam(name = "groupId", required = true) Integer groupId,
                                                 Query query) {
        try {
            //查询所有用户ID
            UserGroup userGroup = new UserGroup();
            userGroup.setGroupId(groupId);
            userGroup.setStatus(UserGroup.NORMAL);
            List<UserGroup> userGroupList = userGroupService.list(Condition.getQueryWrapper(userGroup));
            List<Integer> userIdList = userGroupList.stream().map(UserGroup::getUserId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(userIdList)) {
                return R.data(UserWrapper.build().pageVO(Condition.getPage(query)));
            }
            //创造分页条件
            LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().in(User::getId, userIdList);
            //开始查询
            IPage<User> page = userService.page(Condition.getPage(query), queryWrapper);
            return R.data(UserWrapper.build().pageVO(page));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 带有children的树形下拉列表字典样式
     *
     * @return
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "树形下拉列表字典", notes = "群查看接口（下拉树形图）,groupId为空默认查询根组织下所有群组")
    public R<List<GroupTreeVo>> tree(Integer groupId) {
        try {
            List<GroupTreeVo> tree = groupService.treeChildren(groupId);
            return R.data(tree);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @GetMapping("/tree/user")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "用户群组树形下拉列表字典", notes = "根据用户ID查询其相关的群信息,使用树形表格接口（递归查询，查出子群的子群）")
    public R<List<GroupTreeVo>> treeChildren(@ApiParam(value = "用户ID", required = true) @RequestParam Integer userId) {
        try {
            List<GroupTreeVo> tree = groupService.treeUser(userId);
            return R.data(tree);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


    /**
     * 新增群组
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "微信新增部门", notes = "传入部门名称name, 上级部门parentId, 创建者userId")
    public R save(@Valid @RequestBody GroupVO group) {
            //设置创建人
            Integer userId = group.getUserId();
            if (!groupService.existUser(userId)) {
                throw new ApiException("用户不存在");
            }
            group.setCreateUser(userId);
            return R.status(groupService.wxSaveGroup(group));
    }


//    /**
//     * 修改群组
//     */
//    @PostMapping("/update")
//    @ApiOperationSupport(order = 5)
//    @ApiOperation(value = "修改群组", notes = "不支持修改父群组和管理员以及群人数")
//    public R update(@Valid @RequestBody GroupVO group) {
//        try {
//            Integer userId = group.getUserId();
//            Integer groupId = group.getId();
//            if (groupId == null) {
//                throw new ApiException("主键Id不能为空");
//            }
//            if (!groupService.existGroup(groupId)) {
//                throw new ApiException("部门不存在");
//            }
//            //权限校验
//            if (!groupService.isGroupCreater(groupId, userId)) {
//                throw new ApiException("用户ID和部门创建人ID不一致");
//            }
//            //不提供管理员修改功能
//            if (group.getManagers() != null) {
//                throw new ApiException("此接口不提供管理员修改功能");
//            }
//            //不提供父群组修改功能
//            if (group.getParentId() != null) {
//                throw new ApiException("此接口不提供父部门修改功能");
//            }
//            //不提供人数修改功能
//            if (group.getUserAccount() != null) {
//                throw new ApiException("部门人数不能更新");
//            }
//            return R.status(groupService.updateById(group));
//        } catch (ApiException e) {
//            return R.fail(e.getMessage());
//        }
//    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "删除机构", notes = "请求类型: _postParams, 删除机构及其所有子机构，人员移动到变动人员部门，人员管理权限全部取消")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam Integer groupId,
                    @ApiParam(value = "用户ID", required = true) @RequestParam Integer userId) {
        //用户是否存在
        if (!groupService.existUser(userId)) {
            throw new ApiException("用户不存在");
        }
        //部门是否存在
        if (!groupService.existGroup(groupId)) {
            throw new ApiException("部门不存在");
        }
        return R.status(groupService.wxRemoveGroup(groupId, userId));
    }

    /**
     * 群转让
     */
    @PostMapping("/transfer")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "群转让", notes = "传入group，群转让接口（只有群组拥有者有权限可以将群转让给其他人）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "一级机构创建人用户ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "transferId", value = "被转让人", required = true, paramType = "query", dataType = "int")
    })
    public R transfer(@RequestParam(name = "groupId", required = true) Integer groupId,
                      @RequestParam(name = "userId", required = true) Integer userId,
                      @RequestParam(name = "transferId", required = true) Integer transferId) {
        //判断是否是一级机构
        LambdaQueryWrapper<ParentGroup> queryWrapper = Wrappers.<ParentGroup>lambdaQuery().
                eq(ParentGroup::getGroupId, groupId);
        ParentGroup parentGroup = parentGroupService.getOne(queryWrapper);
        if (parentGroup == null) {
            throw new ApiException("机构不存在");
        }
        if (!Group.TOP_PARENT_ID.equals(parentGroup.getParentId())) {
            throw new ApiException("只允许一级机构转让创建人");
        }
        //校验是否已经转让
        if (groupService.isGroupCreater(groupId, transferId)) {
            throw new ApiException("创建者已转让");
        }
        //校验创建人
        if (!groupService.isGroupCreater(groupId, userId)) {
            throw new ApiException("您不是机构创建人");
        }
        //校验转让人是否输入改机构
        List<Integer> joinUserIds = groupService.selectUserIdByParentId(groupId);
//        if (!joinUserIds.contains(transferId)) {
//            throw new ApiException("被转让人尚未加入该机构");
//        }
        //更改创建人(改机构及其子机构创建人修改为被转让人)
        Group topGroup = groupService.getGroupById(groupId);
        if (topGroup == null) {
            throw new ApiException("数据错误, 请联系管理员");
        }
        LambdaUpdateWrapper<Group> updateWrapper = Wrappers.<Group>lambdaUpdate().
                eq(Group::getGroupIdentify, topGroup.getGroupIdentify()).
                set(Group::getCreateUser, transferId);
        groupService.update(updateWrapper);
        //创建人已经加入机构，则变更为一级管理员
        if (joinUserIds.contains(userId)) {
            String managers = topGroup.getManagers();
            HashSet<Integer> managerSet = new HashSet<>(Func.toIntList(managers));
            managerSet.add(userId);
            String newManagers = StringUtils.join(managerSet, ",");

            Group group = new Group();
            group.setId(topGroup.getId());
            group.setManagers(newManagers);
            groupService.updateById(group);
        }
        return R.status(true);
    }


    /**
     * 子群排序
     */
    @PostMapping("/sort")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "子群排序", notes = "子群排序接口（父群可以设置，其拥有的子群的排序规则）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentGroupId", value = "父群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "groupId", value = "子群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "creatorId", value = "父群创建人ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "排序", required = true, paramType = "query", dataType = "int")
    })
    public R sort(@RequestParam(name = "parentGroupId", required = true) Integer parentGroupId,
                  @RequestParam(name = "groupId", required = true) Integer groupId,
                  @RequestParam(name = "creatorId", required = true) Integer creatorId,
                  @RequestParam(name = "sort", required = true) Integer sort) {

        try {
            if (!groupService.existUser(creatorId)) {
                throw new ApiException("上级部门创建人不存在");
            }
            if (!groupService.existGroup(groupId)) {
                throw new ApiException("部门不存在");
            }
            if (!groupService.isGroupCreater(parentGroupId, creatorId)) {
                throw new ApiException("您不是创建人");
            }
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setParentId(parentGroupId);
            parentGroup.setGroupId(groupId);
            // TODO: 2020/2/29 中间表
//        parentGroup.setStatus(ParentGroup.DELETE);
            ParentGroup parent = parentGroupService.getOne(Condition.getQueryWrapper(parentGroup));
            if (parent == null) {
                throw new ApiException("下级部门不存在");
            }
            parentGroup.setId(parent.getId());
            parentGroup.setSort(sort);
            return R.status(parentGroupService.updateById(parentGroup));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * Excel批量导入群组
     */
    @PostMapping("/excelImport")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "Excel批量导入群组", notes = "传入一级群组属性，和Excel模板，模板中父群组必须存在")
    public R excelImport(@Valid @RequestBody ExcelGroupVo excelGroupVo) {
        if (!groupService.existUser(excelGroupVo.getUserId())) {
            throw new ApiException("用户不存在");
        }
        Group group = new Group();
        BeanUtils.copyProperties(excelGroupVo, group);
        group.setCreateUser(excelGroupVo.getUserId());
        return R.data(groupService.excelImport(group, excelGroupVo.getExcelFile()));
    }

    /**
     * 设置管理员
     */
    @PostMapping("/manager")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增群管理员", notes = "新增群管理员，只有群组拥有者有权限，可以设置管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "managerId", value = "准备设置为管理员的用户ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "int")
    })
    public R manager(@RequestParam(name = "groupId", required = true) Integer groupId,
                     @RequestParam(name = "managerId", required = true) Integer managerId,
                     @RequestParam(name = "userId", required = true) Integer userId) {

        try {
            //校验改群及其子群下是否有此用户
            List<Integer> groupUserIds = groupService.selectUserIdByParentId(groupId);
            if (!groupUserIds.contains(managerId)) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            //获取用户是管理员的群
            LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().eq(Group::getStatus, Group.NORMAL);
            List<Group> managerGroups = groupService.list(queryWrapper);
            managerGroups.removeIf(group -> {
                String managers = group.getManagers();
                if (Func.toIntList(managers).contains(userId)) {
                    return false;
                }
                Integer createUser = group.getCreateUser();
                if (createUser != null && createUser.equals(userId)) {
                    return false;
                }
                return true;
            });
            //查看用户管理的群是否是用户要加入群的父群
            boolean flag = false;
            List<GroupTreeVo> groupAndParent = groupService.selectAllGroupAndParent();
            for (Group managerGroup : managerGroups) {
                if (groupService.isChildrenGroup(groupAndParent, managerGroup.getId(), groupId) || managerGroup.getId().equals(groupId)) {
                    flag = true;
                }
            }
            if (flag == false) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            Group group = groupService.getGroupById(groupId);
            String managers = group.getManagers();
            ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
            if (managerList.contains(managerId)) {
                throw new ApiException("管理员已存在");
            }
//            if (!userGroupService.existUserGroup(groupId, managerId)) {
//                throw new ApiException("用户进群后才能任命为管理员");
//            }
            managerList.add(managerId);
            String newManagers = StringUtils.join(managerList, ",");
            group.setManagers(newManagers);
            return R.status(groupService.updateById(group));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 设置数据管理员
     */
    @PostMapping("/dataManager")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增群管理员", notes = "新增群管理员，只有群组拥有者有权限，可以设置管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true,  dataType = "int"),
            @ApiImplicitParam(name = "managerId", value = "准备设置为数据管理员的用户ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true,  dataType = "int")
    })
    public R dataManager(@RequestParam(name = "groupId", required = true) Integer groupId,
                         @RequestParam(name = "managerId", required = true) Integer managerId,
                         @RequestParam(name = "userId", required = true) Integer userId) {

        try {
            //校验改群及其子群下是否有此用户
            List<Integer> groupUserIds = groupService.selectUserIdByParentId(groupId);
            if (!groupUserIds.contains(managerId)) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            //获取用户是管理员的群
            LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().eq(Group::getStatus, Group.NORMAL);
            List<Group> managerGroups = groupService.list(queryWrapper);
            managerGroups.removeIf(group -> {
                String managers = group.getManagers();
                if (Func.toIntList(managers).contains(userId)) {
                    return false;
                }
                Integer createUser = group.getCreateUser();
                if (createUser != null && createUser.equals(userId)) {
                    return false;
                }
                return true;
            });
            //查看用户管理的群是否是用户要加入群的父群
            boolean flag = false;
            List<GroupTreeVo> groupAndParent = groupService.selectAllGroupAndParent();
            for (Group managerGroup : managerGroups) {
                if (groupService.isChildrenGroup(groupAndParent, managerGroup.getId(), groupId) || managerGroup.getId().equals(groupId)) {
                    flag = true;
                }
            }
            if (flag == false) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            Group group = groupService.getGroupById(groupId);
            String dataManagers = group.getDataManagers();
            ArrayList<Integer> dataManagerList = new ArrayList<>(Func.toIntList(dataManagers));
            if (dataManagerList.contains(managerId)) {
                throw new ApiException("数据管理员已存在");
            }
            dataManagerList.add(managerId);
            String newManagers = StringUtils.join(dataManagerList, ",");
            group.setDataManagers(newManagers);
            return R.status(groupService.updateById(group));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }


    /**
     * 移除管理员
     */
    @DeleteMapping("/manager")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "移除管理员", notes = "移除管理员，只有群组拥有者有权限，可以移除管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "managerId", value = "要被移除的管理员ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "int")
    })
    public R removeManager(@RequestParam(name = "groupId", required = true) Integer groupId,
                           @RequestParam(name = "managerId", required = true) Integer managerId,
                           @RequestParam(name = "userId", required = true) Integer userId) {

        try {
            //校验改群及其子群下是否有此用户
            List<Integer> groupUserIds = groupService.selectUserIdByParentId(groupId);
            if (!groupUserIds.contains(managerId)) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            //获取用户是管理员的群
            LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().eq(Group::getStatus, Group.NORMAL);
            List<Group> managerGroups = groupService.list(queryWrapper);
            managerGroups.removeIf(group -> {
                String managers = group.getManagers();
                if (Func.toIntList(managers).contains(userId)) {
                    return false;
                }
                Integer createUser = group.getCreateUser();
                if (createUser != null && createUser.equals(userId)) {
                    return false;
                }
                return true;
            });
            //查看用户管理的群是否是要移除管理员群的父群，或者是同一个群
            boolean flag = false;
            List<GroupTreeVo> groupAndParent = groupService.selectAllGroupAndParent();
            for (Group managerGroup : managerGroups) {
                if (groupService.isChildrenGroup(groupAndParent, managerGroup.getId(), groupId) || managerGroup.getId().equals(groupId)) {
                    flag = true;
                }
            }
            if (flag == false) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            Group group = groupService.getGroupById(groupId);
            String managers = group.getManagers();
            ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
            if (!managerList.contains(managerId)) {
                throw new ApiException("此用户不是管理员");
            }
            //移除此管理员
            managerList.remove(managerId);
            String newManagers = StringUtils.join(managerList, ",");
            group.setManagers(newManagers);
            return R.status(groupService.updateById(group));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 移除管理员
     */
    @DeleteMapping("/dataManager")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "移除管理员", notes = "移除管理员，只有群组拥有者有权限，可以移除管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "managerId", value = "要被移除的管理员ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "int")
    })
    public R removeDataManager(@RequestParam(name = "groupId", required = true) Integer groupId,
                               @RequestParam(name = "managerId", required = true) Integer managerId,
                               @RequestParam(name = "userId", required = true) Integer userId) {

        try {
            //校验改群及其子群下是否有此用户
            List<Integer> groupUserIds = groupService.selectUserIdByParentId(groupId);
            if (!groupUserIds.contains(managerId)) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            //获取用户是管理员的群
            LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>lambdaQuery().eq(Group::getStatus, Group.NORMAL);
            List<Group> managerGroups = groupService.list(queryWrapper);
            managerGroups.removeIf(group -> {
                String managers = group.getManagers();
                if (Func.toIntList(managers).contains(userId)) {
                    return false;
                }
                Integer createUser = group.getCreateUser();
                if (createUser != null && createUser.equals(userId)) {
                    return false;
                }
                return true;
            });
            //查看用户管理的群是否是要移除管理员群的父群，或者是同一个群
            boolean flag = false;
            List<GroupTreeVo> groupAndParent = groupService.selectAllGroupAndParent();
            for (Group managerGroup : managerGroups) {
                if (groupService.isChildrenGroup(groupAndParent, managerGroup.getId(), groupId) || managerGroup.getId().equals(groupId)) {
                    flag = true;
                }
            }
            if (flag == false) {
                throw new ApiException("您没有权限,请联系创建者或上级管理员");
            }
            Group group = groupService.getGroupById(groupId);
            String managers = group.getDataManagers();
            ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
            if (!managerList.contains(managerId)) {
                throw new ApiException("此用户不是数据管理员");
            }
            //移除此管理员
            managerList.remove(managerId);
            String newManagers = StringUtils.join(managerList, ",");
            group.setDataManagers(newManagers);
            return R.status(groupService.updateById(group));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 群组解散或者关闭接口
     */
    @PostMapping("/close")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "群组解散或者关闭接口", notes = "只有群主才可拥有此操作, 逻辑删除当前群及其子群，当前群和子群的用户也都一并删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "子群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", required = true, paramType = "query", dataType = "int")
    })
    public R close(@RequestParam(name = "groupId", required = true) Integer groupId,
                   @RequestParam(name = "creatorId", required = true) Integer creatorId) {
        return R.status(groupService.close(groupId, creatorId));
    }

    @PostMapping("/test")
    public R test(Integer parentId, Integer checkId) {
        return R.data(true);
    }

}
