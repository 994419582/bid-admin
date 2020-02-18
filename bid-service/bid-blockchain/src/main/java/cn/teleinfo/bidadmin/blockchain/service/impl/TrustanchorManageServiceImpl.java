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

import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorManage;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorManageVO;
import cn.teleinfo.bidadmin.blockchain.mapper.TrustanchorManageMapper;
import cn.teleinfo.bidadmin.blockchain.service.ITrustanchorManageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TrustanchorManageServiceImpl extends ServiceImpl<TrustanchorManageMapper, TrustanchorManage> implements ITrustanchorManageService {

	@Override
	public IPage<TrustanchorManageVO> selectTrustanchorManagePage(IPage<TrustanchorManageVO> page, TrustanchorManageVO trustanchorManage) {
		return page.setRecords(baseMapper.selectTrustanchorManagePage(page, trustanchorManage));
	}

	@Override
	public void saveOrUpdateTrustanchorManage(List<TrustanchorManage> manages) {

		for (TrustanchorManage manage : manages) {
			if ("0000000000000000000000000000000000000000".equals(manage.getOwner())) {
				continue;
			}
			QueryWrapper<TrustanchorManage> manageQueryWrapper = new QueryWrapper<>();
			manageQueryWrapper.eq("owner", manage.getOwner());
			manageQueryWrapper.last("limit 1");

			TrustanchorManage trustanchorManage = baseMapper.selectOne(manageQueryWrapper);

			if (trustanchorManage == null) {
				baseMapper.insert(manage);
			} else {
				manage.setId(trustanchorManage.getId());
				baseMapper.updateById(manage);
			}
		}
	}

}
