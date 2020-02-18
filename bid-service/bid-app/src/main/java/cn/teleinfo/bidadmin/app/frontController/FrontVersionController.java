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
package cn.teleinfo.bidadmin.app.frontController;

import cn.teleinfo.bidadmin.app.entity.Version;
import cn.teleinfo.bidadmin.app.service.IVersionService;
import cn.teleinfo.bidadmin.app.vo.VersionVO;
import cn.teleinfo.bidadmin.app.wrapper.VersionWrapper;
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
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/app/version")
@Api(value = "版本信息", tags = "版本信息接口")
public class FrontVersionController extends BladeController {

	private IVersionService versionService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入version")
	public R<VersionVO> detail(Version version) {
		Version detail = versionService.getOne(Condition.getQueryWrapper(version));
		return R.data(VersionWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入version")
	public R<List<Version>> list() {
		Version version=new Version();
		List<Version> pages = versionService.list(Condition.getQueryWrapper(version).lambda().eq(Version::getStatus,1).orderByDesc(Version::getUpdateTime));
		return R.data(pages);
	}

}
