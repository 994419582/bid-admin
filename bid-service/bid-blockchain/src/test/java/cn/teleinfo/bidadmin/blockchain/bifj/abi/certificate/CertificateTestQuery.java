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
public class CertificateTestQuery {

	@Autowired
	private CertificateConfigure certificateConfigure;

	private static String privateKey = "cfce764097cad46e5601609d7d129ed8c480f308e3a2f94e6b8fbc577f53c5e6";

	Utf8String id = new Utf8String("");

	@Test
	public void testQueryPeriod() {
		Go_Certificate c = certificateConfigure.getCertificateContract().load(privateKey);
		try {
			System.out.println(c.queryPeriod(id).sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryActive() {
		Go_Certificate c = certificateConfigure.getCertificateContract().load(privateKey);
		try {
			System.out.println(c.queryActive(id).sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryIssuer() {
		Go_Certificate c = certificateConfigure.getCertificateContract().load(privateKey);
		try {
			System.out.println(c.queryIssuer(id).sendAsync().get().getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryIssuerSignature() {
		Go_Certificate c = certificateConfigure.getCertificateContract().load(privateKey);
		try {
			List<Type> types = c.queryIssuerSignature(id).sendAsync().get();
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
	public void testQuerySubjectSignature() {
		Go_Certificate c = certificateConfigure.getCertificateContract().load(privateKey);
		try {
			List<Type> types = c.querySubjectSignature(id).sendAsync().get();
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