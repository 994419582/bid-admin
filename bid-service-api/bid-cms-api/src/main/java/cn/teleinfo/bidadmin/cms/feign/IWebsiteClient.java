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


import cn.teleinfo.bidadmin.cms.entity.Partner;
import cn.teleinfo.bidadmin.cms.entity.Stepons;
import cn.teleinfo.bidadmin.cms.entity.Tops;
import cn.teleinfo.bidadmin.cms.vo.*;
import cn.teleinfo.bidadmin.common.constant.AppConstant;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_CMS_NAME,
	fallback = IWebsiteClientFallback.class
)
@Api(value = "加入我们等接口", tags = "接口")
public interface IWebsiteClient {

	String API_PREFIX = "/front/website/";


	/**
	 * 获取加入我们列表
	 *
	 * @param objectMap 输入分页信息即可
	 * @return
	 */
	@GetMapping(API_PREFIX + "/joinus/list")
	@ApiOperation(value = "获取加入我们列表", notes = "传入分页信息")
	R<IPage<JoinusVO>> joinus(@ApiIgnore @RequestParam Map<String, Object> objectMap, Query query);

	/**
	 * 获取合作伙伴
	 *
	 * @param objectMap 输入分页信息即可
	 * @return
	 */
	@GetMapping(API_PREFIX + "/partner/list")
	@ApiOperation(value = "获取合作伙伴列表", notes = "传入分页信息")
	R<IPage<PartnerVO>> partner(@ApiIgnore @RequestParam Map<String, Object> objectMap, Query query);
	/**
	 * 获取专家团队
	 *
	 * @param objectMap 输入分页信息即可
	 * @return
	 */
	@GetMapping(API_PREFIX + "/professional/list")
	@ApiOperation(value = "获取专家列表", notes = "传入分页信息")
	R<IPage<ProfessionalVO>> professional(@ApiIgnore @RequestParam Map<String, Object> objectMap, Query query);

}
