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

import cn.teleinfo.bidadmin.app.entity.UserContacts;
import cn.teleinfo.bidadmin.app.service.IUserContactsService;
import cn.teleinfo.bidadmin.app.vo.UserContactsVO;
import cn.teleinfo.bidadmin.app.wrapper.UserContactsWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.factory.annotation.Required;
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
@RequestMapping("/app/usercontacts")
@Api(value = "用户联系人", tags = "用户联系人接口")
public class FrontUserContactsController extends BladeController {

	private IUserContactsService userContactsService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userContacts")
	public R<UserContactsVO> detail(UserContacts userContacts) {
		UserContacts detail = userContactsService.getOne(Condition.getQueryWrapper(userContacts));
		return R.data(UserContactsWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userContacts")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bid", value = "用户BID", paramType = "query", dataType = "string"),
	})
	public R<List<UserContacts>> list(@Ignore  @RequestParam String bid ) {
		UserContacts userContacts= new UserContacts();
		if (StringUtil.isEmpty(bid)){
			return R.fail("用户BID不能为空");
		}
		userContacts.setBid(bid);
		List<UserContacts> pages = userContactsService.list(Condition.getQueryWrapper(userContacts).lambda().orderByAsc(UserContacts::getContactsRemark).orderByAsc(UserContacts::getContactsName));
		return R.data(pages);
	}

	/**
	* 新增 
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userContacts")
	public R save(@Valid @RequestBody UserContacts userContacts) {
		return R.status(userContactsService.save(userContacts));
	}

	/**
	* 修改 
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userContacts")
	public R update(@Valid @RequestBody UserContacts userContacts) {
		return R.status(userContactsService.updateById(userContacts));
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userContacts")
	public R submit(@Valid @RequestBody UserContacts userContacts) {
		return R.status(userContactsService.saveOrUpdate(userContacts));
	}

	
	/**
	* 删除 
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userContactsService.removeByIds(Func.toIntList(ids)));
	}

	
}
