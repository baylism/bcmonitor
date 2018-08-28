package com.bcam.bcmonitor.api.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {

        return http
                .authorizeExchange()
                    .pathMatchers("/api/**/raw/**", "/api/**/method/**").hasRole("USER")
                    .pathMatchers("/admin/**").hasRole("USER")
                    .pathMatchers("/api/**").permitAll()
                .anyExchange().authenticated()
                .and().httpBasic()
                .and().build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("max")
                .password(passwordEncoder().encode("01cecfb951713cc9ac820f8c2e0b695b"))
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("password")
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}