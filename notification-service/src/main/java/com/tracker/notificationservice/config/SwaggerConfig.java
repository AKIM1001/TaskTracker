package com.tracker.notificationservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "notifications-service",
                version = "v1",
                description = "service for sending notifications to users"
        )
)
public class SwaggerConfig {
}
