package cn.teleinfo.bidadmin.blockchain;

import org.bifj.crypto.Credentials;
import org.bifj.crypto.RawTransaction;
import org.bifj.crypto.TransactionEncoder;
import org.bifj.protocol.core.methods.request.Transaction;
import org.bifj.protocol.core.methods.response.TransactionReceipt;
import org.bifj.protocol.gbif.Gbif;
import org.bifj.protocol.http.HttpService;
import org.bifj.utils.Numeric;
import org.junit.Test;

import java.math.BigInteger;

import static org.bifj.protocol.core.DefaultBlockParameterName.LATEST;

public class AccountManageTest {

    @Test
    public void testCandidates() throws Exception {

        String url = "http://192.168.104.35:33333";

        String address = "did:bid:f8e2b83f7264f45f4a51f6d4";

        Gbif gbif = Gbif.build(new HttpService(url));

//        String address1 = gbif.coreCoinbase().sendAsync().get().getAddress();
//
//        System.out.println(address1);

//        BigInteger balance = gbif.coreGetBalance(address, LATEST).sendAsync().get().getBalance();
//        BigInteger balance = gbif.coreGetBalance("did:bid:27d251f1d25edf9cd519fdaa", LATEST).sendAsync().get().getBalance();
//        System.out.println(balance.toString());

        // nonce
        BigInteger transactionCount = gbif.coreGetTransactionCount("did:bid:27d251f1d25edf9cd519fdaa", LATEST).sendAsync().get().getTransactionCount();
        System.out.println(transactionCount.toString());

        // gasPrice
        BigInteger gasPrice1 = gbif.coreGasPrice().sendAsync().get().getGasPrice();
        System.out.println("gasPrice : " + gasPrice1);

        // gasLimit
        Transaction transaction = new Transaction("did:bid:27d251f1d25edf9cd519fdaa", "did:bid:27d251f1d25edf9cd519fdab", new BigInteger("30"), "");
        BigInteger amountUsed = gbif.coreEstimateGas(transaction).sendAsync().get().getAmountUsed();


        // 钱包
        Credentials credentials = Credentials.create("30be1331f7c38fef2518e3ee2b800fb44227c1239219f0b09f66748db0cb07b0");

        BigInteger nonce = transactionCount;
        BigInteger gasPrice = gasPrice1;
        BigInteger gasLimit = amountUsed;
        String from = "did:bid:27d251f1d25edf9cd519fdaa";
        String to = "did:bid:27d251f1d25edf9cd519fdab";
        BigInteger value = new BigInteger("30");
        String data = "";

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, from, to, value, data);


        byte[] bytes = TransactionEncoder.signMessage(rawTransaction, 777, credentials);
        String rawData = Numeric.toHexString(bytes);
        String hash = gbif.coreSendRawTransaction(rawData).sendAsync().get().getTransactionHash();
        System.out.println(hash);

        org.bifj.protocol.core.methods.response.Transaction transaction1 = gbif.coreGetTransactionByHash(hash).sendAsync().get().getTransaction().get();
        System.out.println(transaction1);

        TransactionReceipt transactionReceipt = gbif.coreGetTransactionReceipt(hash).sendAsync().get().getTransactionReceipt().get();
        System.out.println(transactionReceipt);
    }
}
