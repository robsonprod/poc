package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    @Bean
    RouteLocator gateway (RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder
                .routes()
                .route( routeSpec -> routeSpec
                        .path("/task")
                        .uri("http://localhost:8080/api/training/tasks")
                )
                .route( routeSpec -> routeSpec
                        .path("/gate")
                        .uri("http://localhost:8080/api/training/gate")
                )

                .route( routeSpec -> routeSpec
                        .path("/hello")
                        .filters( gatewayFilterSpec ->
                                    gatewayFilterSpec.setPath("/guides")
                                )
                        .uri("http://spring.io/")
                )
                .route("/twitter", routeSpec -> routeSpec
                            .path("/twitter/**")
                            .filters(fs -> fs
                                    .rewritePath("/twitter/(?<handle>.*)","/${handle}"))
                            .uri("http://twitter.com/@")
                        )
                .build();
    }


}
