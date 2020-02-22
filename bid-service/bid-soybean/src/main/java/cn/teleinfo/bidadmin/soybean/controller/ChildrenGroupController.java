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
import cn.teleinfo.bidadmin.soybean.entity.ChildrenGroup;
import cn.teleinfo.bidadmin.soybean.vo.ChildrenGroupVO;
import cn.teleinfo.bidadmin.soybean.wrapper.ChildrenGroupWrapper;
import cn.teleinfo.bidadmin.soybean.service.IChildrenGroupService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/childrengroup")
@Api(value = "", tags = "接口")
public class ChildrenGroupController extends BladeController {

	private IChildrenGroupService childrenGroupService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入childrenGroup")
	public R<ChildrenGroupVO> detail(ChildrenGroup childrenGroup) {
		ChildrenGroup detail = childrenGroupService.getOne(Condition.getQueryWrapper(childrenGroup));
		return R.data(ChildrenGroupWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入childrenGroup")
	public R<IPage<ChildrenGroupVO>> list(ChildrenGroup childrenGroup, Query query) {
		IPage<ChildrenGroup> pages = childrenGroupService.page(Condition.getPage(query), Condition.getQueryWrapper(childrenGroup));
		return R.data(ChildrenGroupWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入childrenGroup")
	public R<IPage<ChildrenGroupVO>> page(ChildrenGroupVO childrenGroup, Query query) {
		IPage<ChildrenGroupVO> pages = childrenGroupService.selectChildrenGroupPage(Condition.getPage(query), childrenGroup);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入childrenGroup")
	public R save(@Valid @RequestBody ChildrenGroup childrenGroup) {
		return R.status(childrenGroupService.save(childrenGroup));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入childrenGroup")
	public R update(@Valid @RequestBody ChildrenGroup childrenGroup) {
		return R.status(childrenGroupService.updateById(childrenGroup));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入childrenGroup")
	public R submit(@Valid @RequestBody ChildrenGroup childrenGroup) {
		return R.status(childrenGroupService.saveOrUpdate(childrenGroup));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(childrenGroupService.removeByIds(Func.toIntList(ids)));
	}

	
}
