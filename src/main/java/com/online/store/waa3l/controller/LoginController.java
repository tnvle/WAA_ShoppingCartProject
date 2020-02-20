package com.online.store.waa3l.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

   @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        String errorMessge = null;
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login";
    }


    @GetMapping("/denied")
    public String accessDenied(){
        return "accessDenied";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
