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

import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.entity.Quarantine;
import cn.teleinfo.bidadmin.soybean.entity.QuarantineTrip;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IQuarantineService;
import cn.teleinfo.bidadmin.soybean.service.IQuarantineTripService;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.vo.QuarantineVO;
import cn.teleinfo.bidadmin.soybean.wrapper.ClocklnWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.time.LocalDate;
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
@RequestMapping("/wx/interaction")
@Api(value = "用户打卡交互接口", tags = "用户打卡交互接口")
public class InteractionController extends BladeController {

	private IClocklnService clocklnService;
	private IQuarantineService quarantineService;
	private IQuarantineTripService quarantineTripService;

	/**￿
	* 查看当日用户是否已打卡接口
	*/
	@GetMapping("/clock/today")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看当日用户是否已打卡接口", notes = "传入用户ID")
	public R<Boolean> clock(Integer userId) {
		QueryWrapper<Clockln> clocklnQueryWrapper = new QueryWrapper<>();
		clocklnQueryWrapper.eq("user_id", userId);
		LocalDateTime now = LocalDateTime.now();
		clocklnQueryWrapper.between("create_time", LocalDateTime.of(now.toLocalDate(), LocalTime.MIN), LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
		Clockln detail = clocklnService.getOne(clocklnQueryWrapper);
		if (detail == null) {
			return R.data(false);
		} else {
			return R.data(true);
		}
	}

	/**￿
	* 查看当天打卡信息
	*/
	@GetMapping("/show/today")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看当天打卡信息", notes = "")
	public R<IPage<ClocklnVO>> clock(Query query) {
		QueryWrapper<Clockln> clocklnQueryWrapper = new QueryWrapper<>();
		LocalDateTime now = LocalDateTime.now();
		clocklnQueryWrapper.between("create_time", LocalDateTime.of(now.toLocalDate(), LocalTime.MIN), LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
		IPage<Clockln> pages = clocklnService.page(Condition.getPage(query), clocklnQueryWrapper);
		return R.data(ClocklnWrapper.build().pageVO(pages));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入用户ID")
	public R<IPage<ClocklnVO>> list(Integer userId, Query query) {
		Clockln clockln = new Clockln();
		clockln.setUserId(userId);
		IPage<Clockln> pages = clocklnService.page(Condition.getPage(query), Condition.getQueryWrapper(clockln));
		return R.data(ClocklnWrapper.build().pageVO(pages));
	}

	/**
	* 打卡
	*/
	@PostMapping("/clock")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "打卡", notes = "传入clockln")
	public R clock(Integer userId, String address, Integer healthy, Integer hospital, Integer wuhan, String gobacktime,
				   String remarks, Integer quarantine, String reason, Integer otherCity, LocalDate startTime,
				   Double temperature, Integer fever, Integer fatigue, Integer hoose, Integer dyspnea, Integer diarrhea,
				   Integer muscle, String other, String quarantionRemarks, List<QuarantineTrip> quarantineTrips
				   ) {
		Clockln c = new Clockln();
		c.setUserId(userId);
		c.setAddress(address);
		c.setHealthy(healthy);
		c.setHospital(hospital);
		c.setWuhan(wuhan);
		c.setGobacktime(gobacktime);
		c.setRemarks(remarks);
		c.setQuarantine(quarantine);
		c.setReason(reason);
		R.status(clocklnService.saveOrUpdate(c));

		Quarantine q = new Quarantine();
		q.setOtherCity(otherCity);
		q.setStartTime(startTime);
		q.setTemperature(temperature);
		q.setFever(fever);
		q.setFatigue(fatigue);
		q.setHoose(hoose);
		q.setDyspnea(dyspnea);
		q.setDiarrhea(diarrhea);
		q.setMuscle(muscle);
		q.setOther(other);
		q.setRemarks(quarantionRemarks);
		R.status(quarantineService.saveOrUpdate(q));

		return R.status(quarantineTripService.saveOrUpdateBatch(quarantineTrips));
	}
}
