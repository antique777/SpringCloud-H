package org.javaboy.reilience4j2.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateBean {

    @Bean
    RestTemplate restTemplateOne() {
        System.out.println("hello  rest template");
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        System.out.println("hello  rest template  with load balancer");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
