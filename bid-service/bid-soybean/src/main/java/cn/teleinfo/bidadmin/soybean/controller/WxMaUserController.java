package cn.teleinfo.bidadmin.soybean.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.StrUtil;
import cn.teleinfo.bidadmin.soybean.config.WxMaConfiguration;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.wrapper.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序用户接口
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wx/user")
@Api(value = "微信小程序", tags = "微信小程序接口")
public class WxMaUserController extends BladeController {

    private IUserService userService;

    /**
     * 登陆接口
     *
     * @param appid
     * @param code
     * @return
     */
    @ApiOperation(value = "登录", notes = "接收wx.login请求")
    @GetMapping("/login")
    public R login(@RequestParam(name = "appid") String appid, @RequestParam(name = "code") String code) {
        if (StringUtils.isBlank(code)) {
            return R.fail("empty jscode");
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info("login wechat success [openid = {}, session_key = {}]", session.getOpenid(), session.getSessionKey());
            // 查询openid是否存在，否则
            if (StrUtil.isNotBlank(session.getOpenid())) {
                User user = userService.findByWechatId(session.getOpenid());
                if (user == null) {
                    user = new User();
                    user.setWechatId(session.getOpenid());
                    userService.save(user);
                }
            }
            return R.data(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.fail(e.toString());
        }
    }


    /**
     * 获取用户信息接口
     *
     * @param appid
     * @param sessionKey
     * @param signature
     * @param rawData
     * @param encryptedData
     * @param iv
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/info")
    public R info(@RequestParam(name = "appid") String appid,
                  @RequestParam(name = "sessionKey") String sessionKey,
                  @RequestParam(name = "signature") String signature,
                  @RequestParam(name = "rawData") String rawData,
                  @RequestParam(name = "encryptedData") String encryptedData,
                  @RequestParam(name = "iv") String iv) {

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return R.fail("user check failed");
        }
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        // 根据openid绑定并更新用户信息
        User user = userService.findByWechatId(userInfo.getOpenId());
        if (user == null) {
            user = new User();
        }
        user.setNickname(userInfo.getNickName());
        userService.saveOrUpdate(user);
        return R.data(UserWrapper.build().entityVO(user));
    }

    /**
     * 获取用户绑定手机号信息
     *
     * @param appid
     * @param sessionKey
     * @param signature
     * @param rawData
     * @param encryptedData
     * @param iv
     * @return
     */
    @ApiOperation(value = "绑定手机号", notes = "绑定手机号")
    @GetMapping("/phone")
    public R phone(
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "appid") String appid,
            @RequestParam(name = "sessionKey") String sessionKey,
            @RequestParam(name = "signature") String signature,
            @RequestParam(name = "rawData") String rawData,
            @RequestParam(name = "encryptedData") String encryptedData,
            @RequestParam(name = "iv") String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return R.fail("user check failed");
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        // 根据openid绑定并更新用户信息
        User user = userService.getById(id);
        if (user == null) {
            return R.fail("user not found");
        }
        user.setNickname(phoneNoInfo.getPhoneNumber());
        userService.saveOrUpdate(user);
        return R.data(phoneNoInfo);
    }

}
