package cn.teleinfo.bidadmin.blockchain.bifj.balance;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import org.bifj.protocol.core.methods.request.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class QueryBalanceTest {
    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    String address0 = "did:bid:f8e2b83f7264f45f4a51f6d4";
    String privateKey0 = "0x152ddfc0102bd902868bebf027636098c389e94499e1b0c98cb92db461b95efd";


    String address1 = "did:bid:46df0624bdddea7e218dfe22";
    String privateKey1 = "0x2251576634cf4e8732db756e8319d1fd5e9fce2f86b4eb4ae2a69bba7c2ee8f6";


    String address2 = "did:bid:6b0d3c60b1c0502249e31e60";
    String privateKey2 = "0xe5d40885bb25e6b38359321183ea19379aaa0c01ebf485be2a8184be423e2485";


    String address3 = "did:bid:ed2c7b090fc577c2f501fc0d";
    String privateKey3 = "0xe1b204a604ecf3874202d059e89388a8c2906d059fdbb49846ff01e2bc74ac4b";


    String address4 = "did:bid:fb03d18e135844f72e2724c3";
    String privateKey4 = "0x666d1aaf96c5f0e58245c57a13206794c42daf5085f08b0c8b7d376b9043a816";


    String address5 = "did:bid:d6c1d80d02169572c0611285";
    String privateKey5 = "0xb662a12a8e6af468b1148e367f90a321e8c830727a7c50c868fe460377f00823";

    @Autowired
    private BIFClientTemplate template;

    @Test
    public void testGetBalance() {
        BigInteger balance0 = template.getBalance(address0);
        BigInteger balance1 = template.getBalance(address1);
        BigInteger balance2 = template.getBalance(address2);
        BigInteger balance3 = template.getBalance(address3);
        BigInteger balance4 = template.getBalance(address4);
        BigInteger balance5 = template.getBalance(address5);
        System.out.println("balance0:" + balance0);
        System.out.println("balance1:" + balance1);
        System.out.println("balance2:" + balance2);
        System.out.println("balance3:" + balance3);
        System.out.println("balance4:" + balance4);
        System.out.println("balance5:" + balance5);
    }

    @Test
    public void testTransaction1() {
        String from = address0;
        BigInteger value = new BigInteger("18600000000000000000000");
        String to = address1;
        Transaction transaction = new Transaction(from, to, value, null);
        template.sendRawTransaction(777, privateKey0, transaction);
    }

    @Test
    public void testTransaction2() {
        String from = address0;
        BigInteger value = new BigInteger("18600000000000000000000");
        String to = address2;
        Transaction transaction = new Transaction(from, to, value, null);
        template.sendRawTransaction(777, privateKey0, transaction);
    }

    @Test
    public void testTransaction3() {
        String from = address0;
        BigInteger value = new BigInteger("18600000000000000000000");
        String to = address3;
        Transaction transaction = new Transaction(from, to, value, null);
        template.sendRawTransaction(777, privateKey0, transaction);
    }

    @Test
    public void testTransaction4() {
        String from = address0;
        BigInteger value = new BigInteger("18600000000000000000000");
        String to = address4;
        Transaction transaction = new Transaction(from, to, value, null);
        template.sendRawTransaction(777, privateKey0, transaction);
    }

    @Test
    public void testTransaction5() {
        String from = address0;
        BigInteger value = new BigInteger("18600000000000000000000");

        String to = address5;
        Transaction transaction = new Transaction(from, to, value, null);
        template.sendRawTransaction(777, privateKey0, transaction);
    }
}
