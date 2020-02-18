package cn.teleinfo.bidadmin.blockchain.properties;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientFactory;
import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FTPClient配置类，封装了FTPClient的相关配置
 *
 */
@Configuration
@EnableConfigurationProperties(BifProperties.class)
public class BIFSClientConfigure {

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
    public BIFClientTemplate getBIFTemplate() {
        return new BIFClientTemplate(getBIFClientFactory());
    }

}
