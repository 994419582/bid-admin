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

import cn.teleinfo.bidadmin.cms.entity.Site;
import cn.teleinfo.bidadmin.cms.vo.SiteVO;
import cn.teleinfo.bidadmin.cms.mapper.SiteMapper;
import cn.teleinfo.bidadmin.cms.service.ISiteService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-10-08
 */
@Service
public class SiteServiceImpl extends BaseServiceImpl<SiteMapper, Site> implements ISiteService {

	@Override
	public IPage<SiteVO> selectSitePage(IPage<SiteVO> page, SiteVO site) {
		return page.setRecords(baseMapper.selectSitePage(page, site));
	}

	@Override
	public List<Site> getList() {
		return baseMapper.selectList(null);
	}

}
