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

import cn.teleinfo.bidadmin.soybean.entity.GroupLog;
import cn.teleinfo.bidadmin.soybean.vo.GroupLogVO;
import cn.teleinfo.bidadmin.soybean.mapper.GroupLogMapper;
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Service
public class GroupLogServiceImpl extends ServiceImpl<GroupLogMapper, GroupLog> implements IGroupLogService {

	@Override
	public IPage<GroupLogVO> selectGroupLogPage(IPage<GroupLogVO> page, GroupLogVO groupLog) {
		return page.setRecords(baseMapper.selectGroupLogPage(page, groupLog));
	}

    @Override
    public void addLog(Integer groupId, Integer userId, Integer eventType) {
		if (groupId == null) {
			throw new ApiException("用户ID不能为空");
		}
		if (userId == null) {
			throw new ApiException("部门ID不能为空");
		}
		GroupLog groupLog = new GroupLog();
		groupLog.setUserId(userId);
		groupLog.setGroupId(groupId);
		groupLog.setEventType(eventType);
		save(groupLog);
    }

}
