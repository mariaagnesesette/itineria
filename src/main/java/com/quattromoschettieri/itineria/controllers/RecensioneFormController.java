package com.quattromoschettieri.itineria.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quattromoschettieri.itineria.DTO.RecensioneDTO;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.RecensioneService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/recensioni")
@RequiredArgsConstructor
public class RecensioneFormController {

    private final RecensioneService recensioneService;
    private final UtenteService utenteService;

    @PostMapping
    public String save(
            @ModelAttribute RecensioneDTO dto,
            @RequestParam("redirectUrl") String redirectUrl,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        recensioneService.save(dto, utente);

        return "redirect:" + safeRedirect(redirectUrl);
    }

    private String safeRedirect(String redirectUrl) {
        if (redirectUrl == null || !redirectUrl.startsWith("/") || redirectUrl.startsWith("//")) {
            return "/";
        }
        return redirectUrl;
    }
}
