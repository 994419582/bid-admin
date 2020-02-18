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

import cn.teleinfo.bidadmin.app.entity.UserPhoto;
import cn.teleinfo.bidadmin.app.service.IUserPhotoService;
import cn.teleinfo.bidadmin.app.vo.UserPhotoVO;
import cn.teleinfo.bidadmin.app.wrapper.UserPhotoWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制器
 *
 * @author Blade
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/app/userphoto")
@Api(value = "用户头像", tags = "用户头像接口")
public class FrontUserPhotoController extends BladeController {

	private IUserPhotoService userPhotoService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userPhoto")
	public R<UserPhotoVO> detail(UserPhoto userPhoto) {
		UserPhoto detail = userPhotoService.getOne(Condition.getQueryWrapper(userPhoto));
		return R.data(UserPhotoWrapper.build().entityVO(detail));
	}
}
