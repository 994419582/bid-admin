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
package cn.teleinfo.bidadmin.cms.service;

import cn.teleinfo.bidadmin.cms.entity.ArticleData;
import cn.teleinfo.bidadmin.cms.vo.ArticleDataVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Blade
 * @since 2019-10-08
 */
public interface IArticleDataService extends IService<ArticleData> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param articleData
	 * @return
	 */
	IPage<ArticleDataVO> selectArticleDataPage(IPage<ArticleDataVO> page, ArticleDataVO articleData);

}
