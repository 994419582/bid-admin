package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.CertificateApply;
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
public class ICertificateApplyServiceTest {

    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Autowired
    private ICertificateApplyService service;

    @Test
    public void issuerCertificate() {
        R<CertificateApply> certificateApplyR = service.issuerCertificate("spf", "131123199510221234", "did:bid:19e22b7a0902bac941fe083a", "gongyao");
        System.out.println(certificateApplyR);
    }
}