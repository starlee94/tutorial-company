package com.experimental.tca.filter;

import java.io.IOException;

import com.experimental.tca.mapper.TokenMapper;
import com.experimental.tca.entity.Token;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.experimental.tca.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author star.lee
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Autowired
	private final TokenMapper tokenMapper;
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userName;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		userName = jwtService.extractUsername(jwt);
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			boolean isTokenValid = tokenMapper.findByToken(jwt)
					.map(t -> !t.isRevoked())
					.orElse(false);
			if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, 
						null,
						userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
			if(!jwtService.isTokenValid(jwt, userDetails)) {
				tokenMapper.delete(tokenMapper.findByToken(jwt).orElse(new Token()));
			}
			
			filterChain.doFilter(request, response);
			
		}
	}

}
