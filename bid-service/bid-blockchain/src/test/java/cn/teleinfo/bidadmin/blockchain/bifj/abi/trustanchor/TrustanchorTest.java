package cn.teleinfo.bidadmin.blockchain.bifj.abi.trustanchor;

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
public class TrustanchorTest {

	static {
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	@Autowired
	private TrustanchorConfigure trustanchorConfigure;

	private static String privateKey = "cfce764097cad46e5601609d7d129ed8c480f308e3a2f94e6b8fbc577f53c5e6";

	private static String privateKey1 = "0x55bfd5d1d98fa87a75bd2c422ba0822da5e05a99ce73d95d81aab04c94b55bcf";
	private static String privateKey2 = "0xaaff6f565fe9a9b8ad2046243ea59ae021700b79ba37a57c1d74b49deefc3be0";
	private static String privateKey3 = "0x2cb8cf63e081ba517537a0be12147b387ca53aa4fac5755c0dadaacc69c71988";
	private static String privateKey4 = "0x758d726760a638b68b6acbb7d97bcfdeb5a07ee5db0d150220de94b55261fd64";


	Uint64 anchortype0 = new Uint64(10);
	Uint64 anchortype1 = new Uint64(11);
	Utf8String desc = new Utf8String("desc");
	Utf8String website = new Utf8String("web");

	Utf8String candidate1 = new Utf8String("did:bid:b1f819f1411ea7b100b5897d");
	Utf8String candidate2 = new Utf8String("did:bid:c82a830cc31ccc052bc78837");
	Utf8String candidate3 = new Utf8String("did:bid:200fd76b9548f0effa824a23");
	Utf8String candidate4 = new Utf8String("did:bid:20788becd0a573d6354605de");

	Utf8String email = new Utf8String("ema");
	Utf8String company = new Utf8String("com");
	Utf8String anchorname = new Utf8String("name");
	Utf8String companyurl = new Utf8String("cu");
	Utf8String companyUrl = new Utf8String("cu");

	Utf8String anchor = new Utf8String("ch");

	Utf8String documenturl = new Utf8String("du");
	Utf8String documentUrl = new Utf8String("du");

	@Test
	public void testRegisterTrustAnchor() {
		testRegisterTrustAnchor1();
		testRegisterTrustAnchor2();
//		testRegisterTrustAnchor3();
//		testRegisterTrustAnchor4();
//
//		testRegisterTrustAnchor5();
//		testRegisterTrustAnchor6();
		testRegisterTrustAnchor7();
		testRegisterTrustAnchor8();
	}

	@Test
	public void testVoteElect() {
		testVoteElect1();
		testVoteElect2();
		testVoteElect3();
		testVoteElect4();
	}

	@Test
	public void testRegisterTrustAnchor1() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate1,anchortype0,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterTrustAnchor2() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey2);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate2,anchortype0,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterTrustAnchor3() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey3);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate3,anchortype0,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterTrustAnchor4() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey4);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate4,anchortype0,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterTrustAnchor5() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate1,anchortype1,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterTrustAnchor6() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey2);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate2,anchortype1,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterTrustAnchor7() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey3);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate3,anchortype1,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRegisterTrustAnchor8() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey4);
		try {
			TransactionReceipt receipt = c.registerTrustAnchor(candidate4,anchortype1,anchorname,company,companyurl,website,documenturl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testUnRegisterTrustAnchor() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.unRegisterTrustAnchor(anchor).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testUpdateBaseAnchorInfo() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.updateBaseAnchorInfo(anchor,companyUrl,email).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testUpdateExetendAnchorInfo() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.updateExetendAnchorInfo(anchor,companyUrl,website,documentUrl,email,desc).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testExtractOwnBounty() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.extractOwnBounty(anchor).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testVoteElect1() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.voteElect(candidate1).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testVoteElect2() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey2);
		try {
			TransactionReceipt receipt = c.voteElect(candidate2).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testVoteElect3() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey3);
		try {
			TransactionReceipt receipt = c.voteElect(candidate3).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testVoteElect4() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey4);
		try {
			TransactionReceipt receipt = c.voteElect(candidate4).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCancelVote() {
		Go_Trustanchor c = trustanchorConfigure.getTrustanchorContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.cancelVote(candidate1).sendAsync().get();
			System.out.println(receipt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}