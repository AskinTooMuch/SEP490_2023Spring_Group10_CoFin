/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/01/2023    1.0        ChucNV           First Deploy<br>
 */

package com.example.eims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    /**
     * Encrypt the password
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Security 5.7 and above filter chain
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
                .authorizeHttpRequests((auth) ->
//                        auth.anyRequest().authenticated() // Authenticate all Requests
                                auth.requestMatchers(HttpMethod.GET, "/api/**").permitAll() //Allow anyone can access login API
                                        .requestMatchers("/api/auth/**").permitAll()
                                        .requestMatchers("/api/user/**").permitAll()
                                        .requestMatchers("/api/specie/**").permitAll()
                                        .requestMatchers("/api/supplier/**").permitAll()
                                        .requestMatchers("/api/customer/**").permitAll()
                                        .requestMatchers("/api/facility/**").permitAll()
                                        .requestMatchers("/api/machine/**").permitAll()
                                        .anyRequest().authenticated()
                );
        return http.build();
    }
}

