package cn.teleinfo.bidadmin.blockchain.bifj.abi.certificate;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.bifj.crypto.Credentials;
import org.bifj.protocol.gbif.Gbif;
import org.bifj.tx.RawTransactionManager;
import org.bifj.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Slf4j
public class CertificateContract {

//	private GenericObjectPool<Gbif> gbifPool;
	private Gbif gbif;

	public CertificateContract(BIFClientFactory bifClientFactory) {
		this.gbif = bifClientFactory.create();
	}

	public Go_Certificate load(String privateKey) {
		Credentials credentials = Credentials.create(privateKey);
		Go_Certificate contract = Go_Certificate.load(
				"did:bid:00000000000000000000000b",
				gbif,
				new RawTransactionManager(gbif, credentials, 777),
				new StaticGasProvider(BigInteger.valueOf(200000), BigInteger.valueOf(200000))
			);
		return contract;
	}
}