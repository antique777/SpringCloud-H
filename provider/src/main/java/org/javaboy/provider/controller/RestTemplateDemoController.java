package org.javaboy.provider.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.javaboy.api.IUserService;
import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

@RestController
public class RestTemplateDemoController implements IUserService {

    @Value("${server.port}")
    Integer port;

    /*@GetMapping("/rest/controller/helloGet")
    public String hello(String name) {
        System.out.println("helloGet:" + port + ":" + name);
        return "helloGet provider1 " + port.toString() + name;
    }*/

    @PostMapping("/rest/controller/helloPost")
    public User helloPost( User user) {
        System.out.println("helloPost:" + port + user.toString());
        return user;
    }



//    @PostMapping("/rest/controller/helloPost2")
    @Override
    public User helloPost2(@RequestBody User user) {
        System.out.println("helloPost2:" + port + user.toString());
        return user;
    }

    @Override
    @RateLimiter(name = "rlA")
    public String hello() {
        System.out.println("hello:" + port);
        System.out.println(new Date());
//        int i = 1/0;
        return "Hello provider1 : " + port.toString() + " : " ;
    }

    @Override
    public String helloGet(String name) {
        System.out.println("helloGet:" + port + ":" + name);
        return "helloGet provider1 " + port.toString() + name;
    }

    @Override
    public String getUserByName(@RequestHeader String name) throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(name, "UTF-8"));
        return name;
    }
}
