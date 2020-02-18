package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import cn.teleinfo.bidadmin.blockchain.entity.BidDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class IBidDocumentServiceTest {

    @Autowired
    IBidDocumentService bidDocumentService;

    @Test
    public void selectBidDocumentPage() {
    }

    @Test
    @Transactional
    @Rollback
    public void saveBatchDocuments() {
//        List<BidDocument> documents = new ArrayList<>();
//
//        BidDocument d0 = new BidDocument();
//        d0.setBalance("20");
//        d0.setBid("did:bid:fb03d18e135844f72e2724c3");
//        d0.setContexts("test Context");
//        d0.setIsEnable(1);
//        d0.setName("bif name");
//        d0.setType(2);
//        documents.add(d0);
//
//        BidDocument d1 = new BidDocument();
//        d1.setBalance("20");
//        d1.setBid("did:bid:fb03d18e135844f72e2724c3");
//        d1.setContexts("test Context");
//        d1.setIsEnable(1);
//        d1.setName("bif name");
//        d1.setType(2);
//        documents.add(d1);

        String string = "{'Context':'https://w3id.org/future-method/v1', 'Id':'did:bid:6cd04d5686d7034a1e295757', 'Name':'史维君', 'Type':'1', 'PublicKeys': [{'Id':'did:bid:6cd04d5686d7034a1e295757','keyId':'did:bid:6cd04d5686d7034a1e295757#key-1','Type':'secp256k1','Controller':'did:bid:6cd04d5686d7034a1e295757','Authority':'all','PublicKey':'046120c49e68b488d590afea1ebe93607bd926ea6f56f7b5b29771f964050379309a4b44f1b72a434b7a552f50516b3283f699f8a2eb22513e3658895d8731d39f'},{'Id':'did:bid:6cd04d5686d7034a1e295757','keyId':'did:bid:6cd04d5686d7034a1e295757#key-2','Type':'secp256k1','Controller':'did:bid:6cd04d5686d7034a1e295757','Authority':'base','PublicKey':'237755f59dfa66f8b1a55b8212824bd1bad707fd65e85c41975721deab7cf081389eced53a02d164dff39813857a132053458d5b6914b7c793659f9629756d46fd'}], 'Authentications': [{'ProofId':'047755f59dfa66f8b1a55b8212824bd1bad707fd65e85c41975721deab7cf081389eced53a02d164dff39813857a132053458d5b6914b7c793659f9629756d46fc','Type':'公安部','PublicKey':'046120c49e68b488d590afea1ebe93607bd926ea6f56f7b5b29771f964050379309a4b44f1b72a434b7a552f50516b3283f699f8a2eb22513e3658895d8731d39f'}], 'Attributes': [{'AttrType':'','Value':''}], 'IsEnable': '1', 'CreateTime': '2019-10-23 15:39:21.2920376 +0800 CST m=+7.416166501' ,'UpdateTime': '2019-10-23 15:39:21.2920376 +0800 CST m=+7.416166501'}\n";

        bidDocumentService.saveDocument(string, "did:bid:6cd04d5686d7034a1e295757", "800");

    }

    @Test
    public void testSaveOrUpdate(){
        String address ="did:bid:00000000000000000000000b";
        BidDocument document = new BidDocument();
        document.setBid(address);
        document.setContexts("https://w3id.org/future-method/v1");
        document.setIsEnable(1);
        document.setName(address);
        document.setType(1);
        bidDocumentService.saveOrUpdateBID(document);
    }

    @Test
    public void testGetByBid(){
        String address ="did:bid:00000000000000000000000b";
        System.out.println(bidDocumentService.getByBId(address));
    }
}