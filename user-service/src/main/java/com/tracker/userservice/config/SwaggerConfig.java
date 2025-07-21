package com.tracker.userservice.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "user-service",
                version = "v1",
                description = "Responsible for authorization and registration of users. For issuing session access tokens."
        )
)
public class SwaggerConfig {
}

