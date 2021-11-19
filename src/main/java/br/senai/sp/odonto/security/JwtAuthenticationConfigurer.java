package br.senai.sp.odonto.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationConfigurer
	extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

	private JwtAuthenticationService authService;
	
	public JwtAuthenticationConfigurer(JwtAuthenticationService authService) {
		
		this.authService = authService;
		
	}
	
	@Override
	public void configure(HttpSecurity builder) throws Exception {
		
		JwtAuthenticationFilter authFilter = 
				new JwtAuthenticationFilter(authService);
		
		builder.addFilterBefore(authFilter, 
				UsernamePasswordAuthenticationFilter.class);
		
	}
}
