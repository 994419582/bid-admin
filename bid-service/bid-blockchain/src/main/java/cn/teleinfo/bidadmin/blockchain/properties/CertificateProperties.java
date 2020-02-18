package cn.teleinfo.bidadmin.blockchain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 路由配置类
 *
 * @author Chill
 */
@Data
@RefreshScope
@ConfigurationProperties("identity.certificate")
public class CertificateProperties {
    public String context;
    public String owner;
    public String privateKey;
    public String keyType;
    public int period;
    public String format;
}
