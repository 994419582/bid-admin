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

import cn.teleinfo.bidadmin.cms.entity.*;
import cn.teleinfo.bidadmin.cms.service.IArticleDataService;
import cn.teleinfo.bidadmin.cms.service.ICategoryService;
import cn.teleinfo.bidadmin.cms.service.ISteponsService;
import cn.teleinfo.bidadmin.cms.service.ITopsService;
import cn.teleinfo.bidadmin.cms.vo.ArticleVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringUtil;

import java.sql.Wrapper;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-10-08
 */
public class ArticleWrapper extends BaseEntityWrapper<Article, ArticleVO>  {

    public static ArticleWrapper build() {
        return new ArticleWrapper();
    }
	private static IArticleDataService articleDataService;

	static {
		articleDataService = SpringUtil.getBean(IArticleDataService.class);
	}

	private static ICategoryService categoryService;

	static {
		categoryService = SpringUtil.getBean(ICategoryService.class);
	}

	private static ITopsService topsService;

	static {
		topsService = SpringUtil.getBean(ITopsService.class);
	}

	private static ISteponsService steponsService;

	static {
		steponsService = SpringUtil.getBean(ISteponsService.class);
	}



	@Override
	public ArticleVO entityVO(Article article) {
		ArticleVO articleVO = BeanUtil.copy(article, ArticleVO.class);

		return articleVO;
	}

	public Article entity (ArticleVO articleVO){
    	Article article= BeanUtil.copy(articleVO,Article.class);
//		article.setCategoryId(articleVO.getCategoryId());
//		article.setColor();
//		article.setDescription();
//		article.set
		return article;

	}
	public ArticleVO detailEntityVO(Article article,String bid) {
		if (article==null){
			return new ArticleVO();
		}
		ArticleVO articleVO = BeanUtil.copy(article, ArticleVO.class);
		if (article.getCategoryId()!=null){
			Category parent =categoryService.getById(article.getCategoryId());
			if (parent!=null) {
				articleVO.setCategoryName(parent.getName());
			}
		}
		if (!StringUtil.isEmpty(bid)){
			Tops tops= new Tops();
			tops.setContentId(article.getId());
			tops.setBid(bid);
			tops=topsService.getOne(Condition.getQueryWrapper(tops));
			if (tops!=null && tops.getId() !=null){
				articleVO.setTopId(tops.getId());
			}else {
				Stepons stepons = new Stepons();
				stepons.setContentId(article.getId());
				stepons.setBid(bid);
				stepons = steponsService.getOne(Condition.getQueryWrapper(stepons));
				if (stepons != null) {
					articleVO.setSteponsId(stepons.getId());
				}
			}
		}

		ArticleData articleData =articleDataService.getById(article.getId());
		if (articleData!=null) {
			articleVO.setAllowComment(articleData.getAllowComment());
			articleVO.setContent(articleData.getContent());
			articleVO.setCopyfrom(articleData.getCopyfrom());
			articleVO.setRelation(articleData.getRelation());
		}

		return articleVO;
	}

}
