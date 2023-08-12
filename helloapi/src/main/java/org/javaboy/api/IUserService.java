package org.javaboy.api;

import org.javaboy.commons.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

public interface IUserService {

    @GetMapping("/hello")
    String hello();

    @GetMapping("/rest/controller/helloGet")
    String helloGet(@RequestParam("name") String name);

    @PostMapping("/rest/controller/helloPost2")
    User helloPost2(@RequestBody User user);


    @GetMapping("/rest/controller/user3")
    String getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException;

}
