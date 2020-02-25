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
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Service
@AllArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    private IParentGroupService parentGroupService;
    private GroupMapper groupMapper;
    private IChildrenGroupService childrenGroupService;
    private IUserGroupService userGroupService;
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
    @Transactional
    public boolean addUser(UserGroup userGroup) {
        if (getById(userGroup.getGroupId()) == null) {
            throw new ApiException("群组不存在");
        }
        // TODO: 2020/2/23 用户校验后期再做
        //添加用户
        userGroupService.save(userGroup);
        //添加日志
        GroupLog groupLog = new GroupLog();
        groupLog.setUserId(userGroup.getUserId());
        groupLog.setGroupId(userGroup.getGroupId());
        groupLog.setEventType(GroupLog.NEW_USER);
        groupLogService.save(groupLog);
        return true;
    }

    @Override
    @Transactional
    public boolean delUser(UserGroup userGroup) {
        if (getById(userGroup.getGroupId()) == null) {
            throw new ApiException("群组不存在");
        }
        // TODO: 2020/2/23 用户校验后期再做
        //删除用户
        userGroupService.remove(Condition.getQueryWrapper(userGroup));
        //添加日志
        GroupLog groupLog = new GroupLog();
        groupLog.setUserId(userGroup.getUserId());
        groupLog.setGroupId(userGroup.getGroupId());
        groupLog.setEventType(GroupLog.DELETE_USER);
        groupLogService.save(groupLog);
        return true;
    }

    @Override
    public List<HashMap> tree() {
        return groupMapper.tree();
    }

    @Override
    public Group detail(Group group) {
        Group detail = this.getOne(Condition.getQueryWrapper(group));
        if (detail == null) {
            return null;
        }
        LambdaQueryWrapper<ParentGroup> parentGroupLambdaQueryWrapper = Wrappers.<ParentGroup>lambdaQuery().
                eq(ParentGroup::getGroupId, group.getId());
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
    public List<HashMap> treeChildren() {
        List<HashMap> tree = tree();
        List<HashMap> maps = buildTree(tree, 0);
        return maps;
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
    public List<HashMap> buildTree(List<HashMap> groups, Integer parentId) {
        List<HashMap> tree = new ArrayList<HashMap>();

        for (HashMap group : groups) {
            int id = (int) group.get("id");
            int pId = (int) group.get("parentId");

            if (parentId == pId) {
                List<HashMap> treeList = buildTree(groups, id);
                group.put("children", treeList);
                tree.add(group);
            }
        }
            return tree;
    }

    @Override
    @Transactional
    public boolean updateGroupMiddleTable(Group group) {
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

