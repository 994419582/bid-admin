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
public class ElectionTest {

	static {
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	@Autowired
	private ElectionConfigure electionConfigure;

	private static String privateKey = "0x152ddfc0102bd902868bebf027636098c389e94499e1b0c98cb92db461b95efd";

	private static String privateKey1 = "0x55bfd5d1d98fa87a75bd2c422ba0822da5e05a99ce73d95d81aab04c94b55bcf";
	private static String privateKey2 = "0xaaff6f565fe9a9b8ad2046243ea59ae021700b79ba37a57c1d74b49deefc3be0";
	private static String privateKey3 = "0x2cb8cf63e081ba517537a0be12147b387ca53aa4fac5755c0dadaacc69c71988";
	private static String privateKey4 = "0x758d726760a638b68b6acbb7d97bcfdeb5a07ee5db0d150220de94b55261fd64";


    Utf8String website1 = new Utf8String("www.node0.com");
    Utf8String name1 = new Utf8String("bifnode0");
    Utf8String nodeUrl1 = new Utf8String("enode://18fd3b9588524e3bfc1029252a31b478499c0591cdfb86b29419265502abc9b07e185f6bf9c03baf44ac615450a31af30018aebde493a1d2490b93df31d19e60@127.0.0.1:44444");


    Utf8String website2 = new Utf8String("www.node1.com");
    Utf8String name2 = new Utf8String("bifnode1");
    Utf8String nodeUrl2 = new Utf8String("enode://d3f6322f2e6f6455846176120e95d2ba1bf392ef33f54ac6f5c8edf8e01c90be2fc346a8c931d7a4b7cf5566608db334e238e74582561670f854c181399d5295@127.0.0.1:44445");


    Utf8String website3 = new Utf8String("www.node2.com");
    Utf8String name3 = new Utf8String("bifnode2");
    Utf8String nodeUrl3 = new Utf8String("enode://c9684da91f2d5d661ce63a77f0165beee97e83ccfd283acfff4103a98c793a7f96c01854834298aaf5a77b235c3046a472881fe58a8ef36292aa79532ba165f5@127.0.0.1:44446");


    Utf8String website4 = new Utf8String("www.node3.com");
    Utf8String name4 = new Utf8String("bifnode3");
    Utf8String nodeUrl4 = new Utf8String("enode://4f6a42e6bf128a81c0465ed97c31dc2e8537f7c58d522a7310530a3f307483a7df009dff3463080ddab32e076e065ef408bb354d509ee11abf6ff36abc2ff772@127.0.0.1:44447");

	Utf8String proxy = new Utf8String("");

	Uint256 stakeCount1 = new Uint256(100);
	Uint256 stakeCount2 = new Uint256(200);
	Uint256 stakeCount3 = new Uint256(300);
	Uint256 stakeCount4 = new Uint256(400);

	Utf8String candidate4 = new Utf8String("did:bid:b1f819f1411ea7b100b5897d,did:bid:20788becd0a573d6354605de");
	Utf8String candidate3 = new Utf8String("did:bid:c82a830cc31ccc052bc78837,did:bid:20788becd0a573d6354605de");
	Utf8String candidate2 = new Utf8String("did:bid:200fd76b9548f0effa824a23,did:bid:20788becd0a573d6354605de");
	Utf8String candidate1 = new Utf8String("did:bid:20788becd0a573d6354605de,did:bid:200fd76b9548f0effa824a23");

	@Test
	public void testRegisterWitness() {
		testRegisterWitness1();
		testRegisterWitness2();
		testRegisterWitness3();
		testRegisterWitness4();
	}

	@Test
	public void testStake() {
		testStake1();
		testStake2();
		testStake3();
		testStake4();
	}

	@Test
	public void testVoteWitnesses() {
		testVoteWitnesses1();
		testVoteWitnesses2();
		testVoteWitnesses3();
		testVoteWitnesses4();
	}

	@Test
	public void testRegisterWitness1() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.registerWitness(nodeUrl1,website1,name1).sendAsync().get();
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
    public void testRegisterWitness2() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey2);
        try {
            TransactionReceipt receipt = c.registerWitness(nodeUrl2,website2,name2).sendAsync().get();
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
    public void testRegisterWitness3() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey3);
        try {
            TransactionReceipt receipt = c.registerWitness(nodeUrl3,website3,name3).sendAsync().get();
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
    public void testRegisterWitness4() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey4);
        try {
            TransactionReceipt receipt = c.registerWitness(nodeUrl4,website4,name4).sendAsync().get();
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
	public void testUnregisterWitness() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.unregisterWitness().sendAsync().get();
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
	public void testVoteWitnesses1() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.voteWitnesses(candidate1).sendAsync().get();
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
    public void testVoteWitnesses2() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey2);
        try {
            TransactionReceipt receipt = c.voteWitnesses(candidate2).sendAsync().get();
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
    public void testVoteWitnesses3() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey3);
        try {
            TransactionReceipt receipt = c.voteWitnesses(candidate3).sendAsync().get();
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
    public void testVoteWitnesses4() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey4);
        try {
            TransactionReceipt receipt = c.voteWitnesses(candidate4).sendAsync().get();
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
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.cancelVote().sendAsync().get();
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
	public void testStartProxy() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.startProxy().sendAsync().get();
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
	public void testStopProxy() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.stopProxy().sendAsync().get();
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
	public void testCancelProxy() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.cancelProxy().sendAsync().get();
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
	public void testSetProxy() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.setProxy(proxy).sendAsync().get();
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
	public void testStake1() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.stake(stakeCount1).sendAsync().get();
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
    public void testStake2() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey2);
        try {
            TransactionReceipt receipt = c.stake(stakeCount2).sendAsync().get();
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
    public void testStake3() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey3);
        try {
            TransactionReceipt receipt = c.stake(stakeCount3).sendAsync().get();
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
    public void testStake4() {
        Go_Election c = electionConfigure.getElectionContract().load(privateKey4);
        try {
            TransactionReceipt receipt = c.stake(stakeCount4).sendAsync().get();
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
	public void testUnStake() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.unStake().sendAsync().get();
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
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.extractOwnBounty().sendAsync().get();
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
	public void testIssueAddtitionalBounty() {
		Go_Election c = electionConfigure.getElectionContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.issueAddtitionalBounty().sendAsync().get();
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