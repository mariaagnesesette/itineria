package com.quattromoschettieri.itineria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class HomeController { 
    @GetMapping("/")
    public String index() { 
        return "index";
    }

    @GetMapping("/supporto")
public String supporto() {
    return "footer/supporto";
}

@GetMapping("/faq")
public String faq() {
    return "footer/faq";
}

@GetMapping("/contattaci")
public String contattaci() {
    return "footer/contattaci";
}

}
     // Spring Boot cerca templates/index.html } }

