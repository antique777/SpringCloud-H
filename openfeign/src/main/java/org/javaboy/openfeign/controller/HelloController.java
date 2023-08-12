package org.javaboy.openfeign.controller;

import org.javaboy.commons.User;
import org.javaboy.openfeign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() throws UnsupportedEncodingException {

        String s = helloService.helloGet("張三");
        System.out.println(s);

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setId(1);
        User user1 = helloService.helloPost2(user);
        System.out.println(user1.toString());

        helloService.getUserByName(URLEncoder.encode("李四","UTF-8"));

        return helloService.hello();
    }

}
