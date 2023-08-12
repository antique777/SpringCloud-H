package org.javaboy.reilience4j2.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@Retry(name = "retry-A")
//@CircuitBreaker(name = "cbA", fallbackMethod = "error1")
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

//    @Retry(name = "retry-A")
    /*public String hello() {
        return restTemplate.getForObject("http://provider/hello", String.class);
    }*/
    public String hello() {
        for (int i = 0; i < 5; i++) {
            restTemplate.getForObject("http://provider/hello", String.class);
        }
        return "success";
    }
    public String error1(Throwable throwable) {
        System.out.println("error1 is use");
        return "error 1 ";
    }

}
