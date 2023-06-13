package com.experimental.tca.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "782F4125442A472D4B6150645367566B59703373367639792442264528482B4D";
	private int minutes = 120; //adjust how long will the token expired in minutes
	private Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * minutes);
		
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
		
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(
			Map<String, Object> extractClaims,
			String Username) {
		
		return Jwts.builder()
				   .setClaims(extractClaims)
				   .setSubject(Username)
				   .setIssuedAt(new Date(System.currentTimeMillis()))
				   .setExpiration(expiration)
				   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
				   .compact();
	}
	
	public String generateToken(
			Map<String, Object> extractClaims,
			UserDetails userDetails) {
		
		return Jwts.builder()
				   .setClaims(extractClaims)
				   .setIssuedAt(new Date(System.currentTimeMillis()))
				   .setExpiration(expiration)
				   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
				   .compact();
	}
	
	public String generateToken(
			Map<String, Object> extractClaims) {
		
		return Jwts.builder()
				   .setClaims(extractClaims)
				   .setIssuedAt(new Date(System.currentTimeMillis()))
				   .setExpiration(expiration)
				   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
				   .compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
