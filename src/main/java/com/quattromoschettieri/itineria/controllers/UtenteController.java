package com.quattromoschettieri.itineria.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.utenteDTO.UtenteDTO;
import com.quattromoschettieri.itineria.converters.utenteConverter.UtenteConverter;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/utente")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final UtenteConverter utenteConverter;

    // Mostra la pagina principale dell'area personale
    @GetMapping
    public String areaPersonale(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        Utente utente = utenteService.findByEmail(email);

        UtenteDTO dto = utenteConverter.toDto(utente);

        model.addAttribute("utente", dto);

        return "utente/area-personale";
    }

    // Mostra il profilo dell'utente autenticato
    @GetMapping("/profilo")
    public String profilo(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        Utente utente = utenteService.findByEmail(email);

        UtenteDTO dto = utenteConverter.toDto(utente);

        model.addAttribute("utente", dto);

        return "utente/profilo";
    }

    // Modifica i dati del profilo dell'utente autenticato
    @PostMapping("/profilo")
    public String modificaProfilo(
            Authentication authentication,
            UtenteDTO dto) {

        String email = authentication.getName();

        Utente utente = utenteService.findByEmail(email);

        utenteService.update(utente.getId(), dto);

        return "redirect:/utente/profilo";
    }

    // Mostra il form per cambiare la password
    @GetMapping("/password")
    public String password() {
        return "utente/password";
    }

    // Cambia la password dell'utente autenticato
    @PostMapping("/password")
    public String cambiaPassword(
            Authentication authentication,
            String vecchiaPassword,
            String nuovaPassword) {

        String email = authentication.getName();

        Utente utente = utenteService.findByEmail(email);

        utenteService.updatePassword(
                utente.getId(),
                vecchiaPassword,
                nuovaPassword
        );

        return "redirect:/utente/profilo";
    }

    // Elimina definitivamente l'account dell'utente autenticato
    @PostMapping("/elimina")
    public String eliminaAccount(Authentication authentication) {

        String email = authentication.getName();

        Utente utente = utenteService.findByEmail(email);

        utenteService.delete(utente.getId());

        return "redirect:/";
    }
}