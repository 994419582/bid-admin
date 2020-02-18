package cn.teleinfo.bidadmin.blockchain.bifj.properties;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class BIFClientTest {

    @Autowired
    private BIFClientTemplate template;

    @Test
    public void testGetAccount() {
        List<String> accounts = template.getAccounts();
        for (String account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testPrivateKeyToPub(){
        System.out.println(template.privateKey2PublicKey("d37df84af4156fe9ab65a5642418cd7bd9e9371acb5ae1bd282d1d473bcb1f13"));
    }
}
