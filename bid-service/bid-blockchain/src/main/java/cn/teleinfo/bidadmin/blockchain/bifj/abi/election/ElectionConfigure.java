package cn.teleinfo.bidadmin.blockchain.bifj.abi.election;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientFactory;
import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BifProperties.class)
public class ElectionConfigure {

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
	public ElectionContract getElectionContract() {
		return new ElectionContract(getBIFClientFactory());
	}
	@Bean
	public Go_Election getElection() {
		return getElectionContract().load(bifProperties.loadMsgPrivate);
	}
}