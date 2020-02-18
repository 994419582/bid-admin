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
package cn.teleinfo.bidadmin.app.service.impl;

import cn.teleinfo.bidadmin.app.api.BidFaceApi;
import cn.teleinfo.bidadmin.app.entity.UserFace;
import cn.teleinfo.bidadmin.app.vo.UserFaceVO;
import cn.teleinfo.bidadmin.app.mapper.UserFaceMapper;
import cn.teleinfo.bidadmin.app.service.IUserFaceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-11-27
 */
@Service
public class UserFaceServiceImpl extends ServiceImpl<UserFaceMapper, UserFace> implements IUserFaceService {

	@Autowired
	private BidFaceApi api;

	@Override
	public IPage<UserFaceVO> selectUserFacePage(IPage<UserFaceVO> page, UserFaceVO userFace) {
		return page.setRecords(baseMapper.selectUserFacePage(page, userFace));
	}

	@Override
	public boolean search(String bidAddress, String imagePath) {
		QueryWrapper<UserFace> voteQueryWrapper = new QueryWrapper<>();
		voteQueryWrapper.eq("bid", bidAddress);
		voteQueryWrapper.last("limit 1");

		UserFace face = baseMapper.selectOne(voteQueryWrapper);

		if (face == null) {
			return false;
		}

		return api.search(bidAddress, imagePath);
	}

	@Override
	public boolean search(String bidAddress) {
		QueryWrapper<UserFace> voteQueryWrapper = new QueryWrapper<>();
		voteQueryWrapper.eq("bid", bidAddress);
		voteQueryWrapper.last("limit 1");

		UserFace face = baseMapper.selectOne(voteQueryWrapper);

		if (face == null) {
			return false;
		}
        return true;
	}

}
