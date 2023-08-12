package org.javaboy.provider.controller;

import org.javaboy.commons.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @PostMapping("/register")
    public String register(User user) {
        System.out.println(user.toString());
//        return "redirect:/register/loginPage?username=" + user.getUsername();
        return "redirect:http://provider/register/loginPage?username=" + user.getPassword();
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username) {
        System.out.println(username);
        return "loginPage" + username;
    }

}
