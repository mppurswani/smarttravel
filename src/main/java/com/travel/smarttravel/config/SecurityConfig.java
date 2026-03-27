package com.travel.smarttravel.config;

import com.travel.smarttravel.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ===== PUBLIC FRONTEND FILES =====
                .antMatchers(
                    "/",
                    "/index.html",
                    "/style.css",
                    "/app.js",
                    "/favicon.ico"
                ).permitAll()

                // ===== PUBLIC AUTH ENDPOINTS =====
                .antMatchers("/api/auth/**").permitAll()

                // ===== PUBLIC HEALTH ENDPOINT =====
                .antMatchers("/api/health").permitAll()

                // ===== SWAGGER / OPENAPI =====
                .antMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/v3/api-docs",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()

                // ===== CITY APIs =====
                .antMatchers(HttpMethod.GET, "/api/cities/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/cities/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/cities/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/cities/**").hasRole("ADMIN")

                // ===== FAVOURITES =====
                .antMatchers(HttpMethod.GET, "/api/favourites/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/favourites/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/favourites/**").hasAnyRole("USER", "ADMIN")

                // ===== EVERYTHING ELSE =====
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}