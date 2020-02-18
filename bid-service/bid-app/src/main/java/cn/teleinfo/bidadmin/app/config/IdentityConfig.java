package cn.teleinfo.bidadmin.app.config;

import com.fri.ctid.security.jit.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IdentityProperties.class)
public class IdentityConfig {

    @Autowired
    IdentityProperties properties;

    /**
     * 签发者
     */
    public IdentityProperties getProperties()  {
        return this.properties;
    }

    @Bean
    public Api signerAPI() throws Exception {
        Api api = new Api();
        api.initConnection(properties.getConfigUrl());
        return api;
    }

}
