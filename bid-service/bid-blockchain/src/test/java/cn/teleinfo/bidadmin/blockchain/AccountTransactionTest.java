package cn.teleinfo.bidadmin.blockchain;

import org.bifj.protocol.gbif.Gbif;
import org.bifj.protocol.http.HttpService;
import org.junit.Test;

import java.math.BigInteger;

import static org.bifj.protocol.core.DefaultBlockParameterName.LATEST;

public class AccountTransactionTest {

    @Test
    public void testCandidates() throws Exception {

        String url = "http://192.168.104.35:33333";

        String address = "did:bid:f8e2b83f7264f45f4a51f6d4";

        Gbif gbif = Gbif.build(new HttpService(url));


        BigInteger balance = gbif.coreGetBalance(address, LATEST).sendAsync().get().getBalance();
        System.out.println("原地址：" + balance.toString());
        BigInteger balance2 = gbif.coreGetBalance("did:bid:27d251f1d25edf9cd519fdaa", LATEST).sendAsync().get().getBalance();
        System.out.println("新地址：" + balance2.toString());

//        Transaction transaction = new Transaction(address, "did:bid:27d251f1d25edf9cd519fdaa", new BigInteger("3000000000000000000000000000"), "");
//        String transactionHash = gbif.coreSendTransaction(transaction).sendAsync().get().getTransactionHash();
//        System.out.println(transactionHash);
    }
}
