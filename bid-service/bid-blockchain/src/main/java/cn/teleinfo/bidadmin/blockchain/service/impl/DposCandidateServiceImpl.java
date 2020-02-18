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

import cn.teleinfo.bidadmin.blockchain.entity.DposCandidate;
import cn.teleinfo.bidadmin.blockchain.vo.DposCandidateVO;
import cn.teleinfo.bidadmin.blockchain.mapper.DposCandidateMapper;
import cn.teleinfo.bidadmin.blockchain.service.IDposCandidateService;
import cn.teleinfo.bidadmin.blockchain.vo.DposVoterVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-10-17
 */
@Service
public class DposCandidateServiceImpl extends ServiceImpl<DposCandidateMapper, DposCandidate> implements IDposCandidateService {

	@Override
	public IPage<DposCandidateVO> selectDposCandidatePage(Page<DposCandidateVO> page, Integer active) {
		return page.setRecords(baseMapper.selectDposCandidatePage(page, active));
	}

	@Override
	public int saveBatchCandidates(List<DposCandidate> candidates) {
		return baseMapper.saveBatchCandidates(candidates);
	}

	@Override
	public void saveOrUpdateCandidate(DposCandidate candidate) {

		QueryWrapper<DposCandidate> voteQueryWrapper = new QueryWrapper<>();
		voteQueryWrapper.eq("Owner", candidate.getOwner());
		voteQueryWrapper.last("limit 1");

		DposCandidate trustanchorVote = baseMapper.selectOne(voteQueryWrapper);

		if (trustanchorVote == null) {
			baseMapper.insert(candidate);
		} else {
			candidate.setId(trustanchorVote.getId());
			baseMapper.updateById(candidate);
		}
	}

}
