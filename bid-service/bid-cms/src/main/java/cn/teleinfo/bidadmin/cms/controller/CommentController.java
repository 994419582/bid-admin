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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.teleinfo.bidadmin.cms.entity.Comment;
import cn.teleinfo.bidadmin.cms.vo.CommentVO;
import cn.teleinfo.bidadmin.cms.wrapper.CommentWrapper;
import cn.teleinfo.bidadmin.cms.service.ICommentService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-10-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/comment")
@Api(value = "评论接口", tags = "评论接口")
public class CommentController extends BladeController {

	private ICommentService commentService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入comment")
	public R<CommentVO> detail(Comment comment) {
		Comment detail = commentService.getOne(Condition.getQueryWrapper(comment));
		return R.data(CommentWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入comment")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "title", value = "文章标题", paramType = "query", dataType = "string")
	})
	public R<IPage<CommentVO>> list(@ApiIgnore @RequestParam Map<String, Object> comment, Query query) {
		QueryWrapper<Comment> queryWrapper = Condition.getQueryWrapper(comment,Comment.class);
		IPage<Comment> pages = commentService.page(Condition.getPage(query),queryWrapper );
		return R.data(CommentWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入comment")
	public R<IPage<CommentVO>> page(CommentVO comment, Query query) {
		IPage<CommentVO> pages = commentService.selectCommentPage(Condition.getPage(query), comment);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入comment")
	public R save(@Valid @RequestBody Comment comment) {
		return R.status(commentService.save(comment));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入comment")
	public R update(@Valid @RequestBody Comment comment) {
		return R.status(commentService.updateById(comment));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入comment")
	public R submit(@Valid @RequestBody Comment comment) {
		return R.status(commentService.saveOrUpdate(comment));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(commentService.removeByIds(Func.toIntList(ids)));
	}

	
}
