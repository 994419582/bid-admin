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
import cn.teleinfo.bidadmin.soybean.entity.GroupLog;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.mapper.UserGroupMapper;
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IGroupLogService groupLogService;

    @Override
    public IPage<UserGroupVO> selectUserGroupPage(IPage<UserGroupVO> page, UserGroupVO userGroup) {
        return page.setRecords(baseMapper.selectUserGroupPage(page, userGroup));
    }

    @Override
    public List<User> findUserByGroupId(Integer groupid) {
        return baseMapper.findUserByGroupId(groupid);
    }

    @Override
    public void checkAddUserGroup(UserGroup userGroup) {
        Group group = groupService.getGroupById(userGroup.getGroupId());
        if (group == null) {
            throw new ApiException("用户已退群");
        }
		List<UserGroup> userGroups = this.list(Wrappers.<UserGroup>lambdaQuery().
				eq(UserGroup::getGroupId, userGroup.getGroupId()).
				eq(UserGroup::getUserId, userGroup.getUserId()).
				eq(UserGroup::getStatus, UserGroup.NORMAL));
        if (!CollectionUtils.isEmpty(userGroups)) {
            throw new ApiException("用户已添加此群组");
        }
    }

    @Override
    @Transactional
    public boolean managerRemoveUser(UserGroupVO userGroup) {
        Integer managerId = userGroup.getManagerId();
        if (managerId == null) {
            throw new ApiException("管理员ID不能为空");
        }
        //查询管操作人是否有权限
        Integer groupId = userGroup.getGroupId();
        Integer userId = userGroup.getUserId();
        if (!groupService.existGroup(groupId)) {
            throw new ApiException("群组不存在");
        }
        if (!groupService.existUser(userId)) {
            throw new ApiException("用户不存在");
        }
		if (!existUserGroup(groupId, userId)) {
			throw new ApiException("用户不在此群");
		}
		if (getUserGroupStatus(groupId, userId).equals(UserGroup.DELETE)) {
            throw new ApiException("用户已退群");
        }
        if (groupService.isGroupManger(groupId, managerId) || groupService.isGroupCreater(groupId, managerId)) {
            LambdaUpdateWrapper<UserGroup> queryWrapper = Wrappers.<UserGroup>lambdaUpdate().
                    eq(UserGroup::getUserId, userId).
                    eq(UserGroup::getGroupId, groupId)
                    .set(UserGroup::getStatus, UserGroup.DELETE);
            //更改群状态
            this.update(queryWrapper);
            //减少群人数
            motifyUserAccount(groupId, -1);
            //管理员退群, Group表里移除这个管理员
            if (groupService.isGroupManger(groupId,userId)) {
                Group group = groupService.getGroupById(groupId);
                String managers = group.getManagers();
                ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
                //Group表移除此管理员
                managerList.remove(userId);
                String newManagers = StringUtils.join(managerList, ",");
                group.setManagers(newManagers);
                groupService.updateById(group);
            }
            groupLogService.addLog(groupId, managerId, GroupLog.MANAGER_DELETE_USER);
        } else {
            throw new ApiException("操作人无权限");
        }
        return true;
    }

    @Transactional
    public void motifyUserAccount(Integer groupId, Integer addAccount) {
        Group group = groupService.getGroupById(groupId);
        group.setUserAccount(group.getUserAccount() + addAccount);
        groupService.updateById(group);
    }

    @Override
    @Transactional
    public boolean quitGroup(UserGroup userGroup) {
        Integer groupId = userGroup.getGroupId();
        Integer userId = userGroup.getUserId();
        if (!groupService.existGroup(groupId)) {
            throw new ApiException("群组不存在");
        }
        if (!groupService.existUser(userId)) {
            throw new ApiException("用户不存在");
        }
		if (!existUserGroup(groupId, userId)) {
			throw new ApiException("用户不在此群");
		}
        if (getUserGroupStatus(groupId, userId).equals(UserGroup.DELETE)) {
            throw new ApiException("用户已退群");
        }
        if (groupService.isGroupCreater(groupId, userId)) {
            throw new ApiException("创建者不能退群,只能解散群");
        }
        //管理员退群, Group表里移除这个管理员
        if (groupService.isGroupManger(groupId,userId)) {
            Group group = groupService.getGroupById(groupId);
            String managers = group.getManagers();
            ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
            //Group表移除此管理员
            managerList.remove(userId);
            String newManagers = StringUtils.join(managerList, ",");
            group.setManagers(newManagers);
            groupService.updateById(group);
        }
        //更新状态为已删除
        LambdaUpdateWrapper<UserGroup> userGroupLambdaQueryWrapper = Wrappers.<UserGroup>lambdaUpdate().
                eq(UserGroup::getUserId, userId).
                eq(UserGroup::getGroupId, groupId).
                set(UserGroup::getStatus, UserGroup.DELETE);
        this.update(userGroupLambdaQueryWrapper);
        //减少群人数
        motifyUserAccount(groupId, -1);
        //添加日志
        groupLogService.addLog(groupId, userId, GroupLog.DELETE_USER);
        return true;
    }

    private Integer getUserGroupStatus(Integer groupId, Integer userId) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setUserId(userId);
        userGroup.setStatus(UserGroup.NORMAL);
        return getOne(Condition.getQueryWrapper(userGroup)).getStatus();
    }

    @Override
    @Transactional
    public boolean saveUserGroup(UserGroup userGroup) {
        Integer groupId = userGroup.getGroupId();
        Integer userId = userGroup.getUserId();
        if (!groupService.existGroup(groupId)) {
            throw new ApiException("群组不存在");
        }
        if (!groupService.existUser(userId)) {
            throw new ApiException("用户不存在");
        }
        if (userGroup.getStatus() != null) {
            throw new ApiException("新增时不能指定用户状态");
        }
        //检查用此群是否存在此用户,不存在则新增
        if (!existUserGroup(groupId, userId)) {
            //添加群组
            UserGroup newUserGroup = new UserGroup();
            newUserGroup.setUserId(userId);
            newUserGroup.setGroupId(groupId);
            newUserGroup.setStatus(UserGroup.NORMAL);
            this.save(newUserGroup);
            //增加群人数
            motifyUserAccount(groupId, 1);
            //添加日志
            groupLogService.addLog(groupId, userId, GroupLog.NEW_USER);
            return true;
        }
        Integer userGroupStatus = getUserGroupStatus(groupId, userId);
        if (userGroupStatus.equals(UserGroup.NORMAL)) {
            throw new ApiException("用户已加群,状态为正常");
        } else if (userGroupStatus.equals(UserGroup.APPROVAL_PENDING)) {
            throw new ApiException("用户已加群，状态待审核");
        } else {
            //更新状态为正常
            LambdaUpdateWrapper<UserGroup> userGroupLambdaQueryWrapper = Wrappers.<UserGroup>lambdaUpdate().
                    eq(UserGroup::getUserId, userId).
                    eq(UserGroup::getGroupId, groupId).
                    set(UserGroup::getStatus, UserGroup.NORMAL);
            this.update(userGroupLambdaQueryWrapper);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean removeUserGroupByIds(List<Integer> toIntList) {
        for (Integer id : toIntList) {
            UserGroup userGroup = getUserGroupById(id);
            if (userGroup == null) {
                throw new ApiException("用户已退群");
            }
            Integer groupId = userGroup.getGroupId();
            Integer userId = userGroup.getUserId();
            if (groupService.isGroupCreater(groupId, userId)) {
                throw new ApiException("不能移除群创建人");
            }
            //管理员退群, Group表里移除这个管理员
            if (groupService.isGroupManger(groupId, userId)) {
                Group group = groupService.getGroupById(groupId);
                String managers = group.getManagers();
                ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
                //Group表移除此管理员
                managerList.remove(userId);
                String newManagers = StringUtils.join(managerList, ",");
                group.setManagers(newManagers);
                groupService.updateById(group);
            }
            //修改状态
            userGroup.setStatus(UserGroup.DELETE);
            updateById(userGroup);
            //修改群人数
            motifyUserAccount(groupId, -1);
            //添加日志
            groupLogService.addLog(groupId, userId, GroupLog.DELETE_USER);
        }
        return true;
    }

    @Override
    public UserGroup getUserGroupById(Integer id) {
        if (id == null) {
            throw new ApiException("用户ID不能为Null");
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        userGroup.setStatus(UserGroup.NORMAL);
        return this.getOne(Condition.getQueryWrapper(userGroup));
    }

    public boolean existUserGroup(Integer groupId, Integer userId) {
        LambdaQueryWrapper<UserGroup> lambdaQuery = Wrappers.<UserGroup>lambdaQuery()
                .eq(UserGroup::getGroupId, groupId).
                        eq(UserGroup::getStatus, UserGroup.NORMAL).
                        eq(UserGroup::getUserId, userId);
        if (getOne(lambdaQuery) == null) {
            return false;
        }
        return true;
    }

}
