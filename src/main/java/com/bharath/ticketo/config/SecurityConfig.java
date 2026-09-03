package com.bharath.ticketo.config;

import com.bharath.ticketo.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/register","/api/v1/auth/login", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()

                        // user + admin -> browse movies
                        .requestMatchers(HttpMethod.GET, "/api/v1/movie/**")
                        .hasAnyRole("USER", "ADMIN")

                        // view shows
                        .requestMatchers(HttpMethod.GET, "/api/v1/show/**")
                        .hasAnyRole("USER", "ADMIN")

                        // view seats
                        .requestMatchers(HttpMethod.GET, "/api/v1/seat/**")
                        .hasAnyRole("USER", "ADMIN")

                        // book tickets
                        .requestMatchers(HttpMethod.POST, "/api/v1/reservation")
                        .hasAnyRole("USER", "ADMIN")

                        // admin -> view all reservations
                        .requestMatchers(HttpMethod.GET, "/api/v1/reservation")
                        .hasRole("ADMIN")

                        // user -> view bookings
                        .requestMatchers(HttpMethod.GET, "/api/v1/reservation/**")
                        .hasRole("USER")

                        // user -> cancel bookings
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/reservation/**")
                        .hasRole("USER")

                        // upgrading privilege -> only admin
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/auth/**"
                        )
                        .hasRole("ADMIN")

                        // admin only -> manage movies
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/movie/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/movie/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/movie/**"
                        ).hasRole("ADMIN")

                        // Manage theaters -> admin only
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/theatre/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/theatre/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/theatre/**"
                        ).hasRole("ADMIN")

                        // Manage screens -> admin only
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/screen/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/screen/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/screen/**"
                        ).hasRole("ADMIN")


                        // Manage seats -> admin only
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/seat/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/seat/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/seat/**"
                        ).hasRole("ADMIN")

                        // Manage shows -> admin only
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/show/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/show/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/show/**"
                        ).hasRole("ADMIN")

                        .anyRequest()
                        .authenticated())

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
