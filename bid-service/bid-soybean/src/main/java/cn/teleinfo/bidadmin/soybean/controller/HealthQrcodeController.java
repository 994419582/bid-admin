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
import cn.teleinfo.bidadmin.common.tool.StringUtils;
import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.entity.HealthQrcode;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IHealthQrcodeService;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.utils.QrCodeUtil;
import cn.teleinfo.bidadmin.soybean.utils.RegexUtil;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.vo.HealthQrcodeDetailVO;
import cn.teleinfo.bidadmin.soybean.vo.HealthQrcodeVO;
import cn.teleinfo.bidadmin.soybean.wrapper.ClocklnWrapper;
import cn.teleinfo.bidadmin.soybean.wrapper.HealthQrcodeWrapper;
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
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/healthQrcode")
@Api(value = "", tags = "健康码接口")
public class HealthQrcodeController extends BladeController {

	private IHealthQrcodeService healthQrcodeService;
	private IClocklnService clocklnService;
	private IUserService userService;


	@GetMapping("/createHealthQrcode")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value="生成", notes = "healthQrcode")
	public R createHealthQrcode(@RequestParam(name = "userId") Integer userId){
		if (userId == null || "".equals(userId) || userId < 0) {
			return R.fail("用户不存在，请输入正确的用户~");
		}
		User user = userService.getById(userId);
		if (user == null) {
			return R.fail("用户不存在，请输入正确的用户~");
		}
		LocalDate today = LocalDate.now();
		QueryWrapper<HealthQrcode> healthQrcodeQueryWrapper = new QueryWrapper<>();
		healthQrcodeQueryWrapper.eq("user_id", userId);
		healthQrcodeQueryWrapper.eq("record_date", today);
		List<HealthQrcode> healthQrcodes = healthQrcodeService.list(healthQrcodeQueryWrapper);
		HealthQrcodeVO healthQrcodeVO = new HealthQrcodeVO();
		Color color = Color.BLACK;
		String colorString = "";
		if(healthQrcodes != null && healthQrcodes.size() > 0){
			colorString = healthQrcodes.get(0).getColor();
			if("RED".equals(colorString)){
				color = new Color(230, 56, 6);
			}else if("GREEN".equals(colorString)){
				color = new Color(36, 184, 124);
			}else if("YELLOW".equals(colorString)){
				color = new Color(246, 172, 50);
			}
			healthQrcodeVO.setUpdateTime(healthQrcodes.get(0).getUpdateTime());
		}else {
		    //查用户最新打卡
            Clockln clockln = clocklnService.selectTopByUserIdOrderByCreateTimeDesc(user.getId());
            if(clockln == null){
                return R.fail("请先健康打卡！");
            }
            if(!clockln.getComfirmed().equals(1)){
                //确诊
                color = new Color(230, 56, 6);
                colorString = "RED";
            }else {
				if(clockln.getLeaveCity().equals(1)){
					//14天内到达
					if(clockln.getWuhan().equals(1)){
						//接触过疑似、确诊
						color = new Color(230, 56, 6);
						colorString = "RED";
					}else{
						if(clockln.getHealthy().equals(1)){
							//健康
							color = new Color(246, 172, 50);
							colorString = "YELLOW";
						}else {
							//异常
							color = new Color(230, 56, 6);
							colorString = "RED";
						}
					}
				}else if(clockln.getLeaveCity().equals(2)) {
					if(clockln.getWuhan().equals(1)){
						//接触过疑似、确诊
						color = new Color(230, 56, 6);
						colorString = "RED";
					}else {
						if(clockln.getHealthy().equals(1)){
							//健康
							color = new Color(36, 184, 124);
							colorString = "GREEN";
						}else {
							//异常
							color = new Color(246, 172, 50);
							colorString = "YELLOW";
						}
					}
				}
            }
            HealthQrcode healthQrcode = new HealthQrcode();
            healthQrcode.setUserId(userId);
			healthQrcode.setColor(colorString);
			healthQrcode.setRecordDate(today);
			healthQrcode.setCreateTime(LocalDateTime.now());
			healthQrcode.setUpdateTime(LocalDateTime.now());
			healthQrcodeService.save(healthQrcode);
			healthQrcodeVO.setUpdateTime(LocalDateTime.now());
		}
		String base64 = "";
		try {
			base64 = QrCodeUtil.encode("功能正在建设中，敬请期待！", color);
		} catch (Exception e) {
			return R.fail("生成健康码失败，请联系管理员");
		}
		healthQrcodeVO.setColor(colorString);
		healthQrcodeVO.setBase64(base64);
		healthQrcodeVO.setTitle(user.getName() + "的健康码");
		if ("RED".equals(healthQrcodeVO.getColor())) {
			healthQrcodeVO.setDescription("连续14天正常为绿码可通行。");
		} else if ("YELLOW".equals(healthQrcodeVO.getColor())) {
			healthQrcodeVO.setDescription("请自觉持续观察和每天打卡，\n连续14天正常为绿码可通行。");
		} else {
			healthQrcodeVO.setDescription("凭此码可在本单位范围内通行，\n请主动出示、配合检查。");
		}
		return R.data(healthQrcodeVO);
	}


	@PostMapping("/getHealthQrcodeDetail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value="查看信息", notes = "healthQrcode")
	public R getHealthQrcodeDetail(@ApiParam(value = "用户主键", required = true) @RequestParam String id){
		HealthQrcodeDetailVO healthQrcodeDetailVO = new HealthQrcodeDetailVO();
		User user = userService.getById(id);
		if (user == null) {
			return R.fail("用户不存在，请输入正确的用户~");
		}
		LocalDate today = LocalDate.now();
		QueryWrapper<HealthQrcode> healthQrcodeQueryWrapper = new QueryWrapper<>();
		healthQrcodeQueryWrapper.eq("user_id", id);
		healthQrcodeQueryWrapper.eq("record_date", today);
		List<HealthQrcode> healthQrcodes = healthQrcodeService.list(healthQrcodeQueryWrapper);
		if(healthQrcodes != null && healthQrcodes.size() > 0){
			HealthQrcode healthQrcode = healthQrcodes.get(0);
			healthQrcodeDetailVO.setRealName(user.getName());
			healthQrcodeDetailVO.setPhone(user.getPhone().substring(0, 3) + "****" + user.getPhone().substring(7, user.getPhone().length()));
			healthQrcodeDetailVO.setIdCard(user.getIdNumber().replaceAll("(?<=\\w{1})\\w(?=\\w{1})", "*"));
			healthQrcodeDetailVO.setUnit(user.getCompanyName());
			healthQrcodeDetailVO.setUnitAddress(user.getCompanyDetailAddress());
			healthQrcodeDetailVO.setIsLeave(healthQrcode.getIsLeave());
			if(healthQrcode.getReturnDate() != null){
				healthQrcodeDetailVO.setReturnDate(healthQrcode.getReturnDate());
			}
			healthQrcodeDetailVO.setCurrentAddress(healthQrcode.getCurrentAddress());
			healthQrcodeDetailVO.setIsTouchCase(healthQrcode.getIsTouchCase());
			healthQrcodeDetailVO.setCurrentHealth(healthQrcode.getCurrentHealth());
		}
		return R.data(healthQrcodeDetailVO);
	}
}
