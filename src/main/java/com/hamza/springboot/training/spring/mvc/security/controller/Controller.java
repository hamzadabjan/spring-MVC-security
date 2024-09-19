package com.hamza.springboot.training.spring.mvc.security.controller;


import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("/")
    public String showHome(){
        return "home";
    }

    @GetMapping("/leaders")
    public String showleaders(){
        return "leaders";
    }

    @GetMapping("/system")
    public String showsystem(){
        return "system";
    }


}
