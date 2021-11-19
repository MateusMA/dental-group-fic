package br.senai.sp.odonto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import br.senai.sp.odonto.security.UserDetailServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationService authService;

	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic().disable()
		.csrf().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/odonto/auth/login").permitAll()
		.antMatchers(HttpMethod.GET, "/odonto/dentistas/**").hasAnyRole("USER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/odonto/dentistas/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/odonto/dentistas/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/odonto/dentistas/**").hasRole("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.apply(new JwtAuthenticationConfigurer(authService));
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
		
	}
	
}
