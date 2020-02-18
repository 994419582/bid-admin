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
@ConfigurationProperties("bif")
public class BifProperties {
    public  String[] urls ;
    public String loadMsgPrivate;
    public long chainId;
    public long superNode;
}
