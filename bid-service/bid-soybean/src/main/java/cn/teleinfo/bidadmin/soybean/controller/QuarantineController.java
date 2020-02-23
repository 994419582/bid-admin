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
import cn.teleinfo.bidadmin.soybean.entity.Quarantine;
import cn.teleinfo.bidadmin.soybean.vo.QuarantineVO;
import cn.teleinfo.bidadmin.soybean.wrapper.QuarantineWrapper;
import cn.teleinfo.bidadmin.soybean.service.IQuarantineService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/quarantine")
@Api(value = "", tags = "用户隔离接口")
public class QuarantineController extends BladeController {

	private IQuarantineService quarantineService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入quarantine")
	public R<QuarantineVO> detail(Quarantine quarantine) {
		Quarantine detail = quarantineService.getOne(Condition.getQueryWrapper(quarantine));
		return R.data(QuarantineWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入quarantine")
	public R<List<Quarantine>> select(Quarantine quarantine, Query query) {
		IPage<Quarantine> pages = quarantineService.page(Condition.getPage(query), Condition.getQueryWrapper(quarantine));
		return R.data(pages.getRecords());
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入quarantine")
	public R<IPage<QuarantineVO>> list(Quarantine quarantine, Query query) {
		IPage<Quarantine> pages = quarantineService.page(Condition.getPage(query), Condition.getQueryWrapper(quarantine));
		return R.data(QuarantineWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入quarantine")
	public R<IPage<QuarantineVO>> page(QuarantineVO quarantine, Query query) {
		IPage<QuarantineVO> pages = quarantineService.selectQuarantinePage(Condition.getPage(query), quarantine);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入quarantine")
	public R save(@Valid @RequestBody Quarantine quarantine) {
		quarantine.setCreateTime(LocalDateTime.now());
		return R.status(quarantineService.save(quarantine));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入quarantine")
	public R update(@Valid @RequestBody Quarantine quarantine) {
		return R.status(quarantineService.updateById(quarantine));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入quarantine")
	public R submit(@Valid @RequestBody Quarantine quarantine) {
		quarantine.setCreateTime(LocalDateTime.now());
		return R.status(quarantineService.saveOrUpdate(quarantine));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(quarantineService.removeByIds(Func.toIntList(ids)));
	}

	
}
