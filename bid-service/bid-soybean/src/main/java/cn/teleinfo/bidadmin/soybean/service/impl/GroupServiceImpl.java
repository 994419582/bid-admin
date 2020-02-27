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

import cn.teleinfo.bidadmin.soybean.entity.*;
import cn.teleinfo.bidadmin.soybean.mapper.GroupMapper;
import cn.teleinfo.bidadmin.soybean.service.*;
import cn.teleinfo.bidadmin.soybean.vo.GroupTreeVo;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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

    //逗号分隔类型校验
    public static final String STRING_LIST = "\\d+(,\\d+)*";

    @Override
    public IPage<GroupVO> selectGroupPage(IPage<GroupVO> page, GroupVO group) {
        return page.setRecords(baseMapper.selectGroupPage(page, group));
    }

    @Override
    @Transactional
    public boolean saveGroupMiddleTable(Group group) {
        if (StringUtils.isEmpty(group.getParentGroups())) {
            group.setParentGroups(String.valueOf(Group.TOP_PARENT_ID));
        }
        Integer groupId = group.getId();
        String parentGroups = group.getParentGroups();
        //父群组为空则更新群组，并删除中间表
        //父群组不为空则更新群组，删除中间表，重新添加重建表
        if (StringUtils.isEmpty(parentGroups)) {
            //保存群组
            save(group);
        } else {
            //校验parentGroupIds格式
            if (!checkParentGroups(parentGroups, groupId)) {
                throw new ApiException("parentGroupIds格式错误");
            }
            //保存群组
            save(group);
            //保存中间表
            saveMiddleTable(group);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveOrUpdateGroupMiddleTable(Group group) {
        if (StringUtils.isEmpty(group.getParentGroups())) {
            group.setParentGroups(String.valueOf(Group.TOP_PARENT_ID));
        }
        //新增群组
        if (group.getId() == null) {
            saveGroupMiddleTable(group);
        } else {
            //更新群
            updateGroupMiddleTable(group);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean removeGroupMiddleTableById(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ApiException("ids参数值为空");
        }
        for (Integer id : ids) {
            //删除群组
            removeById(id);
            //删除中间表
            removeMiddleTableById(id);
        }
        return true;
    }

    /**
     * 删除中间表
     *
     * @param id
     */
    @Transactional
    public void removeMiddleTableById(Integer id) {
        //删除父群组
        parentGroupService.remove(Wrappers.<ParentGroup>lambdaQuery().eq(ParentGroup::getGroupId, id));
        //删除子群组
        childrenGroupService.remove(Wrappers.<ChildrenGroup>lambdaQuery().eq(ChildrenGroup::getChildId, id));
    }

    @Override
    public List<GroupTreeVo> tree() {
        return groupMapper.tree();
    }

    @Override
    public Group detail(Group group) {
        Integer groupId = group.getId();
        Group detail = this.getById(groupId);
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
        List<GroupTreeVo> tree = tree();
        List<GroupTreeVo> maps = buildTree(tree, 0);
        return maps;
    }

    @Override
    public List<GroupTreeVo> treeUser(Integer userId) {
        List<GroupTreeVo> tree = this.treeChildren();

        ArrayList<GroupTreeVo> groupTreeVos = new ArrayList<>();
        //一级树
        for (GroupTreeVo groupTreeVo : tree) {
            List<Integer> groupAllManager = getGroupAllManager(groupTreeVo);
            if (groupAllManager.contains(userId)) {
                groupTreeVos.add(groupTreeVo);
            }
        }
        //二级树
        for (GroupTreeVo groupTreeVo : tree) {
            for (GroupTreeVo child : groupTreeVo.getChildren()) {
                List<Integer> childAllManager = getGroupAllManager(child);
                if (childAllManager.contains(userId)) {
                    //遍历顶级用户是否有权限
                    boolean b = groupTreeVos.stream().anyMatch(groupTreeVo1 -> {
                        return groupTreeVo1.getId().equals(child.getParentId()) || groupTreeVo1.getId().equals(child.getId());
                    });
                    if (!b) {
                        groupTreeVos.add(child);
                    }
                }
            }
        }
        //三级树
        for (GroupTreeVo groupTreeVo : tree) {
            for (GroupTreeVo child : groupTreeVo.getChildren()) {
                for (GroupTreeVo childChild : child.getChildren()) {
                    List<Integer> childAllManager = getGroupAllManager(child);
                    if (childAllManager.contains(userId)) {
                        //遍历顶级用户是否有权限
                        boolean flag = false;
                        for (GroupTreeVo treeVo : groupTreeVos) {
                            //顶级是否有父ID
                            if (child.getParentId().equals(treeVo.getId()) || child.getId().equals(treeVo.getId())) {
                                flag = true;
                            }
                            //二级是否有父ID
                            for (GroupTreeVo treeVoChild : treeVo.getChildren()) {
                                if (child.getId().equals(treeVoChild.getId()) || child.getParentId().equals(treeVoChild.getId())) {
                                    flag = true;
                                }
                            }
                        }
                        if (!flag) {
                            groupTreeVos.add(child);
                        }
                    }
                }
            }
        }


        return groupTreeVos;
    }

    @Override
    public boolean isGroupManger(Integer groupId, Integer userId) {
        Group group = this.getById(groupId);
        Func.toIntList(group.getManagers());
        return false;
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
     * 根据用户递归构建树形下拉
     * @param groups
     * @param parentId
     * @return
     */
    public List<GroupTreeVo> buildTreeUser(List<GroupTreeVo> groups, Integer parentId,Integer userId) {
        List<GroupTreeVo> tree = new ArrayList<GroupTreeVo>();

        for (GroupTreeVo groupTreeVo : groups) {
            Integer id = groupTreeVo.getId();
            Integer pId = groupTreeVo.getParentId();

            if (parentId == pId) {
                List<GroupTreeVo> treeList = buildTreeUser(groups, id, userId);
                //校验用户对此群组是否有权限
                List<Integer> managerList = getGroupAllManager(groupTreeVo);
                if (managerList.contains(userId)) {
                    groupTreeVo.setChildren(treeList);
                    groupTreeVo.setPermission(true);
                    tree.add(groupTreeVo);
                } else
                    //递归查看该群组对应的父群主是否有权限
                    if (checkParentGroupPermission(groups, id, userId) > 0) {
                        groupTreeVo.setChildren(treeList);
                        groupTreeVo.setPermission(true);
                        tree.add(groupTreeVo);
                    } else {
                        //递归校验该群组所有子群是否有管理权限
                        if (checkParentChildrenPermission(groups, id, userId) > 0) {
                            groupTreeVo.setChildren(treeList);
                            groupTreeVo.setPermission(false);
                            tree.add(groupTreeVo);
                        }
                    }
            }
        }
        return tree;
    }

    private Integer checkParentChildrenPermission(List<GroupTreeVo> groups, Integer id, Integer userId) {
        //有权限的子群组个数
        Integer count = 0;
        List<GroupTreeVo> childrenList = groups.stream().filter(groupTreeVo -> groupTreeVo.getParentId().equals(id)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childrenList)) {
            List<GroupTreeVo> groupTreeVos = groups.stream().filter(groupTreeVo -> groupTreeVo.getId().equals(id)).collect(Collectors.toList());
            for (GroupTreeVo groupTreeVo : groupTreeVos) {
                List<Integer> managers = getGroupAllManager(groupTreeVo);
                List<GroupTreeVo> childrens = groupTreeVo.getChildren();
                for (GroupTreeVo children : childrens) {
                    count = count + checkParentGroupPermission(groups, children.getId(), userId);
                    if (managers.contains(userId)) {
                        count = count + 1;
                    }
                }
            }
            return count;
        } else {
            return 0;
        }
    }

    private Integer checkParentGroupPermission(List<GroupTreeVo> groups, Integer id, Integer userId) {
        //有权限的父群组个数
        Integer count = 0;
        if (!Group.TOP_PARENT_ID.equals(id)) {
            List<GroupTreeVo> groupTreeVos = groups.stream().filter(groupTreeVo -> groupTreeVo.getId().equals(id)).collect(Collectors.toList());
            for (GroupTreeVo groupTreeVo : groupTreeVos) {
                List<Integer> managers = getGroupAllManager(groupTreeVo);
                count = count + checkParentGroupPermission(groups, groupTreeVo.getParentId(), userId);
                if (managers.contains(userId)) {
                    count = count + 1;
                }
            }
            return count;
        } else {
            return 0;
        }
    }

    /**
     * 获取群组所有管理者
     */
    private List<Integer> getGroupAllManager(GroupTreeVo groupTreeVo) {
        //校验用户对此群组是否有权限
        String managers = groupTreeVo.getManagers();
        Integer createUser = groupTreeVo.getCreateUser();
        List<Integer> managerList = new ArrayList<>();

        if (!StringUtils.isEmpty(managers)) {
            List<Integer> collect = Arrays.stream(managers.split(","))
                    .map(s -> Integer.valueOf(s)).
                            collect(Collectors.toList());
            managerList.addAll(collect);
        }
        managerList.add(createUser);
        return managerList;
    }

    @Override
    @Transactional
    public boolean updateGroupMiddleTable(Group group) {
        if (StringUtils.isEmpty(group.getParentGroups())) {
            group.setParentGroups(String.valueOf(Group.TOP_PARENT_ID));
        }
        Integer groupId = group.getId();
        if (getById(groupId) == null) {
            throw new ApiException("群组不存在");
        }
        String parentGroups = group.getParentGroups();
        //父群组为空则更新群组，并删除中间表
        //父群组不为空则更新群组，删除中间表，重新添加重建表
        if (StringUtils.isEmpty(parentGroups)) {
            //更新群组
            updateById(group);
            //删除中间表
            removeMiddleTableById(groupId);
        } else {
            checkParentGroups(parentGroups, groupId);
            //更新群组
            updateById(group);
            //删除中间表
            removeMiddleTableById(groupId);
            //保存中间表
            saveMiddleTable(group);
        }
        return true;
    }

    /**
     * 校验parentGroups
     *
     * @param parentGroups
     * @param groupId
     * @return
     */
    public boolean checkParentGroups(String parentGroups, Integer groupId) {
        //校验parentGroupIds格式
        if (!Pattern.matches(STRING_LIST, parentGroups)) {
            throw new ApiException("父群组格式错误");
        }
        //转集合类型
        List<Integer> parentGroupIds = Arrays.stream(parentGroups.split(",")).
                map(Integer::valueOf).
                collect(Collectors.toList());
        //判断父群组是否存在
        for (Integer parentGroupId : parentGroupIds) {
            if (getById(parentGroupId) == null) {
                throw new ApiException("父群组ID没有对应的群组");
            }
            //父群组ID不能等于群组ID
            if (parentGroupId.equals(groupId)) {
                throw new ApiException("父群组ID不能等于群组ID");
            }
        }
        return true;
    }

    /**
     * 保存中间表
     *
     * @param group
     * @return
     */
    @Transactional
    public boolean saveMiddleTable(Group group) {
        Integer groupId = group.getId();
        String parentGroups = group.getParentGroups();
        //转集合类型
        List<Integer> parentGroupIds = Arrays.stream(parentGroups.split(",")).
                map(Integer::valueOf).
                collect(Collectors.toList());
        for (Integer parentGroupId : parentGroupIds) {
            //新增父群组
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setGroupId(groupId);
            parentGroup.setParentId(parentGroupId);
            parentGroupService.save(parentGroup);
            //新增子群组
            ChildrenGroup childrenGroup = new ChildrenGroup();
            childrenGroup.setChildId(groupId);
            childrenGroup.setGroupId(parentGroupId);
            childrenGroupService.save(childrenGroup);
        }
        return true;
    }

    /**
     * 临时解决Integer为null时, 返回前台为-1的问题
     */
    public static void modifyObject(Object model) {
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

