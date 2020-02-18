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

import cn.teleinfo.bidadmin.cms.entity.Article;
import cn.teleinfo.bidadmin.cms.vo.ArticleVO;
import cn.teleinfo.bidadmin.cms.mapper.ArticleMapper;
import cn.teleinfo.bidadmin.cms.service.IArticleService;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.SecureUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-10-08
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements IArticleService {

	@Override
	public IPage<ArticleVO> selectArticlePage(IPage<ArticleVO> page, ArticleVO article) {
		return page.setRecords(baseMapper.selectArticlePage(page, article));
	}

    @Override
	public IPage<ArticleVO> selectArticlePageWithBId(IPage<ArticleVO> page, ArticleVO article,String bid){
		return page.setRecords(baseMapper.selectArticlePageWithBId(page,article,bid));
	}
	/**
	 * TableId 注解存在更新记录，否插入一条记录
	 *
	 * @param entity 实体对象
	 * @return boolean
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer saveOrUpdateArticle(Article entity) {
		BladeUser user = SecureUtil.getUser();
		entity.setUpdateUser(user.getUserId());
		LocalDateTime now = LocalDateTime.now();
		entity.setUpdateTime(now);
		Integer result=null;
		if (entity.getId()==null){
			if (user != null) {
				entity.setCreateUser(user.getUserId());
			}
			entity.setCreateTime(now);
			if (entity.getStatus() == null) {
				entity.setStatus(1);
			}
			entity.setIsDeleted(0);
			result= baseMapper.insert(entity);
		}else {
			result= baseMapper.updateById(entity);
		}
		return result;
	}
	public int updateWithTime(Article entity) {
		return baseMapper.updateById(entity);
	}

}
