package com.experimental.tca.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import static com.experimental.tca.constant.SecurityEnum.MINUTES;
import static com.experimental.tca.constant.SecurityEnum.SECRET_KEY;

/**
 * @author star.lee
 */
@Service
public class JwtService {

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(
			Map<String, Object> extractClaims,
			String username) {
		
		return Jwts.builder()
				   .setClaims(extractClaims)
				   .setSubject(username)
				   .setIssuedAt(new Date(System.currentTimeMillis()))
				   .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * Integer.parseInt(MINUTES.getData())))
				   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
				   .compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(getSignInKey())
				   .build()
				   .parseClaimsJws(token)
				   .getBody();
	}

	private Key getSignInKey() {

		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY.getData());
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
