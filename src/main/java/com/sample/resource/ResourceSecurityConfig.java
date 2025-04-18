package com.sample.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class ResourceSecurityConfig {

    private final PermissionManager permissionManager;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    @Order(2)
    public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest()
                        .access(permissionManager)
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(
                                jwt -> {
                                    jwt.jwkSetUri("http://localhost:9000/oauth2/jwks");
                                }
                        )
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );

        return http.build();
    }

}

