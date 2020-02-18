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
package cn.teleinfo.bidadmin.cms.controller;

import cn.teleinfo.bidadmin.cms.entity.Joinus;
import cn.teleinfo.bidadmin.cms.vo.JoinusVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.teleinfo.bidadmin.cms.entity.Partner;
import cn.teleinfo.bidadmin.cms.vo.PartnerVO;
import cn.teleinfo.bidadmin.cms.wrapper.PartnerWrapper;
import cn.teleinfo.bidadmin.cms.service.IPartnerService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 官网合作伙伴 控制器
 *
 * @author Blade
 * @since 2019-10-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/partner")
@Api(value = "合作伙伴", tags = "合作伙伴接口")
public class PartnerController extends BladeController {

	private IPartnerService partnerService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入partner")
	public R<PartnerVO> detail(Partner partner) {
		Partner detail = partnerService.getOne(Condition.getQueryWrapper(partner));
		return R.data(PartnerWrapper.build().entityVO(detail));
	}

	/**
	* 分页 官网合作伙伴
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入partner")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "岗位名称", paramType = "query", dataType = "string")
	})
	public R<IPage<PartnerVO>> list(@ApiIgnore @RequestParam Map<String, Object> comment, Query query) {
		QueryWrapper<Partner> queryWrapper = Condition.getQueryWrapper(comment,Partner.class);
		IPage<Partner> pages = partnerService.page(Condition.getPage(query), queryWrapper.lambda().orderByDesc(Partner::getUpdateTime));
		return R.data(PartnerWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 官网合作伙伴
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入partner")
	public R<IPage<PartnerVO>> page(PartnerVO partner, Query query) {
		IPage<PartnerVO> pages = partnerService.selectPartnerPage(Condition.getPage(query), partner);
		return R.data(pages);
	}

	/**
	* 新增 官网合作伙伴
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入partner")
	public R save(@Valid @RequestBody Partner partner) {
		return R.status(partnerService.save(partner));
	}

	/**
	* 修改 官网合作伙伴
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入partner")
	public R update(@Valid @RequestBody Partner partner) {
		return R.status(partnerService.updateById(partner));
	}

	/**
	* 新增或修改 官网合作伙伴
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入partner")
	public R submit(@Valid @RequestBody Partner partner) {
		return R.status(partnerService.saveOrUpdate(partner));
	}

	
	/**
	* 删除 官网合作伙伴
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(partnerService.deleteLogic(Func.toIntList(ids)));
	}

	
}
