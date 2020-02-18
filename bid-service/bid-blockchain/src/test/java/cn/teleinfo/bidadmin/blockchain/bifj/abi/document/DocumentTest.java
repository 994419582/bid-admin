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
public class DocumentTest {

	static {
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	@Autowired
	private DocumentConfigure documentConfigure;

	private static String privateKey = "758d726760a638b68b6acbb7d97bcfdeb5a07ee5db0d150220de94b55261fd64";

	private static String privateKey1 = "0x55bfd5d1d98fa87a75bd2c422ba0822da5e05a99ce73d95d81aab04c94b55bcf";
	private static String privateKey2 = "0xaaff6f565fe9a9b8ad2046243ea59ae021700b79ba37a57c1d74b49deefc3be0";
	private static String privateKey3 = "0x2cb8cf63e081ba517537a0be12147b387ca53aa4fac5755c0dadaacc69c71988";
	private static String privateKey4 = "0x758d726760a638b68b6acbb7d97bcfdeb5a07ee5db0d150220de94b55261fd64";


	Utf8String bidName1 = new Utf8String("bidbifddoname");
	Utf8String bidName2 = new Utf8String("bidbifddoname");
	Utf8String bidName3 = new Utf8String("bidbifddoname");
	Utf8String bidName4 = new Utf8String("bidbifddoname");

	Utf8String authority = new Utf8String("all");
	Utf8String typePuk1 = new Utf8String("puktype1");
	Utf8String publicKey1 = new Utf8String("pub1");

	Utf8String typePuk2 = new Utf8String("puktype2");
	Utf8String publicKey2 = new Utf8String("pub2");

	Utf8String typePuk3 = new Utf8String("puktype3");
	Utf8String publicKey3 = new Utf8String("pub3");

	Utf8String typePuk4 = new Utf8String("puktype4");
	Utf8String publicKey4 = new Utf8String("pub4");

	Utf8String typePro = new Utf8String("0");
	Utf8String proofID = new Utf8String("");

	Utf8String typeAttr1 = new Utf8String("arrttype1");
	Utf8String value1 = new Utf8String("arrt value 11");

	Utf8String typeAttr2 = new Utf8String("arrttype2");
	Utf8String value2 = new Utf8String("arrt value 22");

	Utf8String typeAttr3 = new Utf8String("arrttype3");
	Utf8String value3 = new Utf8String("arrt value 33");

	Utf8String typeAttr4 = new Utf8String("arrttype4");
	Utf8String value4 = new Utf8String("arrt value 44");

	Uint64 bidType = new Uint64(0);

	@Test
	public void testInitializationDDO() {
		testInitializationDDO1();
		testInitializationDDO2();
		testInitializationDDO3();
		testInitializationDDO4();
	}

	@Test
	public void testSetBidName() {
		testSetBidName1();
		testSetBidName2();
		testSetBidName3();
		testSetBidName4();
	}

	@Test
	public void testAddPublicKey() {
		testAddPublicKey1();
		testAddPublicKey2();
		testAddPublicKey3();
		testAddPublicKey4();
	}

	@Test
	public void testAddAttr() {
		testAddAttr1();
		testAddAttr2();
		testAddAttr3();
		testAddAttr4();
	}

	@Test
	public void testInitializationDDO1() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.InitializationDDO(bidType).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testInitializationDDO2() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey2);
		try {
			TransactionReceipt receipt = c.InitializationDDO(bidType).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testInitializationDDO3() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey3);
		try {
			TransactionReceipt receipt = c.InitializationDDO(bidType).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testInitializationDDO4() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey4);
		try {
			TransactionReceipt receipt = c.InitializationDDO(bidType).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testSetBidName1() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.SetBidName(bidName1).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testSetBidName2() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey2);
		try {
			TransactionReceipt receipt = c.SetBidName(bidName2).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testSetBidName3() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey3);
		try {
			TransactionReceipt receipt = c.SetBidName(bidName3).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testSetBidName4() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey4);
		try {
			TransactionReceipt receipt = c.SetBidName(bidName4).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testAddPublicKey1() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.AddPublicKey(typePuk1,authority,publicKey1).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testAddPublicKey2() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey2);
		try {
			TransactionReceipt receipt = c.AddPublicKey(typePuk2,authority,publicKey2).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}@Test
	public void testAddPublicKey3() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey3);
		try {
			TransactionReceipt receipt = c.AddPublicKey(typePuk3,authority,publicKey3).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}@Test
	public void testAddPublicKey4() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey4);
		try {
			TransactionReceipt receipt = c.AddPublicKey(typePuk4,authority,publicKey4).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testDeletePublicKey() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.DeletePublicKey(publicKey1).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testAddProof() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.AddProof(typePro,proofID).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testDeleteProof() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.DeleteProof(proofID).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testAddAttr1() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey1);
		try {
			TransactionReceipt receipt = c.AddAttr(typeAttr1,value1).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testAddAttr2() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey2);
		try {
			TransactionReceipt receipt = c.AddAttr(typeAttr2,value2).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testAddAttr3() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey3);
		try {
			TransactionReceipt receipt = c.AddAttr(typeAttr3,value3).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testAddAttr4() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey4);
		try {
			TransactionReceipt receipt = c.AddAttr(typeAttr4,value4).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testDeleteAttr() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.DeleteAttr(typeAttr1).sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testEnable() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.Enable().sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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
	public void testDisable() {
		Go_Document c = documentConfigure.getDocumentContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.Disable().sendAsync().get();
			System.out.println(receipt);
			List<Go_Document.BidEventEventResponse> events = c.getBidEventEvents(receipt);
			for (Go_Document.BidEventEventResponse e : events) {
				System.out.println(e.methodName.getValue());
				System.out.println(e.reason.getValue());
				System.out.println(e.status.getValue());
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