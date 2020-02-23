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
package cn.teleinfo.bidadmin.soybean.wxfront;

import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.ParentGroup;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import cn.teleinfo.bidadmin.soybean.wrapper.GroupWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/group")
@Api(value = "", tags = "群组接口")
public class WxGroupController extends BladeController {

	private IGroupService groupService;

	@GetMapping("/tree")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "下拉树形图", notes = "下拉树形图")
	public R<List<HashMap>> tree() {
		return R.data(groupService.tree());
	}

	/**
     * 群组添加用户
	 * @return
     */
	@PostMapping("/user")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "群组添加用户", notes = "传入groupId和userId")
	public R addUser(@Valid @RequestBody UserGroup userGroup) {
		return R.status(groupService.addUser(userGroup));
	}

	/**
	 * 群组删除用户
	 * @return
	 */
	@DeleteMapping("/user")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "群组删除用户", notes = "传入groupId和userId")
	public R delUser(@Valid @RequestBody UserGroup userGroup) {
		return R.status(groupService.delUser(userGroup));
	}
	
}
