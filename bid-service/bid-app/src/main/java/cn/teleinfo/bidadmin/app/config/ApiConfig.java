package cn.teleinfo.bidadmin.app.config;

import com.baidu.aip.face.AipFace;
import com.fri.ctid.security.jit.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApiProperties.class)
public class ApiConfig {

    @Autowired
    private ApiProperties properties;
    /**
     * 签发者
     */
//    @Bean
//    public Api signerAPI() throws Exception {
//        Api api = new Api();
//        api.initConnection(properties.getConfigUrl());
//        return api;
//    }

    @Bean
    public AipFace getAipFaceClient() {
        AipFace client = new AipFace(properties.getAppId(), properties.getApiKey(), properties.getSecretKey());

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);  // 建立连接的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000);     // 通过打开的连接传输数据的超时时间（单位：毫秒）

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);       // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);     // 设置socket代理

        return client;
    }

}
