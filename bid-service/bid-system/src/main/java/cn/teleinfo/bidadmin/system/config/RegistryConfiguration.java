package cn.teleinfo.bidadmin.system.config;

import org.springblade.core.secure.registry.SecureRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistryConfiguration {

    @Bean
    public SecureRegistry secureRegistry() {
        SecureRegistry secureRegistry = new SecureRegistry();
        secureRegistry.excludePathPatterns("/front/**");
        secureRegistry.excludePathPatterns("/dict/**");
        return secureRegistry;
    }
}
