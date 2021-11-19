package br.senai.sp.odonto.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean{
	
	private JwtAuthenticationService authService;
	
	public JwtAuthenticationFilter(JwtAuthenticationService authService) {
		
		this.authService = authService;
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	
			throws IOException, ServletException {
		
				Authentication auth = authService
						.getAuthentication((HttpServletRequest) request);
				
				SecurityContextHolder
					.getContext()
					.setAuthentication(auth);
				
				chain.doFilter(request, response);
				
	}
	
}
