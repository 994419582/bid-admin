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

import cn.teleinfo.bidadmin.soybean.bo.UserBO;
import cn.teleinfo.bidadmin.soybean.config.WxSendSubscribe;
import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.WxSubscribe;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.service.IWxSubscribeService;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.bind.annotation.*;
import org.springblade.core.tool.api.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/subscribe/")
@Api(value = "消息订阅接口", tags = "消息订阅接口")
public class WxSubscribeController extends BladeController {

	private IClocklnService clocklnService;

	private IGroupService groupService;

    private IWxSubscribeService wxSubscribeService;

    private IUserService userService;

    private WxSendSubscribe sendSubscribe;

	/**
	 * 微信小程序推送订阅消息
	 * create By KingYiFan on 2020/01/06
	 */
	@ApiOperation(value = "微信小程序推送单个用户订阅消息", notes = "微信小程序推送单个用户订阅消息")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "appId", value = "小程序appId", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "openId", value = "用户openId，单个用户", dataType = "String", paramType = "query")})
	@GetMapping(value = "/send")
	@ResponseBody
	public R sendDYTemplateMessage(@RequestParam(defaultValue = "") String openId) throws Exception {
		openId = openId.trim();
		if ("".equals(openId)) {
			return R.fail("请输入单个用户openId");
		}

		User user=userService.findByWechatId(openId);
		if (user==null){
			R.fail("该用户不存在");
		}
		if (user.getMessage()<=0){
			R.fail("该用户没有订阅");
		}
		WxSubscribe wx = wxSubscribeService.selectWxSubscribe(openId, null, new Date());
		if (wx != null) {
			return R.fail("用户已被提醒");
		}

		String appId = sendSubscribe.getPropertiesAppId();

		R r = sendSubscribe.send(appId, openId);
		if (r.isSuccess()) {
            WxSubscribe wxSubscribe = new WxSubscribe();
            wxSubscribe.setWechatId(openId);
            wxSubscribe.setSendDate(LocalDateTime.now());
            user.setMessage(user.getMessage()-1);
            if (userService.saveOrUpdate(user)) {
				return R.status(wxSubscribeService.save(wxSubscribe));
			}else {
            	return R.fail("发送订阅消息失败");
			}
		} else {
			return R.data(r.getMsg());
		}
	}

	/**
	 * 微信小程序推送订阅消息
	 * create By KingYiFan on 2020/01/06
	 */
	@ApiOperation(value = "微信小程序推送多个用户订阅消息", notes = "微信小程序推送多个用户订阅消息")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "appId", value = "小程序appId", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "openIds", value = "用户openId，多个用户,用英文逗号,隔开", dataType = "String", paramType = "query")})
	@GetMapping(value = "/send/batch")
	@ResponseBody
	public R sendDYTemplateMessageBatch(@RequestParam(defaultValue = "")String openIds) throws Exception {
		openIds = openIds.trim();
		if ("".equals(openIds)) {
			return R.fail("请输入多个用户openId,用英文逗号,隔开");
		}

		String[] openIdArray = openIds.split(",");
		if (openIdArray.length < 1) {
			return R.fail("请输入多个用户openId,用英文逗号,隔开");
		}

		String appId = sendSubscribe.getPropertiesAppId();

		StringBuilder errorOpenId = new StringBuilder();
		Date date = new Date();
		for (String openId : openIdArray) {
			User user=userService.findByWechatId(openId);
			if (user==null){
				continue;
			}
			if (user.getMessage()<=0){
				continue;
			}
			WxSubscribe wx = wxSubscribeService.selectWxSubscribe(openId, null, date);
			if (wx != null) {
				continue;
			}

			R result = sendSubscribe.send(appId, openId);
			if (result.isSuccess()) {
                WxSubscribe wxSubscribe = new WxSubscribe();
                wxSubscribe.setWechatId(openId);
                wxSubscribe.setSendDate(LocalDateTime.now());
                user.setMessage(user.getMessage()-1);
                userService.saveOrUpdate(user);
                wxSubscribeService.save(wxSubscribe);
			} else {
                errorOpenId.append(result.getMsg());
                errorOpenId.append(",");
            }
		}

		if (errorOpenId.length() == 0) {
			return R.success("推送成功");
		} else {
			errorOpenId.deleteCharAt(errorOpenId.length()-1);
			return R.data(errorOpenId.toString());
		}
	}


	/**
	 * 微信小程序推送订阅消息
	 * create By KingYiFan on 2020/01/06
	 */
	@ApiOperation(value = "微信小程序推送群组订阅消息", notes = "微信小程序推送群组订阅消息")
	@ApiImplicitParams({
//			@ApiImplicitParam(name = "appId", value = "小程序appId", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "openIds", value = "用户openId，多个用户,用英文逗号,隔开", dataType = "String", paramType = "query")})
			@ApiImplicitParam(name = "groupId", value = "群组ID，groupId", dataType = "int", paramType = "query")})
	@GetMapping(value = "/send/group")
	@ResponseBody
	public R group(@RequestParam(defaultValue = "")Integer groupId) throws Exception {

		WxSubscribe wx = wxSubscribeService.selectWxSubscribe(null, groupId, new Date());
		if (wx != null) {
			return R.fail("该群组已被提醒");
		}

		List<UserVO> subscribeUsers = new ArrayList<>();

		UserBO userBO = groupService.selectUserByParentId(groupId);
		List<UserVO> users = userBO.getUsers();
		if (users != null && !users.isEmpty()) {

			List<Integer> ids = new ArrayList<>();
			for (UserVO u : users) {
				ids.add(u.getId());
			}

			List<Clockln> clocklns = clocklnService.selectClocklnByGroup(ids, new Date());

			for (UserVO u : users) {
				boolean flag = true;
				for (Clockln c : clocklns) {
					if (u.getId() == c.getUserId() || u.getId().equals(c.getUserId())) {
						flag = false;
						break;
					}
				}
				if (flag) {
					subscribeUsers.add(u);
				}
			}
		}

		if (subscribeUsers.isEmpty()) {
			return R.fail("该群组中所有用户都已打卡");
		}

		String appId = sendSubscribe.getPropertiesAppId();

		StringBuilder errorOpenId = new StringBuilder();
		Date date = new Date();
		LocalDateTime now = LocalDateTime.now();
		for (UserVO u : subscribeUsers) {
//			WxSubscribe wxUser = wxSubscribeService.selectWxSubscribe(u.getWechatId(), null, date);
			String wechatId = u.getWechatId();
			User user=userService.findByWechatId(wechatId);
			if (user==null){
				continue;
			}
			if (user.getMessage()<=0){
				continue;
			}
			WxSubscribe wxUser = wxSubscribeService.selectWxSubscribe(wechatId, null, date);
			if (wxUser != null) {
				continue;
			}
			R result = sendSubscribe.send(appId, u.getWechatId());
			if (result.isSuccess()) {
				WxSubscribe wxSubscribe = new WxSubscribe();
				wxSubscribe.setWechatId(u.getWechatId());
				wxSubscribe.setSendDate(now);
				user.setMessage(user.getMessage()-1);
				userService.saveOrUpdate(user);
				wxSubscribeService.save(wxSubscribe);
			} else {
				errorOpenId.append(result.getMsg());
				errorOpenId.append(",");
			}
		}


        WxSubscribe wxSubscribeGroup = new WxSubscribe();
        wxSubscribeGroup.setGroupId(groupId);
        wxSubscribeGroup.setSendDate(now);
        wxSubscribeService.save(wxSubscribeGroup);

		if (errorOpenId.length() == 0) {
			return R.success("推送成功");
		} else {
			errorOpenId.deleteCharAt(errorOpenId.length()-1);
			return R.data(errorOpenId.toString());
		}
	}
}
