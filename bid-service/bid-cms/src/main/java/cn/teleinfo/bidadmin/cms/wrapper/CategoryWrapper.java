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
package cn.teleinfo.bidadmin.cms.wrapper;

import cn.teleinfo.bidadmin.cms.entity.Site;
import cn.teleinfo.bidadmin.cms.service.ICategoryService;
import cn.teleinfo.bidadmin.cms.service.ISiteService;
import cn.teleinfo.bidadmin.system.feign.IDictClient;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import cn.teleinfo.bidadmin.cms.entity.Category;
import cn.teleinfo.bidadmin.cms.vo.CategoryVO;
import org.springblade.core.tool.utils.SpringUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-10-08
 */
public class CategoryWrapper extends BaseEntityWrapper<Category, CategoryVO>  {

	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	private static ISiteService siteService;

	static {
		siteService = SpringUtil.getBean(ISiteService.class);
	}
	private static ICategoryService categoryService;

	static {
		categoryService = SpringUtil.getBean(ICategoryService.class);
	}
    public static CategoryWrapper build() {
        return new CategoryWrapper();
    }

	@Override
	public CategoryVO entityVO(Category category) {
		CategoryVO categoryVO = BeanUtil.copy(category, CategoryVO.class);
		R<String> dict = dictClient.getValue("cms_model", categoryVO.getModule());
		if (dict.isSuccess()) {
			String name = dict.getData();
			categoryVO.setModuleName(name);
		}
		if (categoryVO.getParentId()!=null){
			Category parent =categoryService.getById(categoryVO.getParentId());
			if (parent!=null) {
				categoryVO.setParentName(parent.getName());
			}
		}
		if (categoryVO.getSiteId()!=null){
			Site site =siteService.getById(categoryVO.getSiteId());
			if (site!=null) {
				categoryVO.setSiteName(site.getName());
			}
		}
		return categoryVO;
	}

	public List<CategoryVO> listNodeVO(List<Category> list) {
		List<CategoryVO> collect = list.stream().map(category -> entityVO(category)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
