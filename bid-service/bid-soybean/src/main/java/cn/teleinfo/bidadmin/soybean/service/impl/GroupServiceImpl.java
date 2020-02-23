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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Collections;
import org.springblade.core.mp.support.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public IPage<GroupVO> selectGroupPage(IPage<GroupVO> page, GroupVO group) {
        return page.setRecords(baseMapper.selectGroupPage(page, group));
    }

    @Override
    @Transactional
    public boolean saveGroupMiddleTable(Group group) {
        //未指定父群组, 直接保存群组
        Integer parentId = group.getParentGroup();
        if (parentId == null || parentId < 1) {
            return save(group);
        }
        //校验父ID是否存在
        if (getById(parentId) == null) {
            return false;
        }
        //保存群组
        save(group);
        //获取群ID
        Integer groupId = group.getId();
        //保存父群组
        ParentGroup parentGroup = new ParentGroup();
        parentGroup.setGroupId(groupId);
        parentGroup.setParentId(parentId);
        parentGroupService.save(parentGroup);
        //保存子群组
        ChildrenGroup childrenGroup = new ChildrenGroup();
        childrenGroup.setChildId(groupId);
        childrenGroup.setGroupId(parentId);
        childrenGroupService.save(childrenGroup);
        return true;
    }


    @Override
    @Transactional
    public boolean saveOrUpdateGroupMiddleTable(Group group) {
        //新增群组
        if (group.getId() == null) {
            saveGroupMiddleTable(group);
        } else {
            //更新群
            group.setUpdateTime(null);
            updateGroupMiddleTable(group);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean removeGroupMiddleTableById(List<Integer> ids) {
        if (Collections.isEmpty(ids)) {
            return false;
        }
        for (Integer id : ids) {
            //删除群组
            removeById(id);
            //删除父群组
            parentGroupService.remove(Wrappers.<ParentGroup>lambdaQuery().eq(ParentGroup::getGroupId, id));
            //删除子群组
            childrenGroupService.remove(Wrappers.<ChildrenGroup>lambdaQuery().eq(ChildrenGroup::getChildId, id));
        }
        return true;
    }

    @Override
    public boolean addUser(UserGroup userGroup) {
        if (getById(userGroup.getGroupId()) == null) {
            return false;
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
    public boolean delUser(UserGroup userGroup) {
        if (getById(userGroup.getGroupId()) == null) {
            return false;
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
    @Transactional
    public boolean updateGroupMiddleTable(Group group) {
        //如果用户去掉父Id则删除父群组和子群组
        Integer parentId = group.getParentGroup();
        if (parentId == null || parentId < 1) {
            //更新群组
            updateById(group);
            //删除父群主
            Integer groupId = group.getId();
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setGroupId(groupId);
            parentGroupService.remove(Condition.getQueryWrapper(parentGroup));
            //删除子群主
            ChildrenGroup childrenGroup = new ChildrenGroup();
            childrenGroup.setChildId(groupId);
            childrenGroupService.remove(Condition.getQueryWrapper(childrenGroup));
            return true;
        }

        //校验父ID是否存在
        if (getById(parentId) == null) {
            return false;
        }
        //更新群组
        updateById(group);
        //获取群组ID
        Integer groupId = group.getId();

        //更新实体
        ParentGroup parentGroup = new ParentGroup();
        parentGroup.setGroupId(groupId);
        parentGroup.setParentId(parentId);
        //父群组查询条件
        LambdaQueryWrapper<ParentGroup> parentGroupLambdaQueryWrapper = Wrappers.<ParentGroup>lambdaQuery().
                eq(ParentGroup::getGroupId, groupId);
        //子群组更新条件
        LambdaUpdateWrapper<ParentGroup> parentGroupLambdaUpdateWrapper = Wrappers.<ParentGroup>lambdaUpdate().
                eq(ParentGroup::getGroupId, groupId);
        //不存在父群主则创建父群组, 否则更新
        if (parentGroupService.getOne(parentGroupLambdaQueryWrapper) == null) {
            parentGroupService.save(parentGroup);
        } else {
            parentGroupService.update(parentGroup, parentGroupLambdaUpdateWrapper);
        }

        //更新实体
        ChildrenGroup childrenGroup = new ChildrenGroup();
        childrenGroup.setChildId(groupId);
        childrenGroup.setGroupId(parentId);
        //父群组查询条件
        LambdaQueryWrapper<ChildrenGroup> childrenGroupLambdaQueryWrapper = Wrappers.<ChildrenGroup>lambdaQuery().
                eq(ChildrenGroup::getChildId, groupId);
        //子群组更新条件
        LambdaUpdateWrapper<ChildrenGroup> childrenGroupLambdaUpdateWrapper = Wrappers.<ChildrenGroup>lambdaUpdate().
                eq(ChildrenGroup::getChildId, groupId);
        //不存在子群主则创建子群组, 否则更新
        if (childrenGroupService.getOne(childrenGroupLambdaQueryWrapper) == null) {
            childrenGroupService.save(childrenGroup);
        } else {
            childrenGroupService.update(childrenGroup, childrenGroupLambdaUpdateWrapper);
        }
        return true;
    }

}
