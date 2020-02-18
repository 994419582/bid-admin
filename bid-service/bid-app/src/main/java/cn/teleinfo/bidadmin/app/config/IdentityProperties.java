package cn.teleinfo.bidadmin.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@ConfigurationProperties("api.identity")
public class IdentityProperties {

    private String customerNumber;

    private String appName;

    private String cardReaderVersion;

    private String liveDetectionControlVersion;
    private String authCodeControlVersion;
    private String authMode;

    private String configUrl;
}
