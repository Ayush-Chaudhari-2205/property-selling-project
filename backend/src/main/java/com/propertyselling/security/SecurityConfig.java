package com.propertyselling.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity//to enable spring sec frmwork support
@Configuration //to tell SC , this is config class containing @Bean methods
@EnableGlobalMethodSecurity(prePostEnabled = true)
//To enable method level authorization support : pre n post authorization
public class SecurityConfig {
	//dep : pwd encoder
	@Autowired
	private PasswordEncoder enc;
	//dep : custom jwt auth filter
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	//dep : custom auth entry point
	@Autowired
	private CustomAuthenticationEntryPoint authEntry;


	@Bean
	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntry))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/","/user/signup","/user/signin", "/v*/api-doc*/**", "/swagger-ui/**").permitAll()
						.requestMatchers(HttpMethod.OPTIONS).permitAll()
						.requestMatchers("/property/add").hasAuthority("ROLE_SELLER")
						.requestMatchers("/products/purchase").hasRole("CUSTOMER")
						.requestMatchers("/products/add").hasRole("ADMIN")
						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


	// Improved for access control
//	@Bean
//	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception {
//		http
//				.csrf(csrf -> csrf.disable())
//				.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntry))
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.authorizeHttpRequests(auth -> auth
//						.requestMatchers("/user/signup", "/user/signin", "/property/all", "/property/{id}", "/review/property/{id}"
//													,"/v*/api-doc*/**", "/swagger-ui/**").permitAll()
//
//						//  Buyer Access
//						.requestMatchers("/wishlist/**", "/inquiry/submit", "/review/add").hasAuthority("BUYER")
//
//						//  Seller Access
//						.requestMatchers("/property/add", "/property/update/**", "/property/delete/**",
//								"/property/image/upload/**", "/property/image/delete/**",
//								"/inquiry/property/**", "/inquiry/respond").hasAuthority("SELLER")
//
//						//  Admin Access
//						.requestMatchers("/admin/**", "/property/status", "/property/delete/**").hasAuthority("ADMIN")
//
//						//  All other endpoints require authentication
//						.anyRequest().authenticated()
//				)
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}

	//configure AuthMgr as a spring bean
	@Bean
	public AuthenticationManager authenticationManager
	(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
}
