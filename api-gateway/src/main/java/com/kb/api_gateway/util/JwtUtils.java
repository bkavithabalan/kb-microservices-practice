package com.kb.api_gateway.util;

import java.nio.charset.StandardCharsets;


import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;
	
	private SecretKey getSigningKey()
	{
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	 public Claims validateAndGetClaims(String token) {
	        return Jwts.parser()
	                .verifyWith(getSigningKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }
}
