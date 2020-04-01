package cn.teleinfo.bidadmin.soybean.task;

import cn.teleinfo.bidadmin.soybean.config.WxSendSubscribe;
import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.WxSubscribe;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.service.IWxSubscribeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ClockInSubscribeScheduleTask {

    @Autowired
    private IUserService userService;

    @Autowired
    private WxSendSubscribe sendSubscribe;

    @Autowired
    private IWxSubscribeService wxSubscribeService;

    @Autowired
    private IClocklnService clocklnService;

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
//    private void configureTasks() {
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//    }

    @Scheduled(cron = "0 0 10 * * ?")
    private void executeTask(){
        LambdaQueryWrapper<User> userQueryWrapper = Wrappers.<User>lambdaQuery().gt(User::getMessage,0) ;
        List<User> userIPage = userService.list(userQueryWrapper);
        String appId = sendSubscribe.getPropertiesAppId();

        StringBuilder errorOpenId = new StringBuilder();
        Date date = new Date();
        for (User user : userIPage) {
            WxSubscribe wx = wxSubscribeService.selectWxSubscribe(user.getWechatId(), null, date);
            if (wx != null) {
                continue;
            }
            Clockln clockln = clocklnService.selectClocklnByUserID(user.getId(), date);
            if (clockln != null) {
                continue;
            }

            R result = sendSubscribe.send(appId, user.getWechatId());
            if (result.isSuccess()) {
                WxSubscribe wxSubscribe = new WxSubscribe();
                wxSubscribe.setWechatId(user.getWechatId());
                wxSubscribe.setSendDate(LocalDateTime.now());
                user.setMessage(0);
                wxSubscribeService.save(wxSubscribe);
                userService.saveOrUpdate(user);
            } else {
                errorOpenId.append(result.getMsg());
                errorOpenId.append(",");
            }
        }
    }
}