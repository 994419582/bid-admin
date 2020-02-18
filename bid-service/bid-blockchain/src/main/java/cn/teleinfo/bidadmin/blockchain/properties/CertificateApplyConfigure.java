package cn.teleinfo.bidadmin.blockchain.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * FTPClient配置类，封装了FTPClient的相关配置
 */
@Configuration
@EnableConfigurationProperties(CertificateProperties.class)
public class CertificateApplyConfigure {

    private CertificateProperties certificateProperties;

    @Autowired
    public void setCertificateProperties(CertificateProperties certificateProperties) {
        this.certificateProperties = certificateProperties;
    }
}
