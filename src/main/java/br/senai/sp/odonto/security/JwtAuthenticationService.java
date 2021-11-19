package br.senai.sp.odonto.security;


import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationService {

	private static final String SECRET_KEY = Base64.getEncoder().encodeToString("abracadabra".getBytes());
	
	private static final String PREFIX = "Bearer";
	
	private static final String EMPTY = "";
	
	private static final String AUTHORIZATION = "Authorization";
	
	private static final long EXPERATION_TIME = 86400000;
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	public String createToken(String username, List<String> roles) {
		
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		
		Date now = new Date();
		
		Date validate = new Date(now.getTime() + EXPERATION_TIME);
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validate)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
		
		return token;
		
	}
	
	public Authentication getAuthentication(HttpServletRequest req) {
		
		String token = resolveToken(req);
		
		if(token != null && validateToken(token)) {
			
			String username = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
			
			if(username != null) {
				
				UserDetails userDeatails = userDetailService.loadUserByUsername(username);
				
				return new UsernamePasswordAuthenticationToken(
						userDeatails,
						null,
						userDeatails.getAuthorities());
				
			}
		}
		
		 return null;
		 
	}
	
	
	private boolean validateToken(String token) {

		Jws<Claims> claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token);
		
		if(claims.getBody().getExpiration().before(new Date())) {
			
			return false;
			
		}
		
		return true;
	}
	
	public String resolveToken(HttpServletRequest req) {
		
		String bearerToken = req.getHeader(AUTHORIZATION);
		
		if(bearerToken != null && bearerToken.startsWith(PREFIX)) {
			
			return bearerToken.replace(PREFIX, EMPTY).trim();
			
		}
		
		return null;
		
	}
	
	
	
}
