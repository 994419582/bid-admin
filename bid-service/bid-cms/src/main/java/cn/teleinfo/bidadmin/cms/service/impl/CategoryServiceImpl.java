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

import cn.teleinfo.bidadmin.cms.entity.Category;
import cn.teleinfo.bidadmin.cms.vo.CategoryVO;
import cn.teleinfo.bidadmin.cms.mapper.CategoryMapper;
import cn.teleinfo.bidadmin.cms.service.ICategoryService;
import cn.teleinfo.bidadmin.cms.wrapper.CategoryWrapper;
import cn.teleinfo.bidadmin.common.tool.DateUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-10-08
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements ICategoryService {

	@Override
	public IPage<CategoryVO> selectCategoryPage(IPage<CategoryVO> page, CategoryVO category) {
		return page.setRecords(baseMapper.selectCategoryPage(page, category));
	}

	@Override
	public List<CategoryVO> tree(Integer moudle) {
		return ForestNodeMerger.merge(baseMapper.tree(moudle));
	}

	@Override
	public List<CategoryVO> routes( Wrapper<Category> queryWrapper) {
		List<Category> all = baseMapper.selectList(queryWrapper);
		CategoryWrapper wrapper=new CategoryWrapper();
		return wrapper.listNodeVO(all);
	}

	public  List<CategoryVO> stats(Map<String, Object> paramMap){
		Category category = new Category();

		Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
		if (beginDate == null){
			beginDate = DateUtils.setDays(new Date(), 1);
			paramMap.put("beginDate", DateUtils.formatDate(beginDate, "yyyy-MM-dd"));
		}
		category.setCreateTime(getDateTimeOfTimestamp(beginDate.getTime()));
		Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
		if (endDate == null){
			endDate = DateUtils.addDays(DateUtils.addMonths(beginDate, 1), -1);
			paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd"));
		}
		category.setUpdateTime(getDateTimeOfTimestamp(endDate.getTime()));
		String cate=(String)paramMap.get("categoryId");
		if (cate != null && !("".equals(cate))){
			Integer categoryId = Integer.parseInt(cate);
			category.setId(categoryId);
		}

		String office=(String)paramMap.get("officeId");
		if (office != null && !("".equals(office))){
			Integer officeId = Integer.parseInt(office);
			category.setOfficeId(officeId);
		}
		String site=(String)paramMap.get("siteId");
		if (site != null && !("".equals(site))){
			Integer siteId = Integer.parseInt(site);
			category.setSiteId(siteId);
		}

		List<CategoryVO> list = baseMapper.findStats(category);
		return list;
	}


	public  LocalDateTime getDateTimeOfTimestamp(long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
}
