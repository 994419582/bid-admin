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

import cn.teleinfo.bidadmin.blockchain.entity.BidDocument;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentVO;
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
import cn.teleinfo.bidadmin.blockchain.entity.DposCandidate;
import cn.teleinfo.bidadmin.blockchain.vo.DposCandidateVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.DposCandidateWrapper;
import cn.teleinfo.bidadmin.blockchain.service.IDposCandidateService;
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
@RequestMapping("/dposcandidate")
@Api(value = "", tags = "dpos候选人接口")
public class DposCandidateController extends BladeController {

	private IDposCandidateService dposCandidateService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dposCandidate")
	public R<DposCandidateVO> detail(DposCandidate dposCandidate) {
		DposCandidate detail = dposCandidateService.getOne(Condition.getQueryWrapper(dposCandidate));
		return R.data(DposCandidateWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入dposCandidate")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "owner", value = "bid", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "name", value = "昵称", paramType = "query", dataType = "string")
	})
	public R<IPage<DposCandidateVO>> list(@ApiIgnore @RequestParam Map<String, Object> dposCandidate, Query query) {
		QueryWrapper<DposCandidate> queryWrapper = Condition.getQueryWrapper(dposCandidate,DposCandidate.class);
		IPage<DposCandidate> pages = dposCandidateService.page(Condition.getPage(query),queryWrapper.lambda().orderByDesc(DposCandidate::getVoteCount));
		return R.data(DposCandidateWrapper.build().pageVO(pages));
	}

//	* 自定义分页
//	*/
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "分页", notes = "传入dposCandidate")
//	public R<IPage<DposCandidateVO>> page(DposCandidateVO dposCandidate, Query query) {
//		IPage<DposCandidateVO> pages = dposCandidateService.selectDposCandidatePage(dposCandidate.getActive());
//		return R.data(pages);
//	}/**


	
}
