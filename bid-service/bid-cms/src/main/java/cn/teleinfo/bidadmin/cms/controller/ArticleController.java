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
package cn.teleinfo.bidadmin.cms.controller;

import cn.hutool.core.util.EscapeUtil;
import cn.teleinfo.bidadmin.cms.entity.Article;
import cn.teleinfo.bidadmin.cms.entity.ArticleData;
import cn.teleinfo.bidadmin.cms.entity.Category;
import cn.teleinfo.bidadmin.cms.entity.Comment;
import cn.teleinfo.bidadmin.cms.service.IArticleDataService;
import cn.teleinfo.bidadmin.cms.service.IArticleService;
import cn.teleinfo.bidadmin.cms.service.ICategoryService;
import cn.teleinfo.bidadmin.cms.vo.ArticleVO;
import cn.teleinfo.bidadmin.cms.wrapper.ArticleWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-10-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/article")
@Api(value = "文章接口", tags = "文章接口")
public class ArticleController extends BladeController {

	private IArticleService articleService;
	private IArticleDataService articleDataService;
	private ICategoryService categoryService;
	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入article")
	public R<ArticleVO> detail(Article article) {
		Article detail = articleService.getOne(Condition.getQueryWrapper(article));
		detail.setHits(detail.getHits().add(BigDecimal.ONE));
		articleService.saveOrUpdate(detail);
		return R.data(ArticleWrapper.build().detailEntityVO(detail,""));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入article")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "title", value = "文章标题", paramType = "query", dataType = "string")
	})
	public R<IPage<ArticleVO>> list(@ApiIgnore @RequestParam Map<String, Object> article, Query query) {
		QueryWrapper<Article> queryWrapper = Condition.getQueryWrapper(article,Article.class);
		IPage<Article> pages = articleService.page(Condition.getPage(query), queryWrapper.lambda().orderByDesc(Article::getPosid).orderByDesc(Article::getWeight).orderByDesc(Article::getCreateTime));
		return R.data(ArticleWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入article")
	public R<IPage<ArticleVO>> page(ArticleVO article, Query query) {
		IPage<ArticleVO> pages = articleService.selectArticlePage(Condition.getPage(query), article);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入article")
	public R save(@Valid @RequestBody Article article) {
		return R.status(articleService.save(article));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入article")
	public R update(@Valid @RequestBody Article article) {
		return R.status(articleService.updateById(article));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入article")
	public R submit(@Valid @RequestBody ArticleVO articleVO) {
		Article article= ArticleWrapper.build().entity(articleVO);
		Category category=null;
		if (article.getCategoryId() !=null){
			category = categoryService.getById(article.getCategoryId());
		}
		// 如果栏目不需要审核，则将该内容设为发布状态
		if (article.getStatus() ==null && category !=null ){
			if (category.getIsAudit()==0){
				article.setStatus(0);
			}
		}
//		if (article.getTops())
		Integer result=articleService.saveOrUpdateArticle(article);
		if (result==null || result <= 0){
			return R.fail("文章保存失败");
		}
		ArticleData articleData= new ArticleData();
		articleData.setId(article.getId());
		if (articleVO.getContent()!=null){
			articleData.setContent(EscapeUtil.safeUnescape(articleVO.getContent()));
		}
		articleData.setAllowComment(articleVO.getAllowComment());

		// 如果栏目不需要审核，则将该内容设为发布状态
		if (articleVO.getAllowComment() ==null && category !=null ){
			articleData.setAllowComment(category.getAllowComment()+"");
		}
		articleData.setCopyfrom(articleVO.getCopyfrom());
		articleData.setRelation(articleVO.getRelation());

		return R.status(articleDataService.saveOrUpdate(articleData));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(articleService.deleteLogic(Func.toIntList(ids)));
	}

	
}
