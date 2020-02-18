package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class IDposPullElectionInfoServiceTest {

    static {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

    @Autowired
    private IDposPullInfoService electionInfoService;

    @Test
    public void pullInfo() {
        R r = electionInfoService.pullInfo();
        System.out.println(r);
    }

    @Test
    public void pullTrustAnchorInfo() {
        R r = electionInfoService.pullTrustAnchorInfo();
        System.out.println(r);
    }

    @Test
    public void pullTrustAnchorVoterInfo() {
        R r = electionInfoService.pullTrustAnchorVoterInfo();
        System.out.println(r);
    }
}