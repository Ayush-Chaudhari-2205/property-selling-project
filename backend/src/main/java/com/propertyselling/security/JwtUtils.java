package com.propertyselling.security;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.propertyselling.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

	@Value("${SECRET_KEY}")
	private String jwtSecret;

	@Value("${EXP_TIMEOUT}")
	private int jwtExpirationMs;
	
	
	private Key key;

	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	// will be invoked by User controller : signin ) , upon successful
	// authentication
	public String generateJwtToken(Authentication authentication) {
		log.info("generate jwt token " + authentication);
		CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))

				.claim("authorities", "ROLE_" + userPrincipal.getUser().getUserType().toString())

				.claim("user_id", userPrincipal.getUser().getId())
				.claim("email", userPrincipal.getUser().getEmail())
				.claim("fullName", userPrincipal.getUser().getFullName())
				.claim("mobileNo", userPrincipal.getUser().getMobileNumber())
				.claim("role", userPrincipal.getUser().getUserType().toString())
				.signWith(key, SignatureAlgorithm.HS512)


				.compact();
	}

	// this method will be invoked by our custom JWT filter
	public String getUserNameFromJwtToken(Claims claims) {
		return claims.getSubject();
	}


	public Claims validateJwtToken(String jwtToken) {

		Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwtToken)
						.getBody();

		System.out.println("User ID: " + claims.get("user_id", Long.class));
		System.out.println("Email: " + claims.get("email", String.class));
		System.out.println("Full Name: " + claims.get("fullName", String.class));
		System.out.println("Mobile No: " + claims.get("mobileNo", String.class));
		System.out.println("Role: " + claims.get("role", String.class));


		return claims;		
	}


	private String getAuthoritiesInString(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
	}

	public List<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {
		String authString = claims.get("authorities", String.class);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authString);
	}

	public Long getUserIdFromJwtToken(Claims claims) {
		return claims.get("user_id", Long.class);
	}

	public Authentication populateAuthenticationTokenFromJWT(String jwt) {
		Claims claims = validateJwtToken(jwt);
		String email = getUserNameFromJwtToken(claims);
		List<GrantedAuthority> authorities = getAuthoritiesFromClaims(claims);
		Long userId = getUserIdFromJwtToken(claims);

		return new UsernamePasswordAuthenticationToken(email, userId, authorities);
	}

}
