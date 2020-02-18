package cn.teleinfo.bidadmin.quartz.task;

import cn.teleinfo.bidadmin.blockchain.feign.IElectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新DPOS
 */
@Component("electionTask")
public class ElectionTask {
    @Autowired
    private IElectionClient client;
    public void queryAllCandidates() {
        client.executeTask();
        System.out.println("测试获取投票选举信息");
    }
}
