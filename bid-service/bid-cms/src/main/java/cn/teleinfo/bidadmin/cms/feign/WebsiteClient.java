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

import cn.teleinfo.bidadmin.cms.entity.*;
import cn.teleinfo.bidadmin.cms.service.*;
import cn.teleinfo.bidadmin.cms.vo.*;
import cn.teleinfo.bidadmin.cms.wrapper.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
@Api(value = "官网其他接口", tags = "官网其他接口")
public class WebsiteClient implements IWebsiteClient {

	 IJoinusService joinusService;
	 IPartnerService partnerService;
	 IProfessionalService professionalService;



	@GetMapping(API_PREFIX + "/joinus/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "加入我们列表", notes = "传入分页信息")
	public R<IPage<JoinusVO>> joinus(Map<String, Object> joinus, Query query) {
		QueryWrapper<Joinus> queryWrapper = Condition.getQueryWrapper(joinus,Joinus.class);
		IPage<Joinus> pages = joinusService.page(Condition.getPage(query), queryWrapper.lambda().orderByDesc(Joinus::getUpdateTime));
		return R.data(JoinusWrapper.build().pageVO(pages));
	}

	@GetMapping(API_PREFIX + "/partner/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "合作伙伴列表", notes = "传入分页信息")
	public R<IPage<PartnerVO>> partner(Map<String, Object> partner, Query query) {
		QueryWrapper<Partner> queryWrapper = Condition.getQueryWrapper(partner,Partner.class);
		IPage<Partner> pages = partnerService.page(Condition.getPage(query), queryWrapper.lambda().orderByDesc(Partner::getUpdateTime));
		return R.data(PartnerWrapper.build().pageVO(pages));
	}

	@GetMapping(API_PREFIX + "/professional/list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "专家团队列表", notes = "传入分页信息")
	public R<IPage<ProfessionalVO>> professional(Map<String, Object> professional, Query query) {
		QueryWrapper<Professional> queryWrapper = Condition.getQueryWrapper(professional,Professional.class);
		IPage<Professional> pages = professionalService.page(Condition.getPage(query), queryWrapper.lambda().orderByDesc(Professional::getUpdateTime));
		return R.data(ProfessionalWrapper.build().pageVO(pages));
	}


}
