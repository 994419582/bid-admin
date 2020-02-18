package cn.teleinfo.bidadmin.blockchain.properties;

import cn.teleinfo.bidadmin.blockchain.ipfs.IPFSClientTemplate;
import cn.teleinfo.bidadmin.blockchain.ipfs.IpfsClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FTPClient配置类，封装了FTPClient的相关配置
 *
 */
@Configuration
@EnableConfigurationProperties(IpfsProperties.class)
public class IPFSClientConfigure {

    private IpfsProperties ipfsProperties;

    @Autowired
    public void setIpfsProperties(IpfsProperties ipfsProperties) {
        this.ipfsProperties = ipfsProperties;
    }

    @Bean
    public IpfsClientFactory getIpfsClientFactory() {
        return new IpfsClientFactory(ipfsProperties);
    }

    @Bean
    public IPFSClientTemplate getIPFSTemplate() {
        return new IPFSClientTemplate(getIpfsClientFactory());
    }

}
