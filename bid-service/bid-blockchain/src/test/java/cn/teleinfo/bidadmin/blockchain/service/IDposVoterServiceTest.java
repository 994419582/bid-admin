package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.DposVoter;
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
public class IDposVoterServiceTest {

    @Autowired
    private IDposVoterService voterService;

    @Test
    public void selectDposVoterPage() {
    }

    @Test
    public void saveBatchVoters() {
        List<DposVoter> voters = new ArrayList<>();

        DposVoter v = new DposVoter();
        v.setIsProxy(1);
        v.setLastVoteCount(3);
        v.setOwner("did:bid123");
        v.setProxy("did:bid123456");
        v.setProxyVoteCount(1);
        v.setVoteTime(LocalDateTime.now());
        voters.add(v);

        voterService.saveBatchVoters(voters);
    }
}