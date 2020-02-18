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

import cn.teleinfo.bidadmin.blockchain.entity.BidDocumentAuth;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.teleinfo.bidadmin.blockchain.entity.BidDocument;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.BidDocumentWrapper;
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-10-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/biddocument")
@Api(value = "", tags = "接口")
public class BidDocumentController extends BladeController {

	private IBidDocumentService bidDocumentService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入bidDocument")
	public R<BidDocumentVO> detail(BidDocument bidDocument) {
		BidDocument detail = bidDocumentService.getOne(Condition.getQueryWrapper(bidDocument));
		return R.data(BidDocumentWrapper.build().entityVO(detail));

	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bid", value = "bid", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "name", value = "昵称", paramType = "query", dataType = "string")
	})
	public R<IPage<BidDocumentVO>> list(@ApiIgnore @RequestParam Map<String, Object> document, Query query) {
		QueryWrapper<BidDocument> queryWrapper = Condition.getQueryWrapper(document,BidDocument.class);
		IPage<BidDocument> pages = bidDocumentService.page(Condition.getPage(query), queryWrapper);
		return R.data(BidDocumentWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入bidDocument")
	public R<IPage<BidDocumentVO>> page(BidDocumentVO bidDocument, Query query) {
		IPage<BidDocumentVO> pages = bidDocumentService.selectBidDocumentPage(Condition.getPage(query), bidDocument);
		return R.data(pages);
	}


	
}
