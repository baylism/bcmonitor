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


/**
 *
 * @Bean
 * 	    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
 * 	        http
 * 	            // ...
 * 	            .authorizeExchange()
 * 	                // any URL that starts with /admin/ requires the role "ROLE_ADMIN"
 * 	                .pathMatchers("/admin/**").hasRole("ADMIN")
 * 	                // a POST to /users requires the role "USER_POST"
 * 	                .pathMatchers(HttpMethod.POST, "/users").hasAuthority("USER_POST")
 * 	                // a request to /users/{username} requires the current authentication's username
 * 	                // to be equal to the {username}
 * 	                .pathMatchers("/users/{username}").access((authentication, context) ->
 * 	                    authentication
 * 	                        .map(Authentication::getName)
 * 	                        .map(username -> username.equals(context.getVariables().get("username")))
 * 	                        .map(AuthorizationDecision::new)
 * 	                )
 * 	                // allows providing a custom matching strategy that requires the role "ROLE_CUSTOM"
 * 	                .matchers(customMatcher).hasRole("CUSTOM")
 * 	                // any other request requires the user to be authenticated
 * 	                .anyExchange().authenticated();
 * 	        return http.build();
 * 	    }
 */
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
                .withUsername("user")
                .password(passwordEncoder().encode("pw"))
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