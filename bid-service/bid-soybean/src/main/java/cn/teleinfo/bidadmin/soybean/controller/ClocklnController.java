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
package cn.teleinfo.bidadmin.soybean.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.utils.RegexUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.wrapper.ClocklnWrapper;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/clockln")
@Api(value = "", tags = "用户打卡接口")
public class ClocklnController extends BladeController {

	private IClocklnService clocklnService;
	private IUserService userService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入clockln")
	public R<ClocklnVO> detail(Clockln clockln) {
		Clockln detail = clocklnService.getOne(Condition.getQueryWrapper(clockln));
		return R.data(ClocklnWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入clockln")
	public R<IPage<ClocklnVO>> list(Clockln clockln, Query query) {
		IPage<Clockln> pages = clocklnService.page(Condition.getPage(query), Condition.getQueryWrapper(clockln));
		return R.data(ClocklnWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入clockln")
	public R<IPage<ClocklnVO>> page(ClocklnVO clockln, Query query) {
		IPage<ClocklnVO> pages = clocklnService.selectClocklnPage(Condition.getPage(query), clockln);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入clockln")
	public R save(@Valid @RequestBody Clockln clockln) {
		if (clockln.getUserId() == null || "".equals(clockln.getUserId()) || clockln.getUserId() < 0) {
			return R.fail("用户不存在，请输入正确的用户~");
		}
		User user = userService.getById(clockln.getUserId());
		if (user == null) {
			return R.fail("用户不存在，请输入正确的用户~");
		}

		if (!RegexUtil.dateFormat(clockln.getGobacktime())) {
			return R.fail("回京时间格式不正确，请写成2020-02-02");
		}

		if (!RegexUtil.dateFormat(clockln.getLeavetime())) {
			return R.fail("离京时间格式不正确，请写成2020-02-02");
		}

		LocalDateTime now = LocalDateTime.now();

		QueryWrapper<Clockln> clocklnQueryWrapper = new QueryWrapper<>();
		clocklnQueryWrapper.eq("user_id", clockln.getUserId());
		clocklnQueryWrapper.between("create_time", LocalDateTime.of(now.toLocalDate(), LocalTime.MIN), LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
		List<Clockln> clist = clocklnService.list(clocklnQueryWrapper);
		if (clist != null && !clist.isEmpty()) {
			return R.fail("今日已打卡~~正常打卡");
		}

		clockln.setCreateTime(now);


		QueryWrapper<Clockln> clocklnLastQueryWrapper = new QueryWrapper<>();
		clocklnLastQueryWrapper.eq("user_id", clockln.getUserId());
		clocklnLastQueryWrapper.between("create_time", DateUtil.offsetDay(DateUtil.beginOfDay(DateUtil.date()), -28), DateUtil.endOfDay(DateUtil.date()));
		List<Clockln> lastlist = clocklnService.list(clocklnLastQueryWrapper);
		boolean hubei = lastlist.stream().anyMatch(item -> StrUtil.contains(item.getAddress(), "湖北"));
		clockln.setHubei(hubei ? 1 : 0);

		if (clockln.getAddress().contains("北京")) {
			clockln.setBeijing(1);

			// 是否在隔离期
			QueryWrapper<Clockln> clockln14QueryWrapper = new QueryWrapper<>();
			clockln14QueryWrapper.eq("user_id", clockln.getUserId());
			clockln14QueryWrapper.between("create_time", LocalDateTime.of(now.plusDays(-14).toLocalDate(), LocalTime.MIN), LocalDateTime.of(now.toLocalDate(), LocalTime.MIN));
			int count = clocklnService.count(clockln14QueryWrapper);
			if(count < 14) {
				clockln.setQuarantine(1);
			} else {
				clockln.setQuarantine(0);
			}

		} else {
			clockln.setBeijing(0);
		}
		return R.status(clocklnService.saveIt(clockln));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入clockln")
	public R update(@Valid @RequestBody Clockln clockln) {
		if (clockln.getUserId() == null || "".equals(clockln.getUserId()) || clockln.getUserId() < 0) {
			return R.fail("用户不存在，请输入正确的用户~");
		}
		User user = userService.getById(clockln.getUserId());
		if (user == null) {
			return R.fail("用户不存在，请输入正确的用户~");
		}

		if (!RegexUtil.dateFormat(clockln.getGobacktime())) {
			return R.fail("回京时间格式不正确，请写成2020-02-02");
		}

		if (!RegexUtil.dateFormat(clockln.getLeavetime())) {
			return R.fail("离京时间格式不正确，请写成2020-02-02");
		}
		return R.status(clocklnService.updateById(clockln));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入clockln")
	public R submit(@Valid @RequestBody Clockln clockln) {
		if (clockln.getUserId() == null || "".equals(clockln.getUserId()) || clockln.getUserId() < 0) {
			return R.fail("用户不存在，请输入正确的用户~");
		}
		User user = userService.getById(clockln.getUserId());
		if (user == null) {
			return R.fail("用户不存在，请输入正确的用户~");
		}

		if (!RegexUtil.dateFormat(clockln.getGobacktime())) {
			return R.fail("回京时间格式不正确，请写成2020-02-02");
		}

		if (!RegexUtil.dateFormat(clockln.getLeavetime())) {
			return R.fail("离京时间格式不正确，请写成2020-02-02");
		}

		LocalDateTime now = LocalDateTime.now();

		QueryWrapper<Clockln> clocklnQueryWrapper = new QueryWrapper<>();
		clocklnQueryWrapper.eq("user_id", clockln.getUserId());
		clocklnQueryWrapper.between("create_time", LocalDateTime.of(now.toLocalDate(), LocalTime.MIN), LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
		List<Clockln> clist = clocklnService.list(clocklnQueryWrapper);
		if (clist != null && !clist.isEmpty()) {
			return R.fail("今日已打卡~~正常打卡");
		}

		clockln.setCreateTime(now);


		QueryWrapper<Clockln> clocklnLastQueryWrapper = new QueryWrapper<>();
		clocklnLastQueryWrapper.eq("user_id", clockln.getUserId());
		clocklnLastQueryWrapper.between("create_time", DateUtil.offsetDay(DateUtil.beginOfDay(DateUtil.date()), -28), DateUtil.endOfDay(DateUtil.date()));
		List<Clockln> lastlist = clocklnService.list(clocklnLastQueryWrapper);
		boolean hubei = lastlist.stream().anyMatch(item -> StrUtil.contains(item.getAddress(), "湖北"));
		clockln.setHubei(hubei ? 1 : 0);

		if (clockln.getAddress().contains("北京")) {
			clockln.setBeijing(1);

			// 是否在隔离期
			QueryWrapper<Clockln> clockln14QueryWrapper = new QueryWrapper<>();
			clockln14QueryWrapper.eq("user_id", clockln.getUserId());
			clockln14QueryWrapper.between("create_time", LocalDateTime.of(now.plusDays(-14).toLocalDate(), LocalTime.MIN), LocalDateTime.of(now.toLocalDate(), LocalTime.MIN));
			int count = clocklnService.count(clockln14QueryWrapper);
			if(count < 14) {
				clockln.setQuarantine(1);
			} else {
				clockln.setQuarantine(0);
			}

		} else {
			clockln.setBeijing(0);
		}
		return R.status(clocklnService.saveOrUpdate(clockln));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(clocklnService.removeByIds(Func.toIntList(ids)));
	}

	
}
