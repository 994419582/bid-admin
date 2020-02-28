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
package cn.teleinfo.bidadmin.soybean.service.impl;

import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.ParentGroup;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.mapper.GroupMapper;
import cn.teleinfo.bidadmin.soybean.service.*;
import cn.teleinfo.bidadmin.soybean.vo.GroupTreeVo;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    @Autowired
    private IParentGroupService parentGroupService;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private IChildrenGroupService childrenGroupService;
    @Autowired
    private IUserGroupService userGroupService;
    @Autowired
    private IGroupLogService groupLogService;
    @Autowired
    private IUserService userService;

    //逗号分隔类型校验
    public static final String STRING_LIST = "\\d+(,\\d+)*";

    @Override
    public IPage<GroupVO> selectGroupPage(IPage<GroupVO> page, GroupVO group) {
        return page.setRecords(baseMapper.selectGroupPage(page, group));
    }

    @Override
    @Transactional
    public boolean saveOrUpdateGroup(Group group) {
        //新增群组
        if (group.getId() == null) {
            if (org.springframework.util.StringUtils.isEmpty(group.getParentGroups())) {
                throw new ApiException("父群组不能为空");
            }
            saveGroup(group);
        } else {
            //更新群
            if (group.getId() == null) {
                throw new ApiException("主键Id不能为空");
            }
            if (!existGroup(group.getId())) {
                throw new ApiException("群组不存在");
            }
            //不提供人数修改功能
            if (group.getUserAccount() != null) {
                throw new ApiException("群人数不能更新");
            }
            updateGroup(group);
        }
        return true;
    }

    @Override
    public boolean close(Integer groupId, Integer creatorId) {
        if (!isGroupCreater(groupId, creatorId)) {
            throw new ApiException("不是群创建人");
        }
        //逻辑删除群
        Group group = new Group();
        group.setId(groupId);
        group.setStatus(Group.DELETE);
        updateById(group);
        //逻辑删除群用户
        LambdaUpdateWrapper<UserGroup> updateWrapper = Wrappers.<UserGroup>lambdaUpdate().
                eq(UserGroup::getGroupId, groupId).
                set(UserGroup::getStatus, UserGroup.DELETE);
        userGroupService.update(updateWrapper);
        //逻辑删除父子
        LambdaUpdateWrapper<ParentGroup> parentGroupLambdaUpdateWrapper = Wrappers.<ParentGroup>lambdaUpdate().
                eq(ParentGroup::getParentId, 1).
                set(ParentGroup::getStatus, ParentGroup.DELETE);
        parentGroupService.update(parentGroupLambdaUpdateWrapper);
        return true;
    }

    @Override
    public List<GroupTreeVo> selectAllGroupAndParent() {
        return groupMapper.tree();
    }

    @Override
    public Group detail(Group group) {
        Integer groupId = group.getId();
        Group detail = this.getGroupById(groupId);
        if (detail == null) {
            return null;
        }
        LambdaQueryWrapper<ParentGroup> parentGroupLambdaQueryWrapper = Wrappers.<ParentGroup>lambdaQuery().
                eq(ParentGroup::getGroupId, groupId);
        List<ParentGroup> parentGroups = parentGroupService.list(parentGroupLambdaQueryWrapper);
        if (!CollectionUtils.isEmpty(parentGroups)) {
            String parentGroupIds = parentGroups.stream().map(parentGroup -> {
                return String.valueOf(parentGroup.getParentId());
            }).collect(Collectors.joining(","));
            detail.setParentGroups(parentGroupIds);
        }
        return detail;
    }

    @Override
    public List<GroupTreeVo> treeChildren() {
        List<GroupTreeVo> tree = selectAllGroupAndParent();
        List<GroupTreeVo> maps = buildTree(tree, 0);
        return maps;
    }

    @Override
    public List<GroupTreeVo> treeUser(Integer userId) {
        if (!existUser(userId)) {
            throw new ApiException("用户不存在");
        }
        ArrayList<GroupTreeVo> treeRootList = new ArrayList<>();
        //查询所有群
        List<GroupTreeVo> groupAndParentList = selectAllGroupAndParent();

        //遍历获取用户管理的所有群
        List<GroupTreeVo> userGroupTreeList = groupAndParentList.stream().filter(groupTreeVo -> {
            List<Integer> managerList = Func.toIntList(groupTreeVo.getManagers());
            Integer createUser = groupTreeVo.getCreateUser();
            if (managerList.contains(userId)) {
                return true;
            }
            if (createUser != null && createUser.equals(userId)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        //递归获取群下所有子群添加到groupTreeVos
        for (GroupTreeVo groupTreeVo : userGroupTreeList) {
            List<GroupTreeVo> childTree = buildTree(groupAndParentList, groupTreeVo.getId());
            if (!CollectionUtils.isEmpty(childTree)) {
                groupTreeVo.setChildren(childTree);
            }
            treeRootList.add(groupTreeVo);
        }
        return treeRootList;
    }

    private boolean isGroupMangerOrCreater(Integer groupId, Integer userId) {
        if (groupId == null) {
            throw new ApiException("群ID不能为null");
        }
        if (userId == null) {
            throw new ApiException("用户ID不能为null");
        }
        Group group = this.getGroupById(groupId);
        if (group == null) {
            throw new ApiException("群组不存在");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        List<Integer> managerList = Func.toIntList(group.getManagers());
        Integer createUser = group.getCreateUser();
        if (managerList.contains(userId)) {
            return true;
        }
        if (createUser != null && createUser.equals(userId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isGroupManger(Integer groupId, Integer userId) {
        if (groupId == null) {
            throw new ApiException("群ID不能为null");
        }
        if (userId == null) {
            throw new ApiException("用户ID不能为null");
        }
        Group group = this.getGroupById(groupId);
        if (group == null) {
            throw new ApiException("群组不存在");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        List<Integer> managerList = Func.toIntList(group.getManagers());
        if (managerList.contains(userId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isGroupCreater(Integer groupId, Integer userId) {
        if (groupId == null) {
            throw new ApiException("群ID不能为null");
        }
        if (userId == null) {
            throw new ApiException("用户ID不能为null");
        }
        Group group = this.getGroupById(groupId);
        if (group == null) {
            throw new ApiException("群组不存在");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        Integer createUser = group.getCreateUser();
        if (createUser != null && createUser.equals(userId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existGroup(Integer groupId) {
        if (groupId == null) {
            throw new ApiException("群组ID不能为空");
        }
        Group group = getGroupById(groupId);
        if (group == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean existUser(Integer userId) {
        if (userId == null) {
            throw new ApiException("用户ID不存在");
        }
        User user = userService.getById(userId);
        if (user == null) {
            return false;
        }
        return true;
    }

    public Group getGroupById(Integer groupId) {
        if (groupId == null) {
            throw new ApiException("群ID不能为Null");
        }
        Group group = new Group();
        group.setId(groupId);
        group.setStatus(Group.NORMAL);
        return getOne(Condition.getQueryWrapper(group));
    }

    @Override
    @Transactional
    public boolean saveGroup(Group group) {
        if (group.getId() != null) {
            throw new ApiException("群主键ID只能为空");
        }
        if (group.getUserAccount() != null) {
            throw new ApiException("群人数只能为空");
        }
        checkParentGroupAndManager(group);
        //新增群
        group.setStatus(Group.NORMAL);
        save(group);
        //设置为群第一个用户
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(group.getId());
        userGroup.setUserId(group.getCreateUser());
        userGroup.setStatus(UserGroup.NORMAL);
        userGroupService.save(userGroup);
        //保存中间表
        String parentGroups = group.getParentGroups();
        for (Integer parentId : Func.toIntList(parentGroups)) {
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setGroupId(group.getId());
            parentGroup.setParentId(parentId);
            parentGroupService.save(parentGroup);
        }
        return true;
    }

    private boolean checkParentGroupAndManager(Group group) {
        //未指定群ID，默认父群组为顶级群组
        Integer groupId = group.getId();
        String parentGroups = group.getParentGroups();
        if (StringUtils.isEmpty(parentGroups)) {
            group.setParentGroups(String.valueOf(Group.TOP_PARENT_ID));
        } else {
            if (!Pattern.matches(Group.PATTERN_STRING_LIST, parentGroups)) {
                throw new ApiException("群组ids格式不正确, 格式为: 1,2,3");
            }
            for (Integer parentId : Func.toIntList(parentGroups)) {
                if (!existGroup(parentId)) {
                    throw new ApiException("父群组不存在");
                }
                if (groupId != null) {
                    if (groupId != null && groupId.equals(parentId)) {
                        throw new ApiException("不能指定自己为父群组");
                    }
                    List<GroupTreeVo> groupAndParentList = selectAllGroupAndParent();
                    //校验是否为子群组
                    isChildrenGroup(groupAndParentList, groupId, parentId);
                }
            }
        }
        //校验管理员是否存在
        String managers = group.getManagers();
        if (!StringUtils.isEmpty(managers) && !Pattern.matches(Group.PATTERN_STRING_LIST, managers)) {
            throw new ApiException("管理员ids格式不正确，格式为: 1,2,3");
        }
        for (Integer id : Func.toIntList(managers)) {
            if (!existUser(id)) {
                throw new ApiException("管理员用户不存在");
            }
        }
        return true;
    }

    @Override
    public boolean updateGroup(Group group) {
        //校验父群和子群格式是否正确
        checkParentGroupAndManager(group);
        //更新群
        updateById(group);
        Integer groupId = group.getId();
        String parentGroups = group.getParentGroups();
        //删除中间表
        parentGroupService.remove(Wrappers.<ParentGroup>lambdaQuery().eq(ParentGroup::getGroupId, groupId));
        //插入中间表
        for (Integer parentGroupId : Func.toIntList(parentGroups)) {
            //新增父群组
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setGroupId(groupId);
            parentGroup.setParentId(parentGroupId);
            parentGroupService.save(parentGroup);
        }
        return true;
    }

    @Override
    public List<Group> children(Group group) {
        Integer groupId = this.getOne(Condition.getQueryWrapper(group)).getId();
        List<ParentGroup> parentGroup = parentGroupService.list(Wrappers.<ParentGroup>lambdaQuery().eq(ParentGroup::getParentId, groupId));
        if (CollectionUtils.isEmpty(parentGroup)) {
            return null;
        }
        List<Integer> groupList = parentGroup.stream().map(ParentGroup::getGroupId).collect(Collectors.toList());
        List<Group> list = this.list(Wrappers.<Group>lambdaQuery().in(Group::getId, groupList));
        return list;
    }

    @Override
    public List<Group> select() {
        List<Group> groups = this.list();
        return groups;
    }

    /**
     * 递归构建树形下拉
     * @param groups
     * @param parentId
     * @return
     */
    public List<GroupTreeVo> buildTree(List<GroupTreeVo> groups, Integer parentId) {
        List<GroupTreeVo> tree = new ArrayList<GroupTreeVo>();

        for (GroupTreeVo group : groups) {
            //获取群组ID
            Integer id = group.getId();
            //获取群组父ID
            Integer pId = group.getParentId();

            if (pId.equals(parentId)) {
                List<GroupTreeVo> treeList = buildTree(groups, id);
                group.setChildren(treeList);
                tree.add(group);
            }
        }
        return tree;
    }

    /**
     * 递归校验是否是子群
     * @param groups 所有群
     * @param parentId 群ID
     * @param checkId 父群ID
     */
    public void isChildrenGroup(List<GroupTreeVo> groups, Integer parentId, Integer checkId) {
        for (GroupTreeVo group : groups) {
            //获取群组ID
            Integer id = group.getId();
            //获取群组父ID
            Integer pId = group.getParentId();
            if (pId.equals(parentId)) {
                isChildrenGroup(groups, id, checkId);
                if (id.equals(checkId)) {
                    throw new ApiException("不能设置子群为父ID");
                }
            }
        }
    }

    /**
     * 临时解决Integer为null时, 返回前台为-1的问题
     */
    public static void modifyObject(Object model) {
        // TODO: 2020/2/27
        //获取实体类的所有属性，返回Field数组
        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取属性的类型
            field.setAccessible(true);
            String type = field.getGenericType().toString();
            if (type.equals("class java.lang.Integer")) {
                try {
                    Object obj = field.get(model);
                    if (obj != null && Integer.valueOf(obj.toString()).equals(-1)) {
                        field.set(model, null);
                    }
                } catch (IllegalAccessException e) {
                    throw new ApiException("-1转null失败");
                }
            }
        }
    }
}

