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

import cn.teleinfo.bidadmin.blockchain.entity.DposCandidate;
import cn.teleinfo.bidadmin.blockchain.vo.DposCandidateVO;
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
import cn.teleinfo.bidadmin.blockchain.entity.DposVoter;
import cn.teleinfo.bidadmin.blockchain.vo.DposVoterVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.DposVoterWrapper;
import cn.teleinfo.bidadmin.blockchain.service.IDposVoterService;
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
@RequestMapping("/dposvoter")
@Api(value = "", tags = "dpos投票人接口")
public class DposVoterController extends BladeController {

	private IDposVoterService dposVoterService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dposVoter")
	public R<DposVoterVO> detail(DposVoter dposVoter) {
		DposVoter detail = dposVoterService.getOne(Condition.getQueryWrapper(dposVoter));
		return R.data(DposVoterWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入dposVoter")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "owner", value = "bid", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "proxy", value = "代理人bid", paramType = "query", dataType = "string")
	})
	public R<IPage<DposVoterVO>> list(@ApiIgnore @RequestParam Map<String, Object> dposVoter, Query query) {
		QueryWrapper<DposVoter> queryWrapper = Condition.getQueryWrapper(dposVoter,DposVoter.class);
		IPage<DposVoter> pages = dposVoterService.page(Condition.getPage(query), queryWrapper);
		return R.data(DposVoterWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入dposVoter")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "owner", value = "候选人地址", paramType = "query", dataType = "Integer"),
	})
	public R<IPage<DposVoterVO> >page(@ApiIgnore @RequestParam String owner,Query query) {
		IPage<DposVoterVO> list = dposVoterService.selectDposVoterPage(Condition.getPage(query),null,owner);
		return R.data(list);
	}
}
