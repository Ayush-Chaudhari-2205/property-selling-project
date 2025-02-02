package com.propertyguru.security;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils utils;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();

		// ✅ Skip JWT filtering for signup and login endpoints
		return path.equals("/user/signup") || path.equals("/user/login");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");


		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			try {
				Claims claims = utils.validateJwtToken(jwt);
				String email = utils.getUserNameFromClaims(claims);
				List<GrantedAuthority> authorities = utils.getAuthoritiesFromClaims(claims);

				UsernamePasswordAuthenticationToken token =
						new UsernamePasswordAuthenticationToken(email, null, authorities);

				SecurityContextHolder.getContext().setAuthentication(token);
				System.out.println("Authenticated user: {}"+ email);
			} catch (Exception e) {
				System.out.println("JWT authentication failed: {}"+ e.getMessage());
			}
		}
		filterChain.doFilter(request, response);
	}
}
