package com.tracker.analiticsservice.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "analytics-service",
                version = "v1",
                description = "service for analyzing user habits"
        )
)
public class SwaggerConfig {
}
