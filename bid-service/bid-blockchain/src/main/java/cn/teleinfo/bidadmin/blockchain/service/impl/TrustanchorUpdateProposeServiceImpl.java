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

import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorUpdatePropose;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorUpdateProposeVO;
import cn.teleinfo.bidadmin.blockchain.mapper.TrustanchorUpdateProposeMapper;
import cn.teleinfo.bidadmin.blockchain.service.ITrustanchorUpdateProposeService;
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
public class TrustanchorUpdateProposeServiceImpl extends ServiceImpl<TrustanchorUpdateProposeMapper, TrustanchorUpdatePropose> implements ITrustanchorUpdateProposeService {

	@Override
	public IPage<TrustanchorUpdateProposeVO> selectTrustanchorUpdateProposePage(IPage<TrustanchorUpdateProposeVO> page, TrustanchorUpdateProposeVO trustanchorUpdatePropose) {
		return page.setRecords(baseMapper.selectTrustanchorUpdateProposePage(page, trustanchorUpdatePropose));
	}

	@Override
	public void saveOrUpdateTrustanchorUpdatePropose(List<TrustanchorUpdatePropose> proposes) {

		for (TrustanchorUpdatePropose propose : proposes) {

			QueryWrapper<TrustanchorUpdatePropose> proposeQueryWrapper = new QueryWrapper<>();
			proposeQueryWrapper.eq("ProposeBid", propose.getProposeBid());
			proposeQueryWrapper.last("limit 1");

			TrustanchorUpdatePropose trustanchorUpdatePropose = baseMapper.selectOne(proposeQueryWrapper);

			if (trustanchorUpdatePropose == null) {
				baseMapper.insert(propose);
			} else {
				propose.setId(trustanchorUpdatePropose.getId());
				baseMapper.updateById(propose);
			}
		}
	}
}
