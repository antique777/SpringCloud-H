package org.javaboy.provider.controller;

import org.javaboy.api.IUserService;
import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {

    @Value("${server.port}")
    Integer port;

//    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello:" + port);
        return "Hello provider1 : " + port.toString() + " : " ;
    }

    @GetMapping("/hello2")
    public String hello2() {
        System.out.println("hello2:" + port);
        return "Hello provider1" + port.toString();
    }

}
