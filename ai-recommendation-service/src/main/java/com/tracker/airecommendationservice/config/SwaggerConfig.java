package com.tracker.airecommendationservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ai-recommendation-service",
                version = "v1",
                description = "service with AI that gives recommendations to users"
        )
)
public class SwaggerConfig {
}

