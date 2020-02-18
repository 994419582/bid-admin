package cn.teleinfo.bidadmin.blockchain.bifj.abi.document;

import java.util.concurrent.ExecutionException;
import org.bifj.abi.datatypes.*;
import org.bifj.abi.datatypes.generated.*;
import org.bifj.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class DocumentTestQuery {

	static {
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	@Autowired
	private DocumentConfigure documentConfigure;

	private static String privateKey = "758d726760a638b68b6acbb7d97bcfdeb5a07ee5db0d150220de94b55261fd64";

	Uint64 key = new Uint64(0);
	Utf8String value = new Utf8String("did:bid:20788becd0a573d6354605de");

	@Test
	public void testFindDDOByType() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			System.out.println(c.FindDDOByType(key,value).sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testIsEnable() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			System.out.println(c.IsEnable(key,value).sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}