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
package cn.teleinfo.bidadmin.cms.feign;

import cn.teleinfo.bidadmin.cms.entity.Article;
import cn.teleinfo.bidadmin.cms.entity.Stepons;
import cn.teleinfo.bidadmin.cms.entity.Tops;
import cn.teleinfo.bidadmin.cms.vo.ArticleVO;
import cn.teleinfo.bidadmin.cms.vo.SteponsVO;
import cn.teleinfo.bidadmin.cms.vo.TopsVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Feign失败配置
 *
 * @author Chill
 */
@Component
public class IArticleClientFallback implements IArticleClient {
	@Override
	public R<ArticleVO> getDetail(String id,@RequestParam("bid") String bid) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<IPage<ArticleVO>> getList(@ApiIgnore @RequestParam Map<String, Object> article, Query query) {
		return R.fail("获取数据失败");
	}
   @Override
	public R<IPage<ArticleVO>> getAppList( Query query,@RequestParam("bid") String bid,@RequestParam("categoryId") String categoryId){
		return R.fail("获取数据失败");
	}

	@Override
	public R<IPage<ArticleVO>> getAppTopList(@ApiIgnore @RequestParam Map<String, Object> article, Query query){
		return R.fail("获取数据失败");
	}
	public R<Article> apparticle(@ApiIgnore @RequestParam Map<String, Object> article){
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> top(HttpServletRequest request, Tops tops) {
		return R.fail("点赞失败");
	}

	@Override
	public R<String> unTop(HttpServletRequest request, Tops tops) {
		return R.fail("取消点赞失败");
	}

	@Override
	public R<String> stepons(HttpServletRequest request, Stepons stepons) {
		return R.fail("踩失败");
	}

	@Override
	public R<String> unStepons(HttpServletRequest request, Stepons stepons) {
		return R.fail("取消踩失败");
	}

	@Override
	public R<SteponsVO> steponsDetail(HttpServletRequest request, Stepons stepons) {
		return R.fail("获取踩失败");
	}

	@Override
	public R<TopsVO> topDetail(HttpServletRequest request, Tops tops) {
		return R.fail("获取点赞失败");
	}

}