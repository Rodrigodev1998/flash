package com.company.flash.infra.security;

import com.company.flash.exception.CustomAccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.PUT,"v1/auth/update").hasRole("ADMIN") //qualquer coisa, trocar
                        .requestMatchers(HttpMethod.GET, "v1/profile/profile-all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "v1/profile/admin/{id}/details").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "v1/profile/delivery/{id}/details").hasRole("DELIVERY_PERSON")
                        .requestMatchers(HttpMethod.PUT, "v1/profile/shop/{id}/details").hasRole("SHOP")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"timestamp\":\"" + LocalDateTime.now() +
                    "\", \"status\":403, \"error\":\"Forbidden\", \"message\":\"You do not have permission to access.\", " +
                    "\"path\":\"" + request.getRequestURI() + "\"}");
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
