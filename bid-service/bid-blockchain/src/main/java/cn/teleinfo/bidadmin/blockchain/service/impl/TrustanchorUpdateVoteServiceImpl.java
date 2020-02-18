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

import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorUpdateVote;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorUpdateVoteVO;
import cn.teleinfo.bidadmin.blockchain.mapper.TrustanchorUpdateVoteMapper;
import cn.teleinfo.bidadmin.blockchain.service.ITrustanchorUpdateVoteService;
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
public class TrustanchorUpdateVoteServiceImpl extends ServiceImpl<TrustanchorUpdateVoteMapper, TrustanchorUpdateVote> implements ITrustanchorUpdateVoteService {

	@Override
	public IPage<TrustanchorUpdateVoteVO> selectTrustanchorUpdateVotePage(IPage<TrustanchorUpdateVoteVO> page, TrustanchorUpdateVoteVO trustanchorUpdateVote) {
		return page.setRecords(baseMapper.selectTrustanchorUpdateVotePage(page, trustanchorUpdateVote));
	}

	@Override
	public void saveOrUpdateTrustanchorUpdateVote(List<TrustanchorUpdateVote> votes) {

		for (TrustanchorUpdateVote vote : votes) {

			QueryWrapper<TrustanchorUpdateVote> voteQueryWrapper = new QueryWrapper<>();
			voteQueryWrapper.eq("ProposeId", vote.getProposeId());
			voteQueryWrapper.eq("votebid", vote.getVotebid());
			voteQueryWrapper.last("limit 1");

			TrustanchorUpdateVote trustanchorUpdateVote = baseMapper.selectOne(voteQueryWrapper);

			if (trustanchorUpdateVote == null) {
				baseMapper.insert(vote);
			} else {
				vote.setId(trustanchorUpdateVote.getId());
				baseMapper.updateById(vote);
			}
		}
	}
}
