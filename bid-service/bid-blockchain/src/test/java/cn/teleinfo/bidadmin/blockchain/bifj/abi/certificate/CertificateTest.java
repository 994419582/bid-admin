package cn.teleinfo.bidadmin.blockchain.bifj.abi.certificate;

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
public class CertificateTest {

	@Autowired
	private CertificateConfigure certificateConfigure;

	private static String privateKey = "cfce764097cad46e5601609d7d129ed8c480f308e3a2f94e6b8fbc577f53c5e6";

	Utf8String SubjectPublicKey = new Utf8String("");
	Uint64 Period = new Uint64(1);
	Utf8String Subject = new Utf8String("");
	Utf8String Id = new Utf8String("");
	Utf8String id = new Utf8String("");
	Utf8String IssuerSignature = new Utf8String("");
	Utf8String SubjectSignature = new Utf8String("");
	Utf8String IssuerAlgorithm = new Utf8String("");
	Utf8String Context = new Utf8String("");
	Utf8String SubjectAlgorithm = new Utf8String("");

	@Test
	public void testRegisterCertificate() {
		Go_Certificate c = certificateConfigure.getCertificateContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.registerCertificate(Id,Context,Subject,Period,IssuerAlgorithm,IssuerSignature,SubjectPublicKey,SubjectAlgorithm,SubjectSignature).sendAsync().get();
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
	public void testRevocedCertificate() {
		Go_Certificate c = certificateConfigure.getCertificateContract().load(privateKey);
		try {
			TransactionReceipt receipt = c.revocedCertificate(id).sendAsync().get();
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