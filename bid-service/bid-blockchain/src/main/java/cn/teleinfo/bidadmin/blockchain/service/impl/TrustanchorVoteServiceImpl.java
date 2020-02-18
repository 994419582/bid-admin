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

import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorVote;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorVoteVO;
import cn.teleinfo.bidadmin.blockchain.mapper.TrustanchorVoteMapper;
import cn.teleinfo.bidadmin.blockchain.service.ITrustanchorVoteService;
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
public class TrustanchorVoteServiceImpl extends ServiceImpl<TrustanchorVoteMapper, TrustanchorVote> implements ITrustanchorVoteService {

	@Override
	public IPage<TrustanchorVoteVO> selectTrustanchorVotePage(IPage<TrustanchorVoteVO> page, TrustanchorVoteVO trustanchorVote) {
		return page.setRecords(baseMapper.selectTrustanchorVotePage(page, trustanchorVote));
	}

	@Override
	public void saveOrUpdateTrustanchorVote(List<TrustanchorVote> votes) {

		for (TrustanchorVote vote : votes) {

			QueryWrapper<TrustanchorVote> voteQueryWrapper = new QueryWrapper<>();
			voteQueryWrapper.eq("TrustAnchor", vote.getTrustAnchor());
			voteQueryWrapper.eq("Voter", vote.getVoter());
			voteQueryWrapper.last("limit 1");

			TrustanchorVote trustanchorVote = baseMapper.selectOne(voteQueryWrapper);

			if (trustanchorVote == null) {
				baseMapper.insert(vote);
			} else {
				vote.setId(trustanchorVote.getId());
				baseMapper.updateById(vote);
			}
		}
	}
}
