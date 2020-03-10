package cn.teleinfo.bidadmin.quartz.task;

import cn.teleinfo.bidadmin.soybean.feign.IWxSubscribeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新DPOS
 */
@Component("wxSubscribeTask")
public class WxSubscribeTask {
    @Autowired
    private IWxSubscribeClient client;

    public void send(String openId) {
        client.send(openId);
        System.out.println("send");
    }

    public void sendBatch(String openIds) {
        client.sendBatch(openIds);
        System.out.println("sendBatch");
    }

    public void sendGroup(String groupId) {
        client.sendGroup(groupId);
        System.out.println("sendGroup");
    }

}
