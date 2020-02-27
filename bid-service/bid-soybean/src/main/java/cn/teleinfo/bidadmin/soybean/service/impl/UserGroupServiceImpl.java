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
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
import cn.teleinfo.bidadmin.soybean.mapper.UserGroupMapper;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *  服务实现类
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
    public void checkUserGroup(UserGroup userGroup) {
		Group group = groupService.getById(userGroup.getGroupId());
		if (group == null) {
			throw new ApiException("群组不存在");
		}
		List<UserGroup> userGroups = this.list(Wrappers.<UserGroup>lambdaQuery().eq(UserGroup::getGroupId, userGroup.getGroupId()).eq(UserGroup::getUserId, userGroup.getUserId()));
		if (!CollectionUtils.isEmpty(userGroups)) {
			throw new ApiException("用户已添加此群组");
		}
    }

    @Override
    public boolean managerRemoveUser(UserGroupVO userGroup) {
		Integer managerId = userGroup.getManagerId();
		if (managerId == null) {
			new ApiException("管理员ID不能为空");
		}
		//查询管操作人是否有权限
		Integer groupId = userGroup.getGroupId();
		Group group = groupService.getById(groupId);
		Integer userId = userGroup.getUserId();
		if (groupService.isGroupManger(groupId, userId)) {

		}
		return false;
    }

}
