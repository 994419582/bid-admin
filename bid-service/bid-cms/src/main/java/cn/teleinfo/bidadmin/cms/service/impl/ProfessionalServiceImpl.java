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
package cn.teleinfo.bidadmin.cms.service.impl;

import cn.teleinfo.bidadmin.cms.entity.Professional;
import cn.teleinfo.bidadmin.cms.vo.ProfessionalVO;
import cn.teleinfo.bidadmin.cms.mapper.ProfessionalMapper;
import cn.teleinfo.bidadmin.cms.service.IProfessionalService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 官网合作伙伴 服务实现类
 *
 * @author Blade
 * @since 2019-10-15
 */
@Service
public class ProfessionalServiceImpl extends BaseServiceImpl<ProfessionalMapper, Professional> implements IProfessionalService {

	@Override
	public IPage<ProfessionalVO> selectProfessionalPage(IPage<ProfessionalVO> page, ProfessionalVO professional) {
		return page.setRecords(baseMapper.selectProfessionalPage(page, professional));
	}

}
