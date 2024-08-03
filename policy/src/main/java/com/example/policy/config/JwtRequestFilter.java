package com.example.policy.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Value("${auth.service.url}")
	private String authServiceUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = null;
		String jwtToken = null;
		List<SimpleGrantedAuthority> authorities=new ArrayList<SimpleGrantedAuthority>();

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);

			String validationUrl = authServiceUrl + "/validateToken?token=" + jwtToken;
			try {
				ResponseEntity<String> validationResponse = restTemplate.exchange(validationUrl, HttpMethod.GET, null,
						String.class);
				String responseBody = validationResponse.getBody();

				if ("Token is invalid".equals(responseBody)) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
				username = extractUsernameFromResponseBody(responseBody);
				authorities = extractRolesFromResponseBody(responseBody);
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
							null, authorities);
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}

			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private String extractUsernameFromResponseBody(String responseBody) {
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
			return jsonNode.get("username").asText();
		} catch (IOException e) {
			return null;
		}
	}

	private List<SimpleGrantedAuthority> extractRolesFromResponseBody(String responseBody) {
		try {
			JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
			return StreamSupport.stream(jsonNode.get("roles").spliterator(), false)
					.map(roleNode -> new SimpleGrantedAuthority(roleNode.asText())).collect(Collectors.toList());
		} catch (IOException e) {
			return List.of();
		}
	}
}