package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorUpdatePropose;
import org.elasticsearch.cluster.metadata.TemplateUpgradeService;
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
public class ITrustanchorUpdateProposeServiceTest {

    static {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

    @Autowired
    private ITrustanchorUpdateProposeService proposeService;

    @Test
    public void selectTrustanchorUpdateProposePage() {
    }

    @Test
    public void saveOrUpdateTrustanchorUpdatePropose() {
        List<TrustanchorUpdatePropose> manages = new ArrayList<>();

        TrustanchorUpdatePropose m1 = new TrustanchorUpdatePropose();
        m1.setProposeBid("did:bid:20788becd0a573d6354605de");
        manages.add(m1);

        TrustanchorUpdatePropose m2 = new TrustanchorUpdatePropose();
        m2.setProposeBid("did:bid:200fd76b9548f0effa824a23");
        manages.add(m2);

        proposeService.saveOrUpdateTrustanchorUpdatePropose(manages);
    }
}