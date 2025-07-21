package com.tracker.habitservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "habit-service",
                version = "v1",
                description = "tracker for habits of users"
        )
)
public class SwaggerConfig {
}