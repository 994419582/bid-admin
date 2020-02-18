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

import cn.teleinfo.bidadmin.blockchain.entity.BidDocumentPuk;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentPukVO;
import cn.teleinfo.bidadmin.blockchain.mapper.BidDocumentPukMapper;
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentPukService;
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
public class BidDocumentPukServiceImpl extends ServiceImpl<BidDocumentPukMapper, BidDocumentPuk> implements IBidDocumentPukService {

	@Override
	public IPage<BidDocumentPukVO> selectBidDocumentPukPage(IPage<BidDocumentPukVO> page, BidDocumentPukVO bidDocumentPuk) {
		return page.setRecords(baseMapper.selectBidDocumentPukPage(page, bidDocumentPuk));
	}

	@Override
	public int saveBatchDocumentPuks(List<BidDocumentPuk> documentPuks) {
		for (BidDocumentPuk vote : documentPuks) {

			QueryWrapper<BidDocumentPuk> voteQueryWrapper = new QueryWrapper<>();
			voteQueryWrapper.eq("owner", vote.getOwner());
			voteQueryWrapper.last("limit 1");

			BidDocumentPuk trustanchorVote = baseMapper.selectOne(voteQueryWrapper);

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
