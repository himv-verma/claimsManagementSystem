package com.microservices.demo.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.demo.jwt.entity.CustomUserDetails;
import com.microservices.demo.jwt.entity.User;
import com.microservices.demo.jwt.service.UserDetailsServiceImpl;
import com.microservices.demo.jwt.service.UserService;
import com.microservices.demo.jwt.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public String authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
		return jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
	}

	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		userService.registerUser(user);
		return "User registered successfully";
	}

	@GetMapping("/validateToken")
	public ResponseEntity<?> validateToken(@RequestParam String token) {
		if (jwtUtil.validateToken(token, jwtUtil.extractUsername(token))) {
			String username = jwtUtil.extractUsername(token);
			List<String> roles = jwtUtil.extractRoles(token);
			return ResponseEntity.ok().body(new UserDetailResponse(username, roles));
		} else {
			return ResponseEntity.status(401).body("Invalid token");
		}
	}

	public static class UserDetailResponse {
		private String username;
		private List<String> roles;

		public UserDetailResponse(String username, List<String> roles) {
			super();
			this.username = username;
			this.roles = roles;
		}
		public List<String> getRoles() {
			return roles;
		}
		public void setRoles(List<String> roles) {
			this.roles = roles;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	}

	public static class LoginRequest {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
