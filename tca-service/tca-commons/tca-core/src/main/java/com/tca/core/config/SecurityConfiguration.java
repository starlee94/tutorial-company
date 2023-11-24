//package com.tca.core.config;
//
//import com.tca.core.config.filter.JwtAuthenticationFilter;
//import com.tca.core.constant.enums.AddressUtil;
//import com.tca.core.service.LogoutService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * @author star.lee
// */
//
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//	@Bean
//	public LogoutService logoutService() {return new LogoutService(); }
//
//	@Bean
//	public JwtAuthenticationFilter jwtAuthenticationFilter() { return new JwtAuthenticationFilter(); }
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//		.csrf()
//		.disable()
//		.authorizeRequests()
//		.antMatchers(AddressUtil.AUTH_FULL.getAddress())
//		.permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and()
//		.authenticationProvider(jwtAuthenticationFilter().authenticationProvider())
//		.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//		.logout()
//		.logoutUrl("/api/v1/auth/logout")
//		.addLogoutHandler(logoutService())
//		.logoutSuccessHandler((request, response, authentication) ->
//			SecurityContextHolder.clearContext());
//
//		return http.build();
//	}
//
//
//
//}
