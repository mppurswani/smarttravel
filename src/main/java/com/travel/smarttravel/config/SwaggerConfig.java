package com.travel.smarttravel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("SmartTravel API")
                .version("2.0")
                .description(
                    "Smart Travel Recommendation System — " +
                    "Explore 100+ Indian cities, hidden gems, " +
                    "categories and manage your favourites.")
                .contact(new Contact()
                    .name("Mayank Purswani")
                    .email("mayankhero2004@gmail.com")))
            // ── JWT auth button in Swagger UI ──
            .addSecurityItem(
                new SecurityRequirement()
                    .addList("Bearer Authentication"))
            .components(new Components()
                .addSecuritySchemes(
                    "Bearer Authentication",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description(
                            "Enter your JWT token here. " +
                            "Get it from /api/auth/login")));
    }
}