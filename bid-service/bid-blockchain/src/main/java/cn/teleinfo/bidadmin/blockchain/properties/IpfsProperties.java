package cn.teleinfo.bidadmin.blockchain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(prefix = "ipfs")
public class IpfsProperties {
    private String multiAddr;
    private String uploadPort;
	private String catPort;
    private String host;

    
}
