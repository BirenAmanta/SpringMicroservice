package com.mindtree.apigateway.utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mindtree.apigateway.exception.APIGatewayException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
@Component
public class JwtUtility {
	@Value("${jwt.secret}")
	private String jwtSecret;

	public Claims getClaims(String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public void validateToken(final String token) throws APIGatewayException{
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new APIGatewayException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new APIGatewayException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new APIGatewayException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new APIGatewayException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new APIGatewayException("JWT claims string is empty.");
		}
	}
}
