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
import cn.teleinfo.bidadmin.blockchain.entity.Block;
import cn.teleinfo.bidadmin.blockchain.vo.BlockVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.BlockWrapper;
import cn.teleinfo.bidadmin.blockchain.service.IBlockService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 * 区块信息表 控制器
 *
 * @author Blade
 * @since 2019-10-22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/block")
@Api(value = "区块信息表", tags = "区块信息表接口")
public class BlockController extends BladeController {

	private IBlockService blockService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入block")
	public R<BlockVO> detail(Block block) {
		Block detail = blockService.getOne(Condition.getQueryWrapper(block));
		return R.data(BlockWrapper.build().entityVO(detail));
	}

	/**
	* 分页 区块信息表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入block")
	public R<IPage<BlockVO>> list(Block block, Query query) {
		IPage<Block> pages = blockService.page(Condition.getPage(query), Condition.getQueryWrapper(block));
		return R.data(BlockWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 区块信息表
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入block")
	public R<IPage<BlockVO>> page(BlockVO block, Query query) {
		IPage<BlockVO> pages = blockService.selectBlockPage(Condition.getPage(query), block);
		return R.data(pages);
	}

	/**
	* 新增 区块信息表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入block")
	public R save(@Valid @RequestBody Block block) {
		return R.status(blockService.save(block));
	}

	/**
	* 修改 区块信息表
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入block")
	public R update(@Valid @RequestBody Block block) {
		return R.status(blockService.updateById(block));
	}

	/**
	* 新增或修改 区块信息表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入block")
	public R submit(@Valid @RequestBody Block block) {
		return R.status(blockService.saveOrUpdate(block));
	}

	
	/**
	* 删除 区块信息表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(blockService.removeByIds(Func.toIntList(ids)));
	}

	
}
