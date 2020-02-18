package cn.teleinfo.bidadmin.quartz.task;

import cn.teleinfo.bidadmin.blockchain.feign.IBlockChainClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新DPOS
 */
@Component("blockChainTask")
public class BlockChainTask {
    @Autowired
    private IBlockChainClient client;
    public void loadBlockMsg() {
        client.executeTask();
        System.out.println("测试获取区块信息");
    }
}
