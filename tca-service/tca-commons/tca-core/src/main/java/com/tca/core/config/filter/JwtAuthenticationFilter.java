//package com.tca.core.config.filter;
//
//import com.tca.core.config.RequestHolder;
//import com.tca.core.service.CommonService;
//import com.tca.core.service.JwtService;
//import lombok.NonNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author star.lee
// */
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter{
//
//	@Autowired
//	private JwtService jwtService;
//
//
//	private CommonService commonService;
//
//	@Autowired
//	private SecurityFilterChain securityFilterChain;
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return username -> commonService.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found."));
//	}
//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
//
//	@Override
//	protected void doFilterInternal(
//			@NonNull HttpServletRequest request,
//			@NonNull HttpServletResponse response,
//			@NonNull FilterChain filterChain)
//			throws ServletException, IOException {
//		RequestHolder.init(request);
//		final String authHeader = request.getHeader("Authorization");
//		final String jwt;
//		final String userName;
//		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		jwt = authHeader.substring(7);
//		userName = jwtService.extractUsername(jwt);
//		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = this.userDetailsService().loadUserByUsername(userName);
//			boolean isTokenValid = commonService.verifyToken(jwt).isPresent();
//			if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//						userDetails,
//						null,
//						userDetails.getAuthorities());
//				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authToken);
//
//			}
//			if(!jwtService.isTokenValid(jwt, userDetails)) {
//				commonService.clearToken(jwt);
//			}
//
//			filterChain.doFilter(request, response);
//
//		}
//		RequestHolder.clean();
//	}
//
//}