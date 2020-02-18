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
import cn.teleinfo.bidadmin.common.constant.AppConstant;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_CMS_NAME,
	fallback = IArticleClientFallback.class
)
public interface IArticleClient {

	String API_PREFIX = "/front/cms/article";

	/**
	 * 获取文章详情
	 *
	 * @param id    字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/detail")
	R<ArticleVO> getDetail(@RequestParam("id") String id,@RequestParam("bid") String bid);

	/**
	 * 获取文章列表
	 *
	 * @param article 字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/list")
	R<IPage<ArticleVO>> getList(@ApiIgnore @RequestParam Map<String, Object> article, Query query);

	@GetMapping(API_PREFIX + "/top-applist")
	R<IPage<ArticleVO>> getAppTopList(@ApiIgnore @RequestParam Map<String, Object> article, Query query);

	@GetMapping(API_PREFIX + "/applist")
	R<IPage<ArticleVO>> getAppList( Query query,@RequestParam("bid") String bid,@RequestParam("categoryId") String categoryId);

	@GetMapping(API_PREFIX + "/top-apparticle")
	R<Article> apparticle(@ApiIgnore @RequestParam Map<String, Object> article);

	@GetMapping(API_PREFIX + "/top")
	R<String> top(HttpServletRequest request, Tops tops);
	@GetMapping(API_PREFIX + "/untop")
	R<String> unTop(HttpServletRequest request, Tops tops);


	@GetMapping(API_PREFIX + "/stepons")
	R<String> stepons(HttpServletRequest request, Stepons stepons);
	@GetMapping(API_PREFIX + "/unstepons")
	R<String> unStepons(HttpServletRequest request, Stepons stepons);

	@GetMapping(API_PREFIX + "/steponsDetail")
	R<SteponsVO> steponsDetail(HttpServletRequest request, Stepons stepons);

	@GetMapping(API_PREFIX + "/topDetail")
	R<TopsVO> topDetail(HttpServletRequest request, Tops tops);

}
