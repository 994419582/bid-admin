package cn.teleinfo.bidadmin.soybean.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.json.JSONUtil;
import cn.teleinfo.bidadmin.soybean.config.WxMaConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序用户接口
 */
@Slf4j
@RestController
@RequestMapping("/wx/user")
public class WxMaUserController {

    /**
     * 登陆接口
     *
     * @param appid
     * @param code
     * @return
     */
    @GetMapping("/login")
    public String login(@RequestParam(name = "appid") String appid, @RequestParam(name = "code") String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info("login wechat success [openid = {}, session_key = {}]", session.getOpenid(), session.getSessionKey());
            //TODO 将openid暂存到某个地方
            return JSONUtil.toJsonStr(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return e.toString();
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
    @GetMapping("/info")
    public String info(@RequestParam(name = "appid") String appid,
                       @RequestParam(name = "sessionKey") String sessionKey,
                       @RequestParam(name = "signature") String signature,
                       @RequestParam(name = "rawData") String rawData,
                       @RequestParam(name = "encryptedData") String encryptedData,
                       @RequestParam(name = "iv") String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        // TODO 根据openid绑定并更新用户信息
        return JSONUtil.toJsonStr(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@RequestParam(name = "appid") String appid,
                        @RequestParam(name = "sessionKey")String sessionKey,
                        @RequestParam(name = "signature")String signature,
                        @RequestParam(name = "rawData")String rawData,
                        @RequestParam(name = "encryptedData") String encryptedData,
                        @RequestParam(name = "iv")String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JSONUtil.toJsonStr(phoneNoInfo);
    }

}
