package cn.teleinfo.bidadmin.blockchain;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-dev.yml")
@BladeBootTest(appName = "bid-blockchain",profile = "dev",enableLoader = true)
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
}
