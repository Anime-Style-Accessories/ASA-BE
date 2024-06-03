package com.miki.animestylebackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static com.miki.animestylebackend.model.Permission.*;
import static com.miki.animestylebackend.model.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/api/v1/product/**",
            "/api/v1/category/**",
            "/api/v1/payment/**",
            "/api/v1/order_items/**",
            "/api/v1/vouchers/**",
            "/api/v1/orders/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
//                                .requestMatchers("/api/v1/users/**").hasAnyRole(CUSTOMER.name(), STAFF.name(), ADMIN.name())
                                .requestMatchers(GET, "/api/v1/users/me").hasAnyRole(CUSTOMER.name(), STAFF.name(), ADMIN.name())
                                .requestMatchers(GET, "/api/v1/users/getUserByUsername").hasAnyRole(STAFF.name(), ADMIN.name())
                                .requestMatchers(GET, "/api/v1/users/getAllUsers").hasAnyRole(STAFF.name(), ADMIN.name())
                                .requestMatchers(PATCH, "/api/v1/users/**").hasAnyRole(CUSTOMER.name(), STAFF.name(), ADMIN.name())
                                .requestMatchers(POST, "/api/v1/users").hasAnyRole(CUSTOMER.name(), STAFF.name(), ADMIN.name())



                                .requestMatchers("/api/v1/staff/**").hasAnyRole(STAFF.name())
                                .requestMatchers(GET, "/api/v1/staff/**").hasAnyAuthority(STAFF_READ.name())
                                .requestMatchers(POST, "/api/v1/staff/**").hasAnyAuthority(STAFF_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/staff/**").hasAnyAuthority(STAFF_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/staff/**").hasAnyAuthority(STAFF_DELETE.name())

                                .requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name())

                                .requestMatchers("/api/v1/customer/**").hasAnyRole(CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/customer/**").hasAnyAuthority(CUSTOMER_READ.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
