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

import cn.teleinfo.bidadmin.cms.entity.Comment;
import cn.teleinfo.bidadmin.cms.service.ICommentService;
import cn.teleinfo.bidadmin.cms.vo.CommentVO;
import cn.teleinfo.bidadmin.cms.wrapper.CommentWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Notice Feign
 *
 * @author Chill
 */
//@ApiIgnore()
@RestController
@AllArgsConstructor
@Api(value = "官网APP文章评论", tags = "官网APP文章评论")
public class CommentClient implements ICommentClient {

	 ICommentService commentService;

	/**
	 * 获取文章详情
	 *
	 * @param id    字典编号
	 * @return
	 */
	@GetMapping(COMMENT_PREFIX + "/detail")
	public  R<CommentVO> getDetail(@RequestParam("id") String id){
		Comment detail = commentService.getById(id);
		return R.data(CommentWrapper.build().entityVO(detail));
	}

	/**
	 * 获取文章评论列表
	 *
	 * @param comment 字典编号
	 * @return
	 */
	@GetMapping(COMMENT_PREFIX + "/list")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "contentId", value = "文章编号", paramType = "query", dataType = "string")
	})
	public R<IPage<CommentVO>> list(@ApiIgnore @RequestParam Map<String, Object> comment, Query query) {
		if (comment.get("contentId")==null){
			return R.fail("文章编号为空");
		}
		QueryWrapper<Comment> queryWrapper = Condition.getQueryWrapper(comment,Comment.class);
		IPage<Comment> pages = commentService.page(Condition.getPage(query), queryWrapper.lambda().orderByDesc(Comment::getCreatetime));
		return R.data(CommentWrapper.build().pageVO(pages));
	}
	@PostMapping(COMMENT_PREFIX + "/submit")
	public R submit(HttpServletRequest request, @Valid @RequestBody Comment comment){
		if (comment.getContentId()==null || comment.getCategoryId()==null || StringUtil.isEmpty(comment.getTitle()) || StringUtil.isEmpty(comment.getContent())){
			return R.fail("文章基础信息不能为空");
		}
		comment.setStatus(1);
		comment.setCreatetime(LocalDateTime.now());
		comment.setIp(request.getRemoteAddr());
		if (comment.getName()==null){
			comment.setName(request.getRemoteHost());
		}
		return R.status(commentService.save(comment));
	}

	@PostMapping(COMMENT_PREFIX+"/remove")
	public R remove(HttpServletRequest request, @Valid @RequestBody Comment comment){
		if (comment.getId()==null ){
			R.fail("评论编号不能为空");
		}
		if (StringUtil.isEmpty(comment.getBid())&& StringUtil.isEmpty(comment.getName())){
			comment.setName(request.getRemoteHost());
		}
		return R.status(commentService.remove(Condition.getQueryWrapper(comment)));
	}


}
