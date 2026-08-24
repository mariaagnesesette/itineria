package com.quattromoschettieri.itineria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class HomeController { 
    @GetMapping("/")
    public String index() { 
        return "index";
    }}
     // Spring Boot cerca templates/index.html } }

