package cn.teleinfo.bidadmin.blockchain.bifj.abi.document;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientFactory;
import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BifProperties.class)
public class DocumentConfigure {

	private BifProperties bifProperties;


	@Autowired
	public void setBipProperties(BifProperties bifProperties) {
		this.bifProperties = bifProperties;
	}

	@Bean
	public BIFClientFactory getBIFClientFactory() {
		return new BIFClientFactory(bifProperties);
	}

	@Bean
	public DocumentContract getDocumentContract() {
		return new DocumentContract(getBIFClientFactory());
	}

	@Bean
	public Go_Document getDocument() {
		return getDocumentContract().load(bifProperties.loadMsgPrivate);
	}
}