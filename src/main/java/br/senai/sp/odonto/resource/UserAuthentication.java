package br.senai.sp.odonto.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.odonto.dto.UserAccountCredential;
import br.senai.sp.odonto.model.User;
import br.senai.sp.odonto.repository.UserRepository;
import br.senai.sp.odonto.security.JwtAuthenticationService;


@RestController
@RequestMapping("/odonto")
public class UserAuthentication {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtAuthenticationService jwtService;
	
	@Autowired(required = true)
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/login")
	public ResponseEntity<Map<Object, Object>> signIn(@RequestBody UserAccountCredential credential) {
		
		System.out.println("Autenticando.........");
		
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
				credential.getUsername(),
				credential.getPassword());
		
		authenticationManager.authenticate(user);
		
		List<String> role = new ArrayList<String>();
		
		User userLogin = new User();
		
		userLogin = userRepository.findByUsername(credential.getUsername());
		
		role.add(userLogin.getRole());
		
		String token = jwtService.createToken(credential.getUsername(), role);
		
		Map<Object, Object> jsonResponse = new HashMap<>();
		
		jsonResponse.put("username", credential.getUsername());
		
		jsonResponse.put("token", token);
		
		return ResponseEntity.ok(jsonResponse);
				
	}
	
}
