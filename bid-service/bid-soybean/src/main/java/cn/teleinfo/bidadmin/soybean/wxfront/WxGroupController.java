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
import cn.teleinfo.bidadmin.soybean.utils.ExcelUtils;
import cn.teleinfo.bidadmin.soybean.vo.GroupTreeVo;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import cn.teleinfo.bidadmin.soybean.wrapper.GroupWrapper;
import cn.teleinfo.bidadmin.soybean.wrapper.UserWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
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
@Api(value = "", tags = "微信群组接口")
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
    @ApiOperation(value = "根据ID查看群信息", notes = "传入主键Id")
    public R<GroupVO> detail(Group group) {
        try {
            if (group.getId() == null) {
                throw new ApiException("群组id不能为空");
            }
            Group detail = groupService.getGroupById(group.getId());
            return R.data(GroupWrapper.build().entityVO(detail));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
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
                throw new ApiException("群组不存在");
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
    @ApiOperation(value = "新增群组", notes = "传入group,用户Id不能为null")
    public R save(@Valid @RequestBody GroupVO group) {
        try {
            //设置创建人
            Integer userId = group.getUserId();
            if (!groupService.existUser(userId)) {
                throw new ApiException("用户不存在");
            }
            group.setCreateUser(userId);
            return R.status(groupService.saveGroup(group));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }


    /**
     * 修改群组
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改群组", notes = "不支持修改父群组和管理员以及群人数")
    public R update(@Valid @RequestBody GroupVO group) {
        try {
            Integer userId = group.getUserId();
            Integer groupId = group.getId();
            if (groupId == null) {
                throw new ApiException("主键Id不能为空");
            }
            if (!groupService.existGroup(groupId)) {
                throw new ApiException("群组不存在");
            }
            //权限校验
            if (!groupService.isGroupCreater(groupId, userId)) {
                throw new ApiException("用户ID和群创建人ID不一致");
            }
            //不提供管理员修改功能
            if (group.getManagers() != null) {
                throw new ApiException("此接口不提供管理员修改功能");
            }
            //不提供父群组修改功能
            if (group.getParentGroups() != null) {
                throw new ApiException("此接口不提供父群组修改功能");
            }
            //不提供人数修改功能
            if (group.getUserAccount() != null) {
                throw new ApiException("群人数不能更新");
            }
            return R.status(groupService.updateById(group));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "删除后status为1, 用户id必须和所有群组创建人一致")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids,
                    @ApiParam(value = "用户ID", required = true) @RequestParam Integer userId) {
        try {
            //用户是否存在
            if (!groupService.existUser(userId)) {
                throw new ApiException("用户不存在");
            }
            //查询用户Id和创建人是否一致
            for (Integer id : Func.toIntList(ids)) {
                if (!groupService.existGroup(id)) {
                    throw new ApiException("群不存在");
                }
                if (!groupService.isGroupCreater(id, userId)) {
                    throw new ApiException("用户ID和群创建人ID不一致");
                }
            }
            return R.status(groupService.removeGroupByIds(ids));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 群转让
     */
    @PostMapping("/transfer")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "群转让", notes = "传入group，群转让接口（只有群组拥有者有权限可以将群转让给其他人）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "创建人用户ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "transferId", value = "转让人", required = true, paramType = "query", dataType = "int")
    })
    public R transfer(@RequestParam(name = "groupId", required = true) Integer groupId,
                      @RequestParam(name = "userId", required = true) Integer userId,
                      @RequestParam(name = "transferId", required = true) Integer transferId) {
        try {
            if (!groupService.isGroupCreater(groupId, userId)) {
                throw new ApiException("用户不是群组创建人");
            }
            if (!groupService.existUser(userId)) {
                throw new ApiException("用户不存在");
            }
            if (!userGroupService.existUserGroup(groupId, transferId)) {
                throw new ApiException("用户进群后才能任命为管理员");
            }
            Group group = new Group();
            group.setId(groupId);
            group.setCreateUser(transferId);

            return R.status(groupService.updateById(group));
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "logo", value = "群组ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "简介", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "contact", value = "联系人", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "创建人ID", required = true, paramType = "query", dataType = "int")
    })
    public R excelImport(@RequestParam(name = "logo", required = true) String logo,
                         @RequestParam(name = "name", required = true) String name,
                         @RequestParam(name = "remarks", required = true) String remarks,
                         @RequestParam(name = "contact", required = true) String contact,
                         @RequestParam(name = "phone", required = true) String phone,
                         @RequestParam(name = "userId", required = true) Integer userId,
                         @RequestParam("excelFile") MultipartFile excelFile) {
        if (!groupService.existUser(userId)) {
            throw new ApiException("用户不存在");
        }
        Group group = new Group();
        group.setLogo(logo);
        group.setName(name);
        group.setRemarks(remarks);
        group.setContact(contact);
        group.setPhone(phone);
        group.setCreateUser(userId);
        return R.status(groupService.excelImport(group, excelFile));
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
                throw new ApiException("父群创建人不存在");
            }
            if (!groupService.existGroup(groupId)) {
                throw new ApiException("子群组不存在");
            }
            if (!groupService.isGroupCreater(parentGroupId, creatorId)) {
                throw new ApiException("改用户不是创建人");
            }
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setParentId(parentGroupId);
            parentGroup.setGroupId(groupId);
            // TODO: 2020/2/29 中间表
//        parentGroup.setStatus(ParentGroup.DELETE);
            ParentGroup parent = parentGroupService.getOne(Condition.getQueryWrapper(parentGroup));
            if (parent == null) {
                throw new ApiException("子群不存在");
            }
            parentGroup.setId(parent.getId());
            parentGroup.setSort(sort);
            return R.status(parentGroupService.updateById(parentGroup));
        } catch (ApiException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 设置管理员
     */
    @PostMapping("/manager")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增群管理员", notes = "新增群管理员，只有群组拥有者有权限，可以设置管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "managerId", value = "管理员ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", required = true, paramType = "query", dataType = "int")
    })
    public R manager(@RequestParam(name = "groupId", required = true) Integer groupId,
                     @RequestParam(name = "managerId", required = true) Integer managerId,
                     @RequestParam(name = "creatorId", required = true) Integer creatorId) {

        try {
            if (!groupService.isGroupCreater(groupId, creatorId)) {
                throw new ApiException("不是群创建人");
            }
            Group group = groupService.getGroupById(groupId);
            String managers = group.getManagers();
            ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
            if (managerList.contains(managerId)) {
                throw new ApiException("管理员已存在");
            }
            if (!userGroupService.existUserGroup(groupId, managerId)) {
                throw new ApiException("用户进群后才能任命为管理员");
            }
            managerList.add(managerId);
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
    @DeleteMapping("/manager")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "移除管理员", notes = "移除管理员，只有群组拥有者有权限，可以移除管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "managerId", value = "管理员ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", required = true, paramType = "query", dataType = "int")
    })
    public R removeManager(@RequestParam(name = "groupId", required = true) Integer groupId,
                           @RequestParam(name = "managerId", required = true) Integer managerId,
                           @RequestParam(name = "creatorId", required = true) Integer creatorId) {

        try {
            if (!groupService.isGroupCreater(groupId, creatorId)) {
                throw new ApiException("不是群创建人");
            }
            Group group = groupService.getGroupById(groupId);
            String managers = group.getManagers();
            ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
            if (!managerList.contains(managerId)) {
                throw new ApiException("此用户不是管理员");
            }
            if (!userGroupService.existUserGroup(groupId, managerId)) {
                throw new ApiException("群组未发现此用户");
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
     * 群组解散或者关闭接口
     */
    @PostMapping("/close")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "群组解散或者关闭接口", notes = "只有群主才可拥有此操作,采用逻辑删除将status状态设为1、并且接触该群与所有用户和其他群的父子关系，以上所有操作均使用逻辑操作改变状态值，并不实质删除任何数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "子群组ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", required = true, paramType = "query", dataType = "int")
    })
    public R close(@RequestParam(name = "groupId", required = true) Integer groupId,
                   @RequestParam(name = "creatorId", required = true) Integer creatorId) {
        try {
            return R.status(groupService.close(groupId, creatorId));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @GetMapping("/test")
    public R test(Integer parentId, Integer checkId) {
        List<GroupTreeVo> groupAndParent = groupService.selectAllGroupAndParent();
        boolean flag = groupService.isChildrenGroup(groupAndParent, parentId, checkId);
        return R.data(flag);
    }

}
