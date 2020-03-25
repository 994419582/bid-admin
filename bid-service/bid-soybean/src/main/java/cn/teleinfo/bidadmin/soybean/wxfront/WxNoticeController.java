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
package cn.teleinfo.bidadmin.soybean.wxfront;

import cn.teleinfo.bidadmin.soybean.entity.Notice;
import cn.teleinfo.bidadmin.soybean.service.INoticeService;
import cn.teleinfo.bidadmin.soybean.vo.NoticeVO;
import cn.teleinfo.bidadmin.soybean.wrapper.NoticeWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 通知公告表 控制器
 *
 * @author Blade
 * @since 2020-03-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/notice")
@Api(value = "通知公告表", tags = "微信通知公告表接口")
public class WxNoticeController extends BladeController {

	private INoticeService noticeService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "根据ID获取通知详情", notes = "传入noticeId")
	public R<NoticeVO> detail(@RequestParam Integer noticeId) {
		Notice detail = noticeService.getById(noticeId);
		if (detail == null) {
			return R.data(null);
		}
		return R.data(NoticeWrapper.build().entityVO(detail));
	}

	/**
	* 读取通知
	*/
	@PostMapping("/read")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "读取通知", notes = "传入noticeId集合, id之间用英文逗号分隔")
	public R<Boolean> read(@ApiParam(value = "通知ID集合", required = true)  @RequestParam String noticeIds) {
		if (!Pattern.matches("\\d+(,\\d+)*", noticeIds)) {
			throw new ApiException("通知ID格式错误");
		}
		List<Integer> noticeIdList = Func.toIntList(noticeIds);
		if (CollectionUtils.isEmpty(noticeIdList)) {
			throw new ApiException("通知ID不能为空");
		}
		return R.status(noticeService.read(noticeIdList));
	}

	/**
	* 分页 通知公告表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页查询通知", notes = "传入notice和分页参数")
	public R<IPage<NoticeVO>> list(Notice notice, Query query) {
		IPage<Notice> pages = noticeService.page(Condition.getPage(query), Condition.getQueryWrapper(notice));
		return R.data(NoticeWrapper.build().pageVO(pages));
	}

}
