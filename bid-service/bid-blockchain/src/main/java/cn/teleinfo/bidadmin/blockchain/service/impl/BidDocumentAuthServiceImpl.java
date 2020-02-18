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

import cn.teleinfo.bidadmin.blockchain.entity.BidDocumentAuth;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentAuthVO;
import cn.teleinfo.bidadmin.blockchain.mapper.BidDocumentAuthMapper;
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentAuthService;
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
public class BidDocumentAuthServiceImpl extends ServiceImpl<BidDocumentAuthMapper, BidDocumentAuth> implements IBidDocumentAuthService {

	@Override
	public IPage<BidDocumentAuthVO> selectBidDocumentAuthPage(IPage<BidDocumentAuthVO> page, BidDocumentAuthVO bidDocumentAuth) {
		return page.setRecords(baseMapper.selectBidDocumentAuthPage(page, bidDocumentAuth));
	}

	@Override
	public int saveBatchDocumentAuths(List<BidDocumentAuth> documentAuths) {
		for (BidDocumentAuth vote : documentAuths) {

			QueryWrapper<BidDocumentAuth> voteQueryWrapper = new QueryWrapper<>();
			voteQueryWrapper.eq("proofID", vote.getProofID());
			voteQueryWrapper.eq("owner", vote.getOwner());
			voteQueryWrapper.last("limit 1");

			BidDocumentAuth trustanchorVote = baseMapper.selectOne(voteQueryWrapper);

			if (trustanchorVote == null) {
				baseMapper.insert(vote);
			} else {
				vote.setId(trustanchorVote.getId());
				baseMapper.updateById(vote);
			}
		}
		return 0;
	}

}
