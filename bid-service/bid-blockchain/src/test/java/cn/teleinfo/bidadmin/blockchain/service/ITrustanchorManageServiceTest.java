package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorManage;
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
public class ITrustanchorManageServiceTest {

    static {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

    @Autowired
    private ITrustanchorManageService manageService;

    @Test
    public void selectTrustanchorManagePage() {
        System.out.println("test ok");
    }

    @Test
    public void gsaveOrUpdateTrustanchorManage() {
        List<TrustanchorManage> manages = new ArrayList<>();

        TrustanchorManage m1 = new TrustanchorManage();
        m1.setOwner("did:bid:20788becd0a573d6354605de");
        manages.add(m1);

        TrustanchorManage m2 = new TrustanchorManage();
        m2.setOwner("did:bid:200fd76b9548f0effa824a23");
        manages.add(m2);

        manageService.saveOrUpdateTrustanchorManage(manages);
    }
}