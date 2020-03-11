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

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.teleinfo.bidadmin.soybean.bo.UserBO;
import cn.teleinfo.bidadmin.soybean.config.WxMaConfiguration;
import cn.teleinfo.bidadmin.soybean.config.WxMaProperties;
import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springframework.web.bind.annotation.*;
import org.springblade.core.tool.api.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	private WxMaProperties properties;

	private IClocklnService clocklnService;

	private IGroupService groupService;
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

		String appId = getPropertiesAppId();

		R r = send(appId, openId);
		if (r.isSuccess()) {
			return r;
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

		String appId = getPropertiesAppId();

		StringBuilder errorOpenId = new StringBuilder();

		for (String openId : openIdArray) {
			R result = send(appId, openId);
			if (!result.isSuccess()) {
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

		String appId = getPropertiesAppId();

		StringBuilder errorOpenId = new StringBuilder();

		for (UserVO u : subscribeUsers) {
			R result = send(appId, u.getWechatId());
			if (!result.isSuccess()) {
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

	private R send(String appId, String openId) {


		WxMaSubscribeMessage subscribeMessage = new WxMaSubscribeMessage();

		//跳转小程序页面路径
		subscribeMessage.setPage("pages/index/index");
		//模板消息id
		subscribeMessage.setTemplateId("xX28cEcVGBT_VQYUpsZastZFrfbC3YGBWYcCC9_mKRE");
		//给谁推送 用户的openid （可以调用根据code换openid接口)
		subscribeMessage.setToUser(openId);
		//==========================================创建一个参数集合========================================================
		ArrayList<WxMaSubscribeMessage.Data> wxMaSubscribeData = new ArrayList<>();

//        订阅消息参数值内容限制说明
//              ---摘自微信小程序官方：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
//        参数类别 	参数说明 	参数值限制 	   说明
//        thing.DATA 	事物 	20个以内字符 	可汉字、数字、字母或符号组合
//        number.DATA 	数字 	32位以内数字 	只能数字，可带小数
//        letter.DATA 	字母 	32位以内字母 	只能字母
//        symbol.DATA 	符号 	5位以内符号 	只能符号
//        character_string.DATA 	字符串 	32位以内数字、字母或符号 	可数字、字母或符号组合
//        time.DATA 	时间 	24小时制时间格式（支持+年月日） 	例如：15:01，或：2019年10月1日 15:01
//        date.DATA 	日期 	年月日格式（支持+24小时制时间） 	例如：2019年10月1日，或：2019年10月1日 15:01
//        amount.DATA 	金额 	1个币种符号+10位以内纯数字，可带小数，结尾可带“元” 	可带小数
//        phone_number.DATA 	电话 	17位以内，数字、符号 	电话号码，例：+86-0766-66888866
//        car_number.DATA 	车牌 	8位以内，第一位与最后一位可为汉字，其余为字母或数字 	车牌号码：粤A8Z888挂
//        name.DATA 	姓名 	10个以内纯汉字或20个以内纯字母或符号 	中文名10个汉字内；纯英文名20个字母内；中文和字母混合按中文名算，10个字内
//        phrase.DATA 	汉字 	5个以内汉字 	5个以内纯汉字，例如：配送中

		//第一个内容： 打卡任务
		WxMaSubscribeMessage.Data wxMaSubscribeData1 = new WxMaSubscribeMessage.Data();
		wxMaSubscribeData1.setName("thing1");
		wxMaSubscribeData1.setValue("健康打卡提醒");
		//每个参数 存放到大集合中
		wxMaSubscribeData.add(wxMaSubscribeData1);

		// 第二个内容：时间
		LocalDate date = LocalDate.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateStr = date.format(fmt);

		WxMaSubscribeMessage.Data wxMaSubscribeData2 = new WxMaSubscribeMessage.Data();
		wxMaSubscribeData2.setName("character_string2");
		wxMaSubscribeData2.setValue(dateStr);
		wxMaSubscribeData.add(wxMaSubscribeData2);

		// 第三个内容：备注
		WxMaSubscribeMessage.Data wxMaSubscribeData3 = new WxMaSubscribeMessage.Data();
		wxMaSubscribeData3.setName("thing3");
		wxMaSubscribeData3.setValue("信鄂通提醒您，请您前往小程序进行健康打卡");
		wxMaSubscribeData.add(wxMaSubscribeData3);

		//把集合给大的data
		subscribeMessage.setData(wxMaSubscribeData);
		//=========================================封装参数集合完毕========================================================

		try {
			//获取微信小程序配置：
			final WxMaService wxService = WxMaConfiguration.getMaService(appId);
			//进行推送
			wxService.getMsgService().sendSubscribeMsg(subscribeMessage);
			return R.success("推送成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.fail(openId);
	}

	private String getPropertiesAppId() {
		List<WxMaProperties.Config> configs = this.properties.getConfigs();
		if (configs == null) {
			throw new RuntimeException("配置服务端的大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
		}
		String appId = configs.get(0).getAppid();
		System.out.println("sendDYTemplateMessage appId:===:" + appId);
		return appId;
	}
}
