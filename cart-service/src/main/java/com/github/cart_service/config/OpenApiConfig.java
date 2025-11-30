package com.github.cart_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI cartOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Cart API")
            .description("Shopping cart service for goldenbean coffee")
            .version("1.0"));
    }
}
