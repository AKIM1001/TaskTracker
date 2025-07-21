package com.tracker.primeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "prime-service",
                version = "v1",
                description = "service for purchasing prime"
        )
)
public class SwaggerConfig {
}
