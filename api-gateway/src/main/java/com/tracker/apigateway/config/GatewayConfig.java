package com.tracker.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GatewayConfig {
    private static final String FALLBACK_URL = "forward:/fallbackRoute";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user_service",
                        r -> r.path("/api/v1/users/**")
                                .filters(
                                        f ->
                                                f.circuitBreaker(c ->
                                                        c.setName("userServiceCB")
                                                                .setFallbackUri(FALLBACK_URL))
                                ).uri("lb://userService")
                )
                .route("habit_tracker_service",
                        r -> r.path("/api/v1/habits/**")
                                .filters(
                                        f ->
                                                f.circuitBreaker(c ->
                                                        c.setName("habitTrackerServiceCB")
                                                                .setFallbackUri(FALLBACK_URL))
                                ).uri("lb://habitTrackerService")
                )
                .route("ai_recommendation_service",
                        r -> r.path("/api/v1/ai/**")
                                .filters(
                                        f ->
                                                f.circuitBreaker(c ->
                                                        c.setName("aiRecommendationServiceCB")
                                                                .setFallbackUri(FALLBACK_URL))
                                ).uri("lb://ai-recommendation-service")
                )
                .route("analytics_service",
                        r -> r.path("/api/v1/analytics/**")
                                .filters(
                                        f ->
                                                f.circuitBreaker(c ->
                                                        c.setName("analyticsServiceCB")
                                                                .setFallbackUri(FALLBACK_URL))
                                ).uri("lb://analyticsService")
                )
                .route("notification_service",
                        r -> r.path("/api/v1/notify/**")
                                .filters(
                                        f ->
                                                f.circuitBreaker(c ->
                                                        c.setName("notificationServiceCB")
                                                                .setFallbackUri(FALLBACK_URL))
                                ).uri("lb://notificationService")
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackHandle() {
        return RouterFunctions
                .route()
                .GET("/fallback", request
                        -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .bodyValue(FALLBACK_URL))
                .build();

    }
}
