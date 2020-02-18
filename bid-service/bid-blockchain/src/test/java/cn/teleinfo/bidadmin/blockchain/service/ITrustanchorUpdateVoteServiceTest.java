package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorUpdateVote;
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
public class ITrustanchorUpdateVoteServiceTest {

    static {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

    @Autowired
    private ITrustanchorUpdateVoteService voteService;

    @Test
    public void selectTrustanchorUpdateVotePage() {
    }

    @Test
    public void saveOrUpdateTrustanchorUpdateVote() {
        List<TrustanchorUpdateVote> manages = new ArrayList<>();

        TrustanchorUpdateVote m1 = new TrustanchorUpdateVote();
        m1.setProposeId("did:bid:20788becd0a573d6354605de");
        m1.setVotebid("did:bid:20788becd0a573d6354605de");
        manages.add(m1);

        TrustanchorUpdateVote m2 = new TrustanchorUpdateVote();
        m2.setProposeId("did:bid:200fd76b9548f0effa824a23");
        m2.setVotebid("did:bid:200fd76b9548f0effa824a23");
        manages.add(m2);

        voteService.saveOrUpdateTrustanchorUpdateVote(manages);
    }
}