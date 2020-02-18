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

import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorManage;
import cn.teleinfo.bidadmin.blockchain.service.ITrustanchorManageService;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorManageVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.TrustanchorManageWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-10-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/front/trustanchormanage")
@Api(value = "信任锚", tags = "信任锚前端接口")
public class TrustanchorManageFrontController extends BladeController {

	private ITrustanchorManageService trustanchorManageService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入trustanchorManage")
	public R<TrustanchorManageVO> detail(TrustanchorManage trustanchorManage) {
		TrustanchorManage detail = trustanchorManageService.getOne(Condition.getQueryWrapper(trustanchorManage));
		if (detail==null){
			return R.fail("没有找到该记录");
		}
		return R.data(TrustanchorManageWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入trustanchorManage")
	public R<IPage<TrustanchorManageVO>> list(TrustanchorManage trustanchorManage, Query query) {
		IPage<TrustanchorManage> pages = trustanchorManageService.page(Condition.getPage(query), Condition.getQueryWrapper(trustanchorManage));
		return R.data(TrustanchorManageWrapper.build().pageVO(pages));
	}

}
