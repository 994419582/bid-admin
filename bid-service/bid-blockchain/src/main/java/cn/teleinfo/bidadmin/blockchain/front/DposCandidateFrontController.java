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
package cn.teleinfo.bidadmin.blockchain.front;

import cn.teleinfo.bidadmin.blockchain.entity.DposCandidate;
import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import cn.teleinfo.bidadmin.blockchain.service.IDposCandidateService;
import cn.teleinfo.bidadmin.blockchain.service.IDposVoterService;
import cn.teleinfo.bidadmin.blockchain.service.ITranscationsService;
import cn.teleinfo.bidadmin.blockchain.vo.DposCandidateVO;
import cn.teleinfo.bidadmin.blockchain.vo.DposVoterVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.DposCandidateWrapper;
import cn.teleinfo.bidadmin.common.tool.CommonUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageUtils;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
@RequestMapping("/front/dposcandidate")
@Api(value = "超级节点前端接口", tags = "超级节点前端接口")
public class DposCandidateFrontController extends BladeController {

	private IDposCandidateService dposCandidateService;
	private IDposVoterService voterService;
	private ITranscationsService transcationsService;

	BifProperties properties;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dposCandidate")
	public R<DposCandidateVO> detail(DposCandidate dposCandidate) {
		DposCandidate detail = dposCandidateService.getOne(Condition.getQueryWrapper(dposCandidate));
		if (detail==null){
			return R.fail("没有找到该记录");
		}
		return R.data(DposCandidateWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/supernode-list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "active")
	public R<IPage<DposCandidateVO> >supernodeList(Query query) {
		IPage<DposCandidateVO> list = dposCandidateService.selectDposCandidatePage(PageUtils.getPage(query),1);
		list.getRecords().forEach(x ->{
			if (x.getVoteCount()!=null && x.getVoteCount()>0 && x.getVote()!=null){
				double percent=x.getVote()/x.getVoteCount() *100;
				x.setVotePercent(CommonUtil.subDouble(percent,2));
			}else {
				x.setVotePercent(0);
			}
		});
		return R.data(list);
	}

	/**
	 * 分页
	 */
	@GetMapping("/candidatenode-list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "active")
	public R<IPage<DposCandidateVO> >candidatenodeList(Query query) {
		Page page= PageUtils.getPage(query);
		page.setOffset(properties.superNode);
		IPage<DposCandidateVO> list = dposCandidateService.selectDposCandidatePage(page,1);
		list.getRecords().forEach(x ->{
			if (x.getVoteCount()!=null && x.getVoteCount()>0 && x.getVote()!=null){
				double percent=x.getVote()/x.getVoteCount() *100;
				x.setVotePercent(CommonUtil.subDouble(percent,2));
			}else {
				x.setVotePercent(0);
			}
		});
		return R.data(list);
	}

	/**
	 * 分页
	 */
	@GetMapping("/vote")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "分页", notes = "vote")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "owner", value = "候选人地址", paramType = "query", dataType = "Integer"),
	})
	public R<IPage<DposVoterVO> >vote(@ApiIgnore @RequestParam String owner,Query query) {
		IPage<DposVoterVO> list = voterService.selectDposVoterPage(Condition.getPage(query),null,owner);
		return R.data(list);
	}

}
