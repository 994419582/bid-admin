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
package cn.teleinfo.bidadmin.blockchain.service.impl;

import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorStake;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorStakeVO;
import cn.teleinfo.bidadmin.blockchain.mapper.TrustanchorStakeMapper;
import cn.teleinfo.bidadmin.blockchain.service.ITrustanchorStakeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-10-29
 */
@Service
public class TrustanchorStakeServiceImpl extends ServiceImpl<TrustanchorStakeMapper, TrustanchorStake> implements ITrustanchorStakeService {

	@Override
	public IPage<TrustanchorStakeVO> selectTrustanchorStakePage(IPage<TrustanchorStakeVO> page, TrustanchorStakeVO trustanchorStake) {
		return page.setRecords(baseMapper.selectTrustanchorStakePage(page, trustanchorStake));
	}

	@Override
	public void saveOrUpdateTrustanchorStake(List<TrustanchorStake> stakes) {

		for (TrustanchorStake stake : stakes) {

			QueryWrapper<TrustanchorStake> manageQueryWrapper = new QueryWrapper<>();
			manageQueryWrapper.eq("owner", stake.getOwner());
			manageQueryWrapper.last("limit 1");

			TrustanchorStake trustanchorStake = baseMapper.selectOne(manageQueryWrapper);

			if (trustanchorStake == null) {
				baseMapper.insert(stake);
			} else {
				stake.setId(trustanchorStake.getId());
				baseMapper.updateById(stake);
			}
		}
	}

}
