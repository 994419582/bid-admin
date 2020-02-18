package cn.teleinfo.bidadmin.blockchain.bifj.bif;

import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import lombok.extern.slf4j.Slf4j;
import org.bifj.crypto.*;
import org.bifj.protocol.core.DefaultBlockParameter;
import org.bifj.protocol.core.methods.response.CoreBlock;
import org.bifj.protocol.core.methods.response.Transaction;
import org.bifj.protocol.core.methods.response.TransactionReceipt;
import org.bifj.protocol.gbif.Gbif;
import org.bifj.utils.Numeric;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.bifj.protocol.core.DefaultBlockParameterName.LATEST;

/**
 * 实现文件上传下载
 *
 * @author ZhenJin
 */
@Slf4j
public class BIFClientTemplate {

//    private GenericObjectPool<Gbif> gbifPool;
    private Gbif gbif;

    @Autowired
    BifProperties bifProperties;

    public BIFClientTemplate(BIFClientFactory bifClientFactory) {
        this.gbif = bifClientFactory.create();
//        new GenericObjectPool<>(bifClientFactory);
    }
    public  List<String> getAccounts() {
        try {
            return gbif.coreAccounts().sendAsync().get().getAccounts();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  BigInteger getBlockNumber() {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreBlockNumber().sendAsync().get().getBlockNumber();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger("0");
    }

    public  String getCoinbase() {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreCoinbase().sendAsync().get().getAddress();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public  BigInteger getGasPrice() {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreGasPrice().sendAsync().get().getGasPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger("0");
    }

    public  BigInteger getGasLimit(org.bifj.protocol.core.methods.request.Transaction transaction) {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreEstimateGas(transaction).sendAsync().get().getAmountUsed();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger("0");
    }

    public  boolean isGenerating() {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreGenerating().sendAsync().get().isGenerating();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public  BigInteger getBalance(String address) {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreGetBalance(address, LATEST).sendAsync().get().getBalance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger("0");
    }

    public  CoreBlock.Block getBlockByHash(String hash, boolean returnFullTransactionObjects) {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreGetBlockByHash(hash, returnFullTransactionObjects).sendAsync().get().getBlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  CoreBlock.Block getBlockByNumber(BigInteger blockNumber, boolean returnFullTransactionObjects) {
        DefaultBlockParameter defaultBlockParameter = DefaultBlockParameter.valueOf(blockNumber);
//        Gbif gbif=null;
        try {
//            gbif = gbifPool.borrowObject();
//            Gbif gbif1= Gbif.build(new HttpService("http://127.0.0.1:55555"));
            return gbif.coreGetBlockByNumber(defaultBlockParameter, returnFullTransactionObjects).sendAsync().get().getBlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  Transaction getTransactionByHash(String hash) {
        try {
            return gbif.coreGetTransactionByHash(hash).sendAsync().get().getTransaction().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  TransactionReceipt getTransactionReceipt(String hash) {
        try {
//            gbif = gbifPool.borrowObject();
//            Gbif gbif1= Gbif.build(new HttpService("http://127.0.0.1:55555"));
            return gbif.coreGetTransactionReceipt(hash).sendAsync().get().getTransactionReceipt().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  BigInteger getNonce(String address) {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreGetTransactionCount(address, LATEST).sendAsync().get().getTransactionCount();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger("0");
    }

    public  String sendTransaction(org.bifj.protocol.core.methods.request.Transaction transaction) {
        try {
//            gbif = gbifPool.borrowObject();
            return gbif.coreSendTransaction(transaction).sendAsync().get().getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public  String sendRawTransaction(long chainId, String privateKey, org.bifj.protocol.core.methods.request.Transaction transaction) {
        try {
//            gbif = gbifPool.borrowObject();
            Credentials credentials = Credentials.create(privateKey);

            BigInteger nonce = getNonce(credentials.getAddress());

            BigInteger gasPrice = getGasPrice();
            BigInteger gasLimit = getGasLimit(transaction);
            String from = transaction.getFrom();
            String to = transaction.getTo();
            BigInteger value = Numeric.decodeQuantity(transaction.getValue());
            String data = transaction.getData();

            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, from, to, value, data);



            byte[] bytes = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
            String rawData = Numeric.toHexString(bytes);
            return gbif.coreSendRawTransaction(rawData).sendAsync().get().getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public  String privateKey2PublicKey(String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        return Numeric.toHexStringWithPrefix(ecKeyPair.getPublicKey());
    }

    public  String privateKey2Bid(String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        return Keys.getAddress(ecKeyPair.getPublicKey());
    }

    public static String publicKey2Address(String publicKey) {return Keys.getAddress(publicKey);}


    }

