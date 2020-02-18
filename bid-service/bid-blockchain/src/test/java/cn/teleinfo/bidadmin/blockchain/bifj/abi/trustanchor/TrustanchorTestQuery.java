package cn.teleinfo.bidadmin.blockchain.bifj.abi.trustanchor;

import java.util.concurrent.ExecutionException;
import org.bifj.abi.datatypes.*;
import org.bifj.abi.datatypes.generated.*;
import org.bifj.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
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
public class TrustanchorTestQuery {

	static {
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	@Autowired
	private TrustanchorConfigure trustanchorConfigure;

	private static String privateKey = "cfce764097cad46e5601609d7d129ed8c480f308e3a2f94e6b8fbc577f53c5e6";

	Utf8String address = new Utf8String("did:bid:b1f819f1411ea7b100b5897d");
	Utf8String anchor = new Utf8String("did:bid:b1f819f1411ea7b100b5897d");
	Utf8String voter1 = new Utf8String("did:bid:c82a830cc31ccc052bc78837");
	Utf8String voter2 = new Utf8String("did:bid:b1f819f1411ea7b100b5897d");
	Utf8String voter3 = new Utf8String("did:bid:20788becd0a573d6354605de");
	Utf8String voter4 = new Utf8String("did:bid:200fd76b9548f0effa824a23");

	Go_Trustanchor c = null;

	@Before
	public void beforeRun() {
		c = trustanchorConfigure.getTrustanchorContract().load(privateKey);
	}

	@Test
	public void testIsTrustAnchor() {
		try {
			System.out.println(c.isTrustAnchor(address).sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryTrustAnchor() {
		try {
			List<Type> types = c.queryTrustAnchor(anchor).sendAsync().get();
			for (Type type : types) {
				System.out.println(type.getTypeAsString() + ": " + type.getValue());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryVoter() {
		testQueryVoter1();
		testQueryVoter2();
		testQueryVoter3();
		testQueryVoter4();
	}
	@Test
	public void testQueryVoter1() {
		try {
			System.out.println(c.queryVoter(voter1).sendAsync().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryVoter2() {
		try {
			System.out.println(c.queryVoter(voter2).sendAsync().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryVoter3() {
		try {
			System.out.println(c.queryVoter(voter3).sendAsync().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryVoter4() {
		try {
			System.out.println(c.queryVoter(voter4).sendAsync().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCheckSenderAddress() {
		try {
			System.out.println(c.checkSenderAddress(voter4).sendAsync().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryTrustAnchorStatus() {
		try {
			System.out.println(c.queryTrustAnchorStatus(anchor).sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryBaseTrustAnchorList() {
		try {
			System.out.println(c.queryBaseTrustAnchorList().sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryBaseTrustAnchorNum() {
		try {
			System.out.println(c.queryBaseTrustAnchorNum().sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryExpendTrustAnchorList() {
		try {
			System.out.println(c.queryExpendTrustAnchorList().sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryExpendTrustAnchorNum() {
		try {
			System.out.println(c.queryExpendTrustAnchorNum().sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}