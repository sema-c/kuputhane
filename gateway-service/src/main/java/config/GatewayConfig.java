package config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-service", r -> r.path("/api/auth/**", "/api/users/**", "/api/contact/**")
                        .uri("http://localhost:8081"))

                .route("book-service", r -> r.path("/api/books/**", "/api/categories/**", "/api/publishers/**")
                        .uri("http://localhost:8082"))

                .route("payment-service", r -> r.path("/api/borrow/**", "/api/punishment/**")
                        .uri("http://localhost:8083"))

                .route("permission-service", r -> r.path("/api/permissions/**", "/api/roles/**")
                        .uri("http://localhost:8084"))

                .build();
    }
}
