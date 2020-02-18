package cn.teleinfo.bidadmin.blockchain.bifj.contract;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.bifj.crypto.CipherException;
import org.bifj.crypto.Credentials;
import org.bifj.crypto.WalletUtils;
import org.bifj.protocol.gbif.Gbif;
import org.bifj.tx.RawTransactionManager;
import org.bifj.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Contract {

    private GenericObjectPool<Gbif> gbifPool;

    public Contract(BIFClientFactory bifClientFactory) {
        this.gbifPool = new GenericObjectPool<>(bifClientFactory);
    }

    public String deploy(String privateKey) {
//        Credentials credentials = null;
//        try {
//            credentials = WalletUtils.loadCredentials("node", "/Users/shanpengfei/work/bif/bifj-test/src/main/java/solidity/coinbase_keystore");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CipherException e) {
//            e.printStackTrace();
//        }

        Gbif gbif = null;
        try {
            gbif = gbifPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Credentials credentials = Credentials.create(privateKey);
        try {
            Solidity_E_sol_E contract = Solidity_E_sol_E.deploy(
                    gbif,
                    new RawTransactionManager(gbif, credentials, 777),
                    new StaticGasProvider(BigInteger.valueOf(200000), BigInteger.valueOf(200000))
            ).sendAsync().get();
            return contract.getContractAddress();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Solidity_E_sol_E load(String privateKey, String contractAddress) {
//        Credentials credentials = null;
//        try {
//            credentials = WalletUtils.loadCredentials("node", "/Users/shanpengfei/work/bif/bifj-test/src/main/java/solidity/coinbase_keystore");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CipherException e) {
//            e.printStackTrace();
//        }

        Gbif gbif = null;
        try {
            gbif = gbifPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Credentials credentials = Credentials.create(privateKey);
        Solidity_E_sol_E contract = Solidity_E_sol_E.load(
                contractAddress,
                gbif,
                new RawTransactionManager(gbif, credentials, 777),
                new StaticGasProvider(BigInteger.valueOf(200000), BigInteger.valueOf(200000))
            );
        return contract;
    }

}
