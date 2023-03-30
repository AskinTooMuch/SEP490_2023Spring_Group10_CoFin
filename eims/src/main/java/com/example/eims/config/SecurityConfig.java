/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/01/2023    1.0        ChucNV           First Deploy<br>
 * 23/03/2023    2.0        ChucNV           Re-config security<br>
 */

package com.example.eims.config;

import com.example.eims.config.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig{

    private UserDetailsService userDetailsService;
    @Autowired
    AuthenticationProvider authProvider;
    @Autowired
    SecurityFilter securityFilter;
    @Value("${spring.websecurity.debug:false}")
    boolean webSecurityDebug;
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
        /**
         * Old security filter chain : permit all requests
         */
//        http.cors().and().csrf().disable()
//                .authorizeHttpRequests((auth) ->
////                        auth.anyRequest().authenticated() // Authenticate all Requests
//                                auth.requestMatchers(HttpMethod.GET, "/api/auth/signin").permitAll() //Allow anyone can access login API
//                                        .requestMatchers("/api/auth/**").permitAll()
//                                        .requestMatchers("/api/user/**").permitAll()
//                                        .requestMatchers("/api/specie/**").permitAll()
//                                        .requestMatchers("/api/supplier/**").permitAll()
//                                        .requestMatchers("/api/customer/**").permitAll()
//                                        .requestMatchers("/api/facility/**").permitAll()
//                                        .requestMatchers("/api/machine/**").permitAll()
//                                        .requestMatchers("/api/registration/**").permitAll()
//                                        .requestMatchers("/api/breed/**").permitAll()
//                                        .requestMatchers("/api/employee/**").permitAll()
//                                        .requestMatchers("/api/import/**").permitAll()
//                                        .requestMatchers("/api/eggBatch/**").permitAll()
//                );


        /**
         * New security filter chain
         */
        http.cors().configurationSource(request ->{
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:3000"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE","PATCH","OPTIONS"));
            configuration.addExposedHeader("Message");
            configuration.setAllowedHeaders(List.of("Authorization","Cache-Control","Content-Type", "access-control-allow-origin"));
            return configuration;
        }).and().csrf().disable();

        final String[] NO_ROLE = {
                "/api/auth/**",
                "/api/user/update/**"
        };
        final String[] ALL_ROLE = {
                "/api/auth/changePassword"};
        final String[] OWNER_EMPLOYEE = {
                "/api/user/details",
                "/api/specie/list",
                "/api/specie/details",
                "/api/machine/all",
                "/api/machine/get",
                "/api/machine/allPaging",
                "/api/machine/create",
                "/api/machine/update/**",
                "/api/machine/delete",
                "/api/breed/detail/specieId",
                "/api/eggBatch/**"
        };
        final String[] MODERATOR_ADMIN = {
                "/api/user/all",
                "/api/user/allPaging",
                "/api/facility/all"
        };
        final String[] OWNER = {
                "/api/specie/new",
                "/api/specie/edit/**",
                "/api/specie/delete",
                "/api/supplier/**",
                "/api/customer/**",
                "/api/facility/update/**",
                "/api/breed/**",
                "/api/employee/**",
                "/api/import/**",
                "/api/eggBatch/**",
                "/api/notification/**",
                "/api/cost/**",
                "/api/payroll/**"
        };
        final String[] MODERATOR = {
                "/api/registration/**"
        };

        http
        .authorizeHttpRequests()
                .requestMatchers(NO_ROLE).permitAll()
                .and().authorizeHttpRequests().requestMatchers(ALL_ROLE)
//                        .hasAnyAuthority("ROLE_OWNER", "ROLE_EMPLOYEE", "ROLE_MODERATOR", "ROLE_ADMIN")
                        .hasAnyRole("OWNER", "EMPLOYEE", "MODERATOR", "ADMIN")
                .and().authorizeHttpRequests().requestMatchers(OWNER_EMPLOYEE)
                        .hasAnyRole("OWNER", "EMPLOYEE")
//                        .hasAnyAuthority("ROLE_OWNER", "ROLE_EMPLOYEE")
//                        .permitAll()
                .and().authorizeHttpRequests().requestMatchers(MODERATOR_ADMIN)
                        .hasAnyRole("MODERATOR", "ADMIN")
//                        .hasAnyAuthority("ROLE_MODERATOR", "ROLE_ADMIN")
                .and().authorizeHttpRequests().requestMatchers(OWNER)
                        .hasAnyRole("OWNER")
//                        .hasAnyAuthority("ROLE_OWNER")
//                        .permitAll()
                .and().authorizeHttpRequests().requestMatchers(MODERATOR)
                        .hasAnyRole("MODERATOR")
//                .hasAnyAuthority("ROLE_MODERATOR")
                .and().authorizeHttpRequests().anyRequest().authenticated()
                .and().authenticationProvider(authProvider)
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}

