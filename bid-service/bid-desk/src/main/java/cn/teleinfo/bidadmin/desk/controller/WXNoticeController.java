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
package cn.teleinfo.bidadmin.desk.controller;

import cn.teleinfo.bidadmin.common.cache.CacheNames;
import cn.teleinfo.bidadmin.desk.entity.Notice;
import cn.teleinfo.bidadmin.desk.service.INoticeService;
import cn.teleinfo.bidadmin.desk.vo.NoticeVO;
import cn.teleinfo.bidadmin.desk.wrapper.NoticeWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 * @since 2018-09-29
 */
@RestController
@RequestMapping("wx/notice")
@AllArgsConstructor
@Api(value = "前端用户通知接口", tags = "博客接口")
public class WXNoticeController extends BladeController implements CacheNames {

	private INoticeService noticeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public R<NoticeVO> detail(Notice notice) {
		Notice detail = noticeService.getOne(Condition.getQueryWrapper(notice));
		return R.data(NoticeWrapper.build().entityVO(detail));
	}


}
