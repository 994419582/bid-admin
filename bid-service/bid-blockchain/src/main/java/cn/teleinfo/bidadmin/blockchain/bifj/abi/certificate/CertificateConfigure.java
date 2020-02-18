package cn.teleinfo.bidadmin.blockchain.bifj.abi.certificate;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientFactory;
import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BifProperties.class)
public class CertificateConfigure {

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
    public CertificateContract getCertificateContract() {
        return new CertificateContract(getBIFClientFactory());
    }

    @Bean
    public Go_Certificate getCertificate() {
        return getCertificateContract().load(bifProperties.loadMsgPrivate);
    }
}