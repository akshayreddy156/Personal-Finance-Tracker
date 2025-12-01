package com.pfm.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.pfm.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return new CustomUserDetailsService(userRepository);
	}

	// Custom Authentication Entry Point for JSON error responses
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (HttpServletRequest request, HttpServletResponse response,
				org.springframework.security.core.AuthenticationException authException) -> {

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			String json = """
					{
					    "status": 401,
					    "error": "Unauthorized",
					    "message": "Please login to access this resource"
					}
					""";

			response.getWriter().write(json);
		};
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (HttpServletRequest request, HttpServletResponse response,
				org.springframework.security.access.AccessDeniedException accessDeniedException) -> {

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);

			String json = """
					{
					    "status": 403,
					    "error": "Forbidden",
					    "message": "You don't have permission to access this resource"
					}
					""";

			response.getWriter().write(json);
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				// Add custom authentication entry point for JSON error responses
				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint())
						.accessDeniedHandler(accessDeniedHandler()))
				.httpBasic(Customizer.withDefaults());

		// if using H2 console in dev
		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:5173"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}