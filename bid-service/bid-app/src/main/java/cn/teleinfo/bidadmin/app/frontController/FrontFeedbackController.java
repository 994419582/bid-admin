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
package cn.teleinfo.bidadmin.app.frontController;

import cn.teleinfo.bidadmin.app.entity.Feedback;
import cn.teleinfo.bidadmin.app.service.IFeedbackService;
import cn.teleinfo.bidadmin.app.vo.FeedbackVO;
import cn.teleinfo.bidadmin.app.wrapper.FeedbackWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/app/feedback")
@Api(value = "用户反馈", tags = "用户反馈APP接口")
public class FrontFeedbackController extends BladeController {

	private IFeedbackService feedbackService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入feedback")
	public R<FeedbackVO> detail(Feedback feedback) {
		Feedback detail = feedbackService.getOne(Condition.getQueryWrapper(feedback));
		return R.data(FeedbackWrapper.build().entityVO(detail));
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入feedback")
	public R save(@Valid @RequestBody Feedback feedback) {
		return R.status(feedbackService.save(feedback));
	}


	
}
