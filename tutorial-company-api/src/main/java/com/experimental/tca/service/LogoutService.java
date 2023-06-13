package com.experimental.tca.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.experimental.tca.repository.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.experimental.tca.model.Token;
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{
	
	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		jwt = authHeader.substring(7);
		Token storedToken = tokenRepository.findByToken(jwt)
										 .orElse(null);
		
		if(storedToken != null) {
			storedToken.setRevoked(true);
			
			tokenRepository.save(storedToken);
		}
	}
}
