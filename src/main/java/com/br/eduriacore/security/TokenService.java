package com.br.eduriacore.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    private Long EXPIRATION = 86400000L;
	
	private String SECRET = "e7bc404e950d3b33ac8766582ac8974b01397ea10c815bd5b6ed6e3c264c5de7c680c7ddfcd54f122818c6edbc21e949e249581d0a35c6afe7c96c898b3628a8";

    public String generateToken(Long personId) {
  
		Date now        = new Date();
		Date expiration = new Date(now.getTime() + this.EXPIRATION);

		return Jwts.builder()
				.setIssuer("Eduria Login")
				.setSubject(personId.toString())
				.setIssuedAt(now)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
    }

    public boolean verifyIfValid(String token) {
        
        try {			
			Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Long getPersonId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
