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

import cn.teleinfo.bidadmin.cms.entity.Partner;
import cn.teleinfo.bidadmin.cms.vo.PartnerVO;
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
import cn.teleinfo.bidadmin.cms.entity.Professional;
import cn.teleinfo.bidadmin.cms.vo.ProfessionalVO;
import cn.teleinfo.bidadmin.cms.wrapper.ProfessionalWrapper;
import cn.teleinfo.bidadmin.cms.service.IProfessionalService;
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
@RequestMapping("/professional")
@Api(value = "专家团队", tags = "专家团队")
public class ProfessionalController extends BladeController {

	private IProfessionalService professionalService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入professional")
	public R<ProfessionalVO> detail(Professional professional) {
		Professional detail = professionalService.getOne(Condition.getQueryWrapper(professional));
		return R.data(ProfessionalWrapper.build().entityVO(detail));
	}

	/**
	* 分页 官网合作伙伴
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入professional")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "岗位名称", paramType = "query", dataType = "string")
	})
	public R<IPage<ProfessionalVO>> list(@ApiIgnore @RequestParam Map<String, Object> comment, Query query) {
		QueryWrapper<Professional> queryWrapper = Condition.getQueryWrapper(comment,Professional.class);
		IPage<Professional> pages = professionalService.page(Condition.getPage(query), queryWrapper.lambda().orderByDesc(Professional::getUpdateTime));
		return R.data(ProfessionalWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 官网合作伙伴
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入professional")
	public R<IPage<ProfessionalVO>> page(ProfessionalVO professional, Query query) {
		IPage<ProfessionalVO> pages = professionalService.selectProfessionalPage(Condition.getPage(query), professional);
		return R.data(pages);
	}

	/**
	* 新增 官网合作伙伴
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入professional")
	public R save(@Valid @RequestBody Professional professional) {
		return R.status(professionalService.save(professional));
	}

	/**
	* 修改 官网合作伙伴
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入professional")
	public R update(@Valid @RequestBody Professional professional) {
		return R.status(professionalService.updateById(professional));
	}

	/**
	* 新增或修改 官网合作伙伴
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入professional")
	public R submit(@Valid @RequestBody Professional professional) {
		return R.status(professionalService.saveOrUpdate(professional));
	}

	
	/**
	* 删除 官网合作伙伴
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(professionalService.deleteLogic(Func.toIntList(ids)));
	}

	
}
