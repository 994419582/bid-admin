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

import cn.teleinfo.bidadmin.cms.entity.Site;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.teleinfo.bidadmin.cms.entity.Category;
import cn.teleinfo.bidadmin.cms.vo.CategoryVO;
import cn.teleinfo.bidadmin.cms.wrapper.CategoryWrapper;
import cn.teleinfo.bidadmin.cms.service.ICategoryService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-10-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/category")
@Api(value = "", tags = "栏目接口")
public class CategoryController extends BladeController {

	private ICategoryService categoryService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入category")
	public R<CategoryVO> detail(Category category) {
		Category detail = categoryService.getOne(Condition.getQueryWrapper(category));
		return R.data(CategoryWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入category")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "栏目名称", paramType = "query", dataType = "string"),
	})
	public R<List<CategoryVO>> list(@ApiIgnore @RequestParam Map<String, Object>  category) {
		QueryWrapper<Category> queryWrapper = Condition.getQueryWrapper(category,Category.class);
		@SuppressWarnings("unchecked")
		List<Category> list = categoryService.list(queryWrapper.lambda().orderByAsc(Category::getSort));
		return R.data(CategoryWrapper.build().listNodeVO(list));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入category")
	public R<IPage<CategoryVO>> page(CategoryVO category, Query query) {
		IPage<CategoryVO> pages = categoryService.selectCategoryPage(Condition.getPage(query), category);
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入category")
	public R save(@Valid @RequestBody Category category) {
		return R.status(categoryService.save(category));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入category")
	public R update(@Valid @RequestBody Category category) {
		return R.status(categoryService.updateById(category));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入category")
	public R submit(@Valid @RequestBody Category category) {
		return R.status(categoryService.saveOrUpdate(category));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(categoryService.deleteLogic(Func.toIntList(ids)));
	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<CategoryVO>> tree(String module) {
		List<CategoryVO> tree=null;
		if (!StringUtil.isEmpty(module)){
			 tree = categoryService.tree(Integer.parseInt(module));
		}else {
			tree = categoryService.tree(null);
		}
		return R.data(tree);
	}
	/**
	 * 前端菜单数据
	 */
	@GetMapping("/routes")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
	public R<List<CategoryVO>> routes( Category category, Query query) {
		List<CategoryVO> list = categoryService.routes(Condition.getQueryWrapper(category));
		return R.data(list);
	}

	/**
	 * 前端菜单数据
	 */
	@GetMapping("/stats")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
	public R<List<CategoryVO>> stats(@RequestParam Map<String, Object> paramMap, Query query) {
		List<CategoryVO> list = categoryService.stats(paramMap);
		return R.data(list);
	}
	
}
