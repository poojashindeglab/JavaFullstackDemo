package com.ecommerce.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.project.security.jwt.AuthTokenFilter;
import com.ecommerce.project.security.jwt.AuthenticationEntryPointJwt;
import com.ecommerce.project.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Bean
	 AuthTokenFilter authFilter() {
		return new AuthTokenFilter();
	}
	
	@Autowired
	private AuthenticationEntryPointJwt authEntrypoint;
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  
		http.csrf(csrf -> csrf.disable());

	    http.sessionManagement(session ->
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    );

	    http.authorizeHttpRequests(auth ->{
	    	auth.requestMatchers("/api/auth/**").permitAll();
	    	auth.requestMatchers("/api/public/**").permitAll();
	    auth.anyRequest().authenticated();});

	    http.exceptionHandling(exception ->
	            exception.authenticationEntryPoint(authEntrypoint)
	    );

	    http.authenticationProvider(authenticationProvider());

	    http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);

	    http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));
	    return http.build();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	 @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	

}
