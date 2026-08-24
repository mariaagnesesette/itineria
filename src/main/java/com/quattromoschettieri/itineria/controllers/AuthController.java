package com.quattromoschettieri.itineria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.quattromoschettieri.itineria.DTO.utenteDTO.UtenteDTO;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UtenteService utenteService;

    @GetMapping("/accedi")
    public String accedi() {
        return "utente/accedi";
    }

    @GetMapping("/registrazione")
    public String registrazione() {
        return "utente/registrazione";
    }

    @PostMapping("/utenti")
    public String registrazione(UtenteDTO dto) {

        utenteService.save(dto);

        return "redirect:/accedi";
    }
}
