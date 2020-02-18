package cn.teleinfo.bidadmin.blockchain.bifj.abi.election;

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
public class ElectionTestQuery {

	static {
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	@Autowired
	private ElectionConfigure electionConfigure;

//	private static String privateKey = "cfce764097cad46e5601609d7d129ed8c480f308e3a2f94e6b8fbc577f53c5e6";
	private static String privateKey = "758d726760a638b68b6acbb7d97bcfdeb5a07ee5db0d150220de94b55261fd64";

	Utf8String voteraddress = new Utf8String("did:bid:20788becd0a573d6354605de");
	Utf8String candiaddress = new Utf8String("did:bid:20788becd0a573d6354605de");

	@Test
	public void testQueryCandidates() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			List<Type> types = c.queryCandidates(candiaddress).sendAsync().get();
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
	public void testQueryAllCandidates() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			List<Type> types = c.queryAllCandidates().sendAsync().get();
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
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			List<Type> types = c.queryVoter(voteraddress).sendAsync().get();
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
	public void testQueryStake() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			List<Type> types = c.queryStake(candiaddress).sendAsync().get();
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
	public void testQueryVoterList() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			List<Type> types = c.queryVoterList(candiaddress).sendAsync().get();
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
}