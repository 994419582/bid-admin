package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorStake;
import cn.teleinfo.bidadmin.blockchain.es.service.TranscationSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class ITrustanchorStakeServiceTest {

    static {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

    @Autowired
    private ITrustanchorStakeService stakeService;

    @Test
    public void selectTrustanchorStakePage() {
    }

    @Test
    public void saveOrUpdateTrustanchorStake() {
        List<TrustanchorStake> manages = new ArrayList<>();

        TrustanchorStake m1 = new TrustanchorStake();
        m1.setOwner("did:bid:20788becd0a573d6354605de");
        manages.add(m1);

        TrustanchorStake m2 = new TrustanchorStake();
        m2.setOwner("did:bid:200fd76b9548f0effa824a23");
        manages.add(m2);

        stakeService.saveOrUpdateTrustanchorStake(manages);
    }
}