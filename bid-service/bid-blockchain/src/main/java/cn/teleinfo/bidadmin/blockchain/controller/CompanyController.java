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
package cn.teleinfo.bidadmin.blockchain.controller;

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
import cn.teleinfo.bidadmin.blockchain.entity.Company;
import cn.teleinfo.bidadmin.blockchain.vo.CompanyVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.CompanyWrapper;
import cn.teleinfo.bidadmin.blockchain.service.ICompanyService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-10-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/company")
@Api(value = "", tags = "接口")
public class CompanyController extends BladeController {

	private ICompanyService companyService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入company")
	public R<CompanyVO> detail(Company company) {
		Company detail = companyService.getOne(Condition.getQueryWrapper(company));
		return R.data(CompanyWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入company")
	public R<IPage<CompanyVO>> list(Company company, Query query) {
		IPage<Company> pages = companyService.page(Condition.getPage(query), Condition.getQueryWrapper(company));
		return R.data(CompanyWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入company")
	public R<IPage<CompanyVO>> page(CompanyVO company, Query query) {
		IPage<CompanyVO> pages = companyService.selectCompanyPage(Condition.getPage(query), company);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入company")
	public R save(@Valid @RequestBody Company company) {
		return R.status(companyService.save(company));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入company")
	public R update(@Valid @RequestBody Company company) {
		return R.status(companyService.updateById(company));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入company")
	public R submit(@Valid @RequestBody Company company) {
		return R.status(companyService.saveOrUpdate(company));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(companyService.removeByIds(Func.toIntList(ids)));
	}

	
}
