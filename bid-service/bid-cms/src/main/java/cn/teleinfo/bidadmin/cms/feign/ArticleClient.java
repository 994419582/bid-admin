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
import cn.teleinfo.bidadmin.cms.mapper.TopsMapper;
import cn.teleinfo.bidadmin.cms.service.IArticleService;
import cn.teleinfo.bidadmin.cms.service.ISteponsService;
import cn.teleinfo.bidadmin.cms.service.ITopsService;
import cn.teleinfo.bidadmin.cms.vo.ArticleVO;
import cn.teleinfo.bidadmin.cms.vo.SteponsVO;
import cn.teleinfo.bidadmin.cms.vo.TopsVO;
import cn.teleinfo.bidadmin.cms.wrapper.ArticleWrapper;
import cn.teleinfo.bidadmin.cms.wrapper.SteponsWrapper;
import cn.teleinfo.bidadmin.cms.wrapper.TopsWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.statement.select.Top;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * Notice Feign
 *
 * @author Chill
 */
//@ApiIgnore()
@RestController
@AllArgsConstructor
@Api(value = "官网APP文章", tags = "官网APP文章")
public class ArticleClient implements IArticleClient {

	 IArticleService articleService;
	 ITopsService topsService;
	 ISteponsService steponsService;

	/**
	 * 获取文章详情
	 *
	 * @param id    字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入article id")
	public  R<ArticleVO> getDetail(@RequestParam("id") String id,@RequestParam("bid") String bid){
		Article detail = articleService.getById(id);
		if (detail ==null){
			return R.fail("没有查询到信息");
		}
		detail.setHits(detail.getHits().add(BigDecimal.ONE));
		articleService.updateWithTime(detail);
		return R.data(ArticleWrapper.build().detailEntityVO(detail,bid));
	}

	/**
	 * 获取文章列表
	 *
	 * @param article 字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入article")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "title", value = "文章标题", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "categoryId", value = "栏目名称", paramType = "query")
	})
	public R<IPage<ArticleVO>> getList(@ApiIgnore @RequestParam Map<String, Object> article, Query query) {
		QueryWrapper<Article> queryWrapper = Condition.getQueryWrapper(article,Article.class);
		IPage<Article> pages = articleService.page(Condition.getPage(query),queryWrapper.lambda().eq(Article::getStatus,1).orderByDesc(Article::getCreateTime));
		return R.data(ArticleWrapper.build().pageVO(pages));
	}

	/**
	 * 获取文章列表
	 *
	 * @param article 字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/applist")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入article")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "categoryId", value = "栏目名称", paramType = "query")
	})
	public R<IPage<ArticleVO>> getAppList( Query query,@RequestParam(value = "bid",required = false) String bid,@RequestParam("categoryId") String categoryId) {
		ArticleVO articleVO= new ArticleVO();
		if (!StringUtil.isEmpty(categoryId)){
			articleVO.setCategoryId(Integer.parseInt(categoryId));
		}
		IPage<ArticleVO> pages = articleService.selectArticlePageWithBId(Condition.getPage(query),articleVO,bid);
		return R.data(pages);
	}

	/**
	 * 获取文章列表
	 *
	 * @param article 字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/top-applist")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "分页", notes = "传入article")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "title", value = "文章标题", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "categoryId", value = "栏目名称", paramType = "query")
	})
	public R<IPage<ArticleVO>>  getAppTopList(@ApiIgnore @RequestParam Map<String, Object> article, Query query) {
		QueryWrapper<Article> queryWrapper = Condition.getQueryWrapper(article,Article.class);
		IPage<Article> pages = articleService.page(Condition.getPage(query),queryWrapper.lambda().eq(Article::getStatus,1).eq(Article::getPosid,1).orderByDesc(Article::getWeight).orderByDesc(Article::getCreateTime));
		return R.data(ArticleWrapper.build().pageVO(pages));
	}

	/**
	 * 获取文章列表
	 *
	 * @param article 字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/top-apparticle")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "分页", notes = "传入article")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "categoryId", value = "栏目名称", paramType = "query")
	})
	public R<Article> apparticle(@ApiIgnore @RequestParam Map<String, Object> article) {
		QueryWrapper<Article> queryWrapper = Condition.getQueryWrapper(article,Article.class);
		IPage<Article> pages = articleService.page(Condition.getPage(new Query().setSize(1).setCurrent(1)),queryWrapper.lambda().eq(Article::getStatus,1).eq(Article::getPosid,1).orderByDesc(Article::getWeight));
		if (pages.getRecords().size()>0){
			return R.data(pages.getRecords().get(0));
		}else {
			return R.data(null);
		}
	}


	@GetMapping(API_PREFIX + "/top")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "点赞", notes = "传入tops")
	public R<String> top(HttpServletRequest request, Tops tops){
		if (tops.getContentId()==null){
			return R.fail("请选择文章");
		}
		if (tops.getBid()==null && tops.getName()==null){
			tops.setName(request.getRemoteHost());
		}
		Tops temp=topsService.getOne(Condition.getQueryWrapper(tops));
		if (temp !=null){
			return R.fail("不能重复点赞");
		}
		Article detail = articleService.getById(tops.getContentId());
		if (detail==null) {
			return R.fail("点赞失败");
		}

		//取消点赞
		Stepons stepons= new Stepons();
		stepons.setName(tops.getName());
		stepons.setBid(tops.getBid());
		stepons.setIp(request.getRemoteAddr());
		stepons.setContentId(tops.getContentId());
		Stepons t=steponsService.getOne(Condition.getQueryWrapper(stepons));
		if (t!=null){
			steponsService.removeById(t.getId());
		}
		detail.setStepons(detail.getStepons()-1);

		detail.setTops(detail.getTops()+1);
		if (articleService.updateWithTime(detail)>0){
			tops.setIp(request.getRemoteAddr());
			if (tops.getName()==null){
				tops.setName(request.getRemoteHost());
			}
			tops.setCreateTime(LocalDateTime.now());
			return R.status(topsService.save(tops));
		}
		return R.fail("点赞失败");

	}
	@GetMapping(API_PREFIX + "/untop")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "取消点赞", notes = "传入tops")
	public R<String> unTop(HttpServletRequest request, Tops tops){
		if (tops.getBid()==null){
			tops.setName(request.getRemoteHost());
		}
		Tops temp=topsService.getOne(Condition.getQueryWrapper(tops));
		if (temp ==null){
			return R.fail("之前没有点赞");
		}
		Article detail = articleService.getById(tops.getContentId());
		if (detail==null) {
			return R.fail("取消点赞失败");
		}
		detail.setTops(detail.getTops()-1);
		if (articleService.updateWithTime(detail)>0){
			return R.status(topsService.remove( Condition.getQueryWrapper(tops)));
		}
		return R.fail("取消点赞失败");
	}

	@GetMapping(API_PREFIX + "/stepons")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "踩", notes = "传入stepons")
	public R<String> stepons(HttpServletRequest request, Stepons stepons){
		if (stepons.getContentId()==null){
			return R.fail("请选择文章");
		}
		if (stepons.getName()==null){
			stepons.setName(request.getRemoteHost());
		}
		Stepons temp=steponsService.getOne(Condition.getQueryWrapper(stepons));
		if (temp !=null){
			return R.fail("不能重复踩");
		}
		Article detail = articleService.getById(stepons.getContentId());
		if (detail==null) {
			return R.fail("踩失败");
		}
		//取消点赞
		Tops top= new Tops();
		top.setName(stepons.getName());
		top.setBid(stepons.getBid());
		top.setIp(request.getRemoteAddr());
		top.setContentId(stepons.getContentId());
		Tops t=topsService.getOne(Condition.getQueryWrapper(top));
		if (t!=null){
			topsService.removeById(t.getId());
		}
		detail.setTops(detail.getTops()-1);
		detail.setStepons(detail.getStepons()+1);
		if (articleService.updateWithTime(detail)>0){
			stepons.setIp(request.getRemoteAddr());
			stepons.setCreateTime(LocalDateTime.now());
			return R.status(steponsService.save(stepons));
		}
		return R.fail("踩失败");
	}

	@GetMapping(API_PREFIX + "/unstepons")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "取消踩", notes = "传入stepons")
	public R<String> unStepons(HttpServletRequest request,  Stepons stepons){
		if (stepons.getBid()==null){
			stepons.setName(request.getRemoteHost());
		}
		Stepons temp=steponsService.getOne(Condition.getQueryWrapper(stepons));
		if (temp ==null){
			return R.fail("之前没有踩");
		}
		Article detail = articleService.getById(stepons.getContentId());
		if (detail==null) {
			return R.fail("取消踩失败");
		}
		detail.setStepons(detail.getStepons()-1);
		if (articleService.updateWithTime(detail)>0){
			return R.status(steponsService.remove( Condition.getQueryWrapper(stepons)));
		}
		return R.fail("取消踩失败");
	}
	@GetMapping(API_PREFIX + "/steponsDetail")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "踩的详细信息", notes = "传入stepons")
	public R<SteponsVO> steponsDetail(HttpServletRequest request, Stepons stepons){
		if (stepons.getContentId()==null){
			return R.fail("获取失败");
		}
		if (stepons.getBid()==null && stepons.getName()==null){
			stepons.setName(request.getRemoteHost());
		}
		return R.data(SteponsWrapper.build().entityVO(steponsService.getOne(Condition.getQueryWrapper(stepons))));
	}

	@GetMapping(API_PREFIX + "/topDetail")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "点赞的详细信息", notes = "传入tops")
	public R<TopsVO> topDetail(HttpServletRequest request, Tops tops){
		if (tops.getContentId()==null){
			return R.fail("获取失败");
		}
		if (tops.getBid()==null && tops.getName()==null){
			tops.setName(request.getRemoteHost());
		}
		return R.data(TopsWrapper.build().entityVO(topsService.getOne(Condition.getQueryWrapper(tops))));
	}

}
