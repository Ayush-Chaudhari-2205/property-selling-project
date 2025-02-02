package com.propertyguru.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Use this instead of @EnableGlobalMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;
	private final CustomAuthenticationEntryPoint authEntry;

	public SecurityConfig(JwtAuthenticationFilter jwtFilter, CustomAuthenticationEntryPoint authEntry) {
		this.jwtFilter = jwtFilter;
		this.authEntry = authEntry;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(authEntry)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/products/view", "/user/signup", "/users/signin",
								"/v*/api-doc*/**", "/swagger-ui/**").permitAll()
						.requestMatchers(HttpMethod.OPTIONS).permitAll()
						.requestMatchers("/products/purchase").hasRole("CUSTOMER")
						.requestMatchers("/products/add").hasRole("ADMIN")
						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Ensure password encryption
	}
}
