package org.javaboy.openfeign.service;

import org.javaboy.api.IUserService;
import org.javaboy.commons.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("provider")
public interface HelloService extends IUserService {

    /**
     * 這裡的方法名隨便取，
     * String  是provider的返回類型
     * getMapping是provider的接口地址  對應restTemplate.getFor
     *
     * @return java.lang.String
     * @author xab
     * @date 2023/8/9 18:16
     */
    /*@GetMapping("/hello")
    String hello();

    @GetMapping("/rest/controller/helloGet")
    String helloGet(@RequestParam("name") String name);

    @PostMapping("/rest/controller/helloPost2")
    User helloPost2(@RequestBody User user);


    @GetMapping("/rest/controller/user3")
    String getUserByName(@RequestHeader("name") String name);*/
}
