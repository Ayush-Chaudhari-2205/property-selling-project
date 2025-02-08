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


//	@Bean
//	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception {
//		http
//				.csrf(csrf -> csrf.disable())
//				.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntry))
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.authorizeHttpRequests(auth -> auth
//						.requestMatchers("/","/user/signup","/user/signin", "/v*/api-doc*/**", "/swagger-ui/**").permitAll()
//						.requestMatchers(HttpMethod.OPTIONS).permitAll()
//						.requestMatchers("/property/add").hasAuthority("ROLE_SELLER")
//						.requestMatchers("/products/purchase").hasRole("CUSTOMER")
//						.requestMatchers("/products/add").hasRole("ADMIN")
//						.anyRequest().authenticated()
//				)
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}


	@Bean
	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntry))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// ✅ Public Endpoints (Accessible Without Authentication)
						.requestMatchers("/", "/user/signup", "/user/signin", "/v*/api-doc*/**", "/swagger-ui/**").permitAll()
						.requestMatchers("/property/all", "/property/{id}", "/review/property/{id}").permitAll()

						// ✅ Buyer Access
						.requestMatchers("/wishlist/**", "/inquiry/submit", "/review/add").hasAuthority("ROLE_BUYER")

						// ✅ Seller Access
						.requestMatchers("/property/add", "/property/update/**", "/property/delete/**").hasAuthority("ROLE_SELLER")
						.requestMatchers("/property/image/upload/**", "/property/image/delete/**").hasAuthority("ROLE_SELLER")
						.requestMatchers("/inquiry/property/**", "/inquiry/respond").hasAuthority("ROLE_SELLER")

						// ✅ Admin Access
						.requestMatchers("/admin/**", "/property/status", "/property/delete/**").hasAuthority("ROLE_ADMIN")
						.requestMatchers("/user/active-users", "/user/non-admin-users").hasAuthority("ROLE_ADMIN")

						// ✅ Secure Image Upload & Retrieval
						.requestMatchers("/property/image/{propertyId}").authenticated()
						.requestMatchers("/property/image/delete/{imageId}/seller/{sellerId}").hasAuthority("ROLE_SELLER")

						// ✅ Inquiry Management
						.requestMatchers("/inquiry/seller/{sellerId}").hasAuthority("ROLE_SELLER")

						// ✅ Property Management (Filtering & Searching)
						.requestMatchers("/property/filter", "/property/search").authenticated()

						// ✅ Secure All Other Requests
						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


	//configure AuthMgr as a spring bean
	@Bean
	public AuthenticationManager authenticationManager
	(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
}
