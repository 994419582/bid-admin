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

import cn.teleinfo.bidadmin.blockchain.entity.Transactions;
import cn.teleinfo.bidadmin.blockchain.vo.TranscationsVO;
import cn.teleinfo.bidadmin.blockchain.mapper.TranscationsMapper;
import cn.teleinfo.bidadmin.blockchain.service.ITranscationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 交易信息表 服务实现类
 *
 * @author Blade
 * @since 2019-10-22
 */
@Service
public class TranscationsServiceImpl extends ServiceImpl<TranscationsMapper, Transactions> implements ITranscationsService {

	@Override
	public IPage<TranscationsVO> selectTranscationsPage(IPage<TranscationsVO> page, TranscationsVO transcations) {
		return page.setRecords(baseMapper.selectTranscationsPage(page, transcations));
	}

}
