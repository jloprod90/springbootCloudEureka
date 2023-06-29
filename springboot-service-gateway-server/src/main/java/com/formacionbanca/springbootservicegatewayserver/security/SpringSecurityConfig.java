package com.formacionbanca.springbootservicegatewayserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/serviceOauth/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/serviceProducts/products",
                        "/api/serviceItems/items",
                        "/api/serviceUsers/users"
                        //,
                        //"/api/serviceItems/items/{id}/amount/{amount}",
                        //"/api/serviceProducts/products/{id}"
                        ).permitAll()
                .pathMatchers(HttpMethod.GET,"/api/serviceUsers/users/{id}").hasAnyRole("ADMIN","USER")
                .pathMatchers("/api/serviceProducts/**", "/api/serviceItems/**", "/api/serviceUsers/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }

}
