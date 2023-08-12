package org.javaboy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    /**
     * 有这个bean就可以实现请求转发
     *
     * @param routeLocatorBuilder -
     * @return org.springframework.cloud.gateway.route.RouteLocator
     * @author xab
     * @date 2023/8/12 15:09
     */
    /*@Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("javaboy_route", r -> r.path("/get").uri("http://httpbin.org"))
                .build();
    }*/
}
