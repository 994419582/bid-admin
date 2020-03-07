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
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import cn.teleinfo.bidadmin.soybean.mapper.UserMapper;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private IUserGroupService userGroupService;

	@Autowired
	private IGroupService groupService;

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user) {
		return page.setRecords(baseMapper.selectUserPage(page, user));
	}
	@Override
	public IPage<User> selectUserVOPage(IPage<User> page, Integer group) {
		return page.setRecords(baseMapper.selectUserVOPage(page, group));
	}

	@Override
	public User findByWechatId(String openid) {
		return baseMapper.selectOne(Wrappers.<User>query().eq("wechat_id", openid));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submit(User user) {
		if (user != null) {
			User userStore = findByWechatId(user.getWechatId());
			// 新增
			if (userStore == null) {
				this.saveOrUpdate(user);
			} else if (userStore != null && userStore.getId().equals(user.getId())) {
				this.updateById(user);
			} else if (userStore != null && !userStore.getId().equals(user.getId())) {
				// 疑似重复数据
				this.removeById(userStore);
				this.saveOrUpdate(user);
			}
			return true;
		}
		return false;
	}

    @Override
	@Transactional
    public boolean removeUserByIds(List<Integer> ids) {
		//查询用户加入的群组
		for (Integer userId : ids) {
			LambdaQueryWrapper<UserGroup> queryWrapper = Wrappers.<UserGroup>lambdaQuery().
					eq(UserGroup::getUserId, userId).eq(UserGroup::getStatus, UserGroup.NORMAL);
			List<UserGroup> userGroups = userGroupService.list(queryWrapper);
			//用户退群
			for (UserGroup userGroup : userGroups) {
				Integer groupId = userGroup.getGroupId();
				//删除用户拥有的所有组织的管理员权限
				List<Group> userManageGroups = groupService.getUserManageGroups(userId);
				for (Group group : userManageGroups) {
					String managers = group.getManagers();
					ArrayList<Integer> managerList = new ArrayList<>(Func.toIntList(managers));
					//Group表移除此管理员
					managerList.remove(userId);
					String newManagers = StringUtils.join(managerList, ",");
					group.setManagers(newManagers);
					groupService.updateById(group);
				}
				//删除用户拥有的所有组织数据管理员权限
				List<Group> userDataManageGroups = groupService.getUserDataManageGroups(userId);
				for (Group group : userDataManageGroups) {
					String dataManagers = group.getDataManagers();
					ArrayList<Integer> dataManagerList = new ArrayList<>(Func.toIntList(dataManagers));
					//Group表移除此管理员
					dataManagerList.remove(userId);
					String newDataManagers = StringUtils.join(dataManagerList, ",");
					group.setDataManagers(newDataManagers);
					groupService.updateById(group);
				}
				//更新状态为已删除
				LambdaUpdateWrapper<UserGroup> userGroupLambdaQueryWrapper = Wrappers.<UserGroup>lambdaUpdate().
						eq(UserGroup::getUserId, userId).
						eq(UserGroup::getGroupId, groupId).
						set(UserGroup::getStatus, UserGroup.DELETE);
				userGroupService.update(userGroupLambdaQueryWrapper);
				//减少群人数
				userGroupService.motifyUserAccount(groupId, -1);
			}
		}
		//删除所有用户
		removeByIds(ids);
		return true;
    }
}
