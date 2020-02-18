package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.DposCandidate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class IDposCandidateServiceTest {

    @Autowired
    IDposCandidateService candidateService;

    @Test
    public void selectDposCandidatePage() {
    }

    @Test
    public void saveBatchCandidates() {
        List<DposCandidate> candidates = new ArrayList<>();

        DposCandidate c1 = new DposCandidate();
        c1.setActive(1);
        c1.setExtractedBounty("extracted bounty");
        c1.setIp("192.168.1.1");
        c1.setLastExtractTime(LocalDateTime.now());
        c1.setLocation("location");
        c1.setName("bifname");
        c1.setTotalBounty("300");
        c1.setUrl("enode://node1");
        c1.setVoteCount(501);
        c1.setWebsite("www.node1.com");
        c1.setOwner("did:bid:d6c1d80d02169572c0611285");
        candidates.add(c1);

        int r = candidateService.saveBatchCandidates(candidates);
        System.out.println(r);
    }
}