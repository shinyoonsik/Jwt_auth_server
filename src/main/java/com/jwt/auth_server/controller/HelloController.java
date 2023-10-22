package com.jwt.auth_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/index")
    public String hello(){
        return "<h1>hello</h1>";
    }

    @GetMapping("/user")
    public String user(){
        return "<h1>USER</h1>";
    }

    @GetMapping("/admin")
    public String admin(){
        return "<h1>ADMIN</h1>";
    }

    @GetMapping("/super-admin")
    public String superAdmin(){
        return "<h1>SUPER ADMIN</h1>";
    }

}
