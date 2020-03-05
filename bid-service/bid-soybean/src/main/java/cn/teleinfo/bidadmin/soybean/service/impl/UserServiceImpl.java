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

import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import cn.teleinfo.bidadmin.soybean.mapper.UserMapper;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

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
		//删除所有用户
		removeByIds(ids);
		//查询用户加入的群组
		for (Integer userId : ids) {
			LambdaQueryWrapper<UserGroup> queryWrapper = Wrappers.<UserGroup>lambdaQuery().
					eq(UserGroup::getUserId, userId).eq(UserGroup::getStatus, UserGroup.NORMAL);
			List<UserGroup> userGroups = userGroupService.list(queryWrapper);
			//用户退群
			for (UserGroup userGroup : userGroups) {
				userGroupService.quitGroup(userGroup);
			}
		}
		return true;
    }
}
