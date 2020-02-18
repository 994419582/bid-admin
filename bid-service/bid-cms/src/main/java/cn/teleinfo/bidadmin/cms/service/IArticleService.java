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

import cn.teleinfo.bidadmin.cms.entity.Article;
import cn.teleinfo.bidadmin.cms.vo.ArticleVO;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Objects;

/**
 *  服务类
 *
 * @author Blade
 * @since 2019-10-08
 */
public interface IArticleService extends BaseService<Article> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param article
	 * @return
	 */
	IPage<ArticleVO> selectArticlePage(IPage<ArticleVO> page, ArticleVO article);
	Integer saveOrUpdateArticle(Article entity) ;
	int updateWithTime(Article entity);

	IPage<ArticleVO> selectArticlePageWithBId(IPage<ArticleVO> page, ArticleVO article,String bid);
}
