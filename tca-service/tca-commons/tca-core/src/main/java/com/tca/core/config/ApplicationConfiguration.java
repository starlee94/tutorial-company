package com.tca.core.config;

import com.tca.core.config.filter.AuthSignatureFilter;
import com.tca.core.constant.finals.SpringBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


/**
 * @author star.lee
 */
@Import(SpringDocConfig.class)
public class ApplicationConfiguration {

	@Bean
	public SpringBeanFactory springBeanFactory() { return new SpringBeanFactory(); }

	@Bean
	public AuthSignatureFilter authSignatureFilter() {return new AuthSignatureFilter(); }

//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
}
