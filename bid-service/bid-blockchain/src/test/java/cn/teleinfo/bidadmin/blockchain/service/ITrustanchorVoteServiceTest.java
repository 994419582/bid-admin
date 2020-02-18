package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorVote;
import org.elasticsearch.index.termvectors.TermVectorsService;
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
public class ITrustanchorVoteServiceTest {

    static {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

    @Autowired
    private ITrustanchorVoteService voteService;

    @Test
    public void selectTrustanchorVotePage() {
    }

    @Test
    public void saveOrUpdateTrustanchorVote() {
        List<TrustanchorVote> manages = new ArrayList<>();

        TrustanchorVote m1 = new TrustanchorVote();
        m1.setVoter("did:bid:20788becd0a573d6354605de");
        manages.add(m1);

        TrustanchorVote m2 = new TrustanchorVote();
        m2.setVoter("did:bid:200fd76b9548f0effa824a23");
        manages.add(m2);

        voteService.saveOrUpdateTrustanchorVote(manages);
    }
}