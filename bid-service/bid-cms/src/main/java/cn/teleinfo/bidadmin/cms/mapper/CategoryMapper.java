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
package cn.teleinfo.bidadmin.cms.mapper;

import cn.teleinfo.bidadmin.cms.entity.Category;
import cn.teleinfo.bidadmin.cms.vo.CategoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2019-10-08
 */
public interface CategoryMapper extends BaseMapper<Category> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param category
	 * @return
	 */
	List<CategoryVO> selectCategoryPage(IPage page, CategoryVO category);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<CategoryVO> tree(@Param("mod") Integer mod);


	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<CategoryVO> findStats(@Param("category") Category category);


}
