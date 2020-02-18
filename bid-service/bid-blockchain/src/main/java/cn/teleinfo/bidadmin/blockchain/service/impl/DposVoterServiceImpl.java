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

import cn.teleinfo.bidadmin.blockchain.entity.DposVoter;
import cn.teleinfo.bidadmin.blockchain.vo.DposVoterVO;
import cn.teleinfo.bidadmin.blockchain.mapper.DposVoterMapper;
import cn.teleinfo.bidadmin.blockchain.service.IDposVoterService;
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
 * @since 2019-10-17
 */
@Service
public class DposVoterServiceImpl extends ServiceImpl<DposVoterMapper, DposVoter> implements IDposVoterService {

	@Override
	public IPage<DposVoterVO> selectDposVoterPage(IPage<DposVoterVO> page, DposVoterVO dposVoter,String owner) {
		return page.setRecords(baseMapper.selectDposVoterPage(page, dposVoter,owner));
	}

	@Override
	public int saveBatchVoters(List<DposVoter> voters) {
		return baseMapper.saveBatchVoters(voters);
	}

	@Override
	public void saveOrUpdateVoter(DposVoter voter) {
		QueryWrapper<DposVoter> voteQueryWrapper = new QueryWrapper<>();
		voteQueryWrapper.eq("Owner", voter.getOwner());
		voteQueryWrapper.last("limit 1");

		DposVoter trustanchorVote = baseMapper.selectOne(voteQueryWrapper);

		if (trustanchorVote == null) {
			baseMapper.insert(voter);
		} else {
			voter.setId(trustanchorVote.getId());
			baseMapper.updateById(voter);
		}
	}

}
