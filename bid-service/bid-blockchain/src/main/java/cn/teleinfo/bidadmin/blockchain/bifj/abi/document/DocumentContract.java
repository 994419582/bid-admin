package cn.teleinfo.bidadmin.blockchain.bifj.abi.document;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.bifj.crypto.Credentials;
import org.bifj.protocol.gbif.Gbif;
import org.bifj.tx.RawTransactionManager;
import org.bifj.tx.gas.StaticGasProvider;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentContract {

	private GenericObjectPool<Gbif> gbifPool;

	public DocumentContract(BIFClientFactory bifClientFactory) {
		this.gbifPool = new GenericObjectPool<>(bifClientFactory);
	}

	public Go_Document load(String privateKey) {
		Gbif gbif = null;
		try {
			gbif=gbifPool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Credentials credentials = Credentials.create(privateKey);
		Go_Document contract = Go_Document.load(
				"did:bid:00000000000000000000000a",
				gbif,
				new RawTransactionManager(gbif, credentials, 777),
				new StaticGasProvider(BigInteger.valueOf(200000), BigInteger.valueOf(200000))
			);
		return contract;
	}
}