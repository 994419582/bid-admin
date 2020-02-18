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
import cn.teleinfo.bidadmin.cms.vo.CommentVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Component;
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
public class ICommentClientFallback implements ICommentClient {
	@Override
	public R<CommentVO> getDetail(String id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<IPage<CommentVO>> list(@ApiIgnore @RequestParam Map<String, Object> comment, Query query) {
		return R.fail("获取数据失败");
	}

	@Override
	public R submit(HttpServletRequest request,Comment comment){
		return R.fail("提交评论失败");
	};

	@Override
	public R remove(HttpServletRequest request,Comment comment){
		return R.fail("删除评论失败");
	};

}