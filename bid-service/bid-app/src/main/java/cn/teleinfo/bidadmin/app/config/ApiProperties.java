package cn.teleinfo.bidadmin.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties("api.app.face.bif")
public class ApiProperties {

    private String appId;

    private String apiKey;

    private String secretKey;

}
