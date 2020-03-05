/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.teleinfo.bidadmin.soybean.config;


import org.springblade.core.secure.registry.SecureRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * secure模块api放行配置
 *
 * @author Chill
 */
@Configuration
public class RegistryConfiguration implements WebMvcConfigurer {

	@Bean
	public SecureRegistry secureRegistry() {
		SecureRegistry secureRegistry = new SecureRegistry();
//		secureRegistry.excludePathPatterns("/soybean/front/**");

//		secureRegistry.excludePathPatterns("/wx/user/login");
//		secureRegistry.excludePathPatterns("/wx/user/token");
//		secureRegistry.excludePathPatterns("/user/saveOrUpdate");

		// TODO 权限暂时全部放开
		secureRegistry.excludePathPatterns("/wx/**");
		secureRegistry.excludePathPatterns("/user/**");
		secureRegistry.excludePathPatterns("/group/**");
		secureRegistry.excludePathPatterns("/download/**");
		return secureRegistry;
	}

}
