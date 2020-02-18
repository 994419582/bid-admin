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
import cn.teleinfo.bidadmin.cms.entity.Stepons;
import cn.teleinfo.bidadmin.cms.vo.SteponsVO;
import cn.teleinfo.bidadmin.cms.wrapper.SteponsWrapper;
import cn.teleinfo.bidadmin.cms.service.ISteponsService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-10-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/stepons")
@Api(value = "", tags = "踩接口")
public class SteponsController extends BladeController {

	private ISteponsService steponsService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入stepons")
	public R<SteponsVO> detail(Stepons stepons) {
		Stepons detail = steponsService.getOne(Condition.getQueryWrapper(stepons));
		return R.data(SteponsWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入stepons")
	public R<IPage<SteponsVO>> list(Stepons stepons, Query query) {
		IPage<Stepons> pages = steponsService.page(Condition.getPage(query), Condition.getQueryWrapper(stepons));
		return R.data(SteponsWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入stepons")
	public R<IPage<SteponsVO>> page(SteponsVO stepons, Query query) {
		IPage<SteponsVO> pages = steponsService.selectSteponsPage(Condition.getPage(query), stepons);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入stepons")
	public R save(@Valid @RequestBody Stepons stepons) {
		return R.status(steponsService.save(stepons));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入stepons")
	public R update(@Valid @RequestBody Stepons stepons) {
		return R.status(steponsService.updateById(stepons));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入stepons")
	public R submit(@Valid @RequestBody Stepons stepons) {
		return R.status(steponsService.saveOrUpdate(stepons));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(steponsService.removeByIds(Func.toIntList(ids)));
	}

	
}
