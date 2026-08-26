package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.utenteDTO.UtenteDTO;
import com.quattromoschettieri.itineria.converters.utenteConverter.UtenteConverter;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.repository.eventoRepository.EventoRepository;
import com.quattromoschettieri.itineria.services.BibliotecaService;
import com.quattromoschettieri.itineria.services.LocaleService;
import com.quattromoschettieri.itineria.services.MuseoService;
import com.quattromoschettieri.itineria.services.RecensioneService;
import com.quattromoschettieri.itineria.services.RistoranteService;
import com.quattromoschettieri.itineria.services.ZonaVerdeService;
import com.quattromoschettieri.itineria.services.utenteService.PreferitiService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/utente")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final UtenteConverter utenteConverter;
    private final LuogoInteresseRepository luogoInteresseRepository;
    private final EventoRepository eventoRepository;
    private final PreferitiService preferitiService;
    private final RecensioneService recensioneService;

    private final BibliotecaService bibliotecaService;
    private final MuseoService museoService;
    private final RistoranteService ristoranteService;
    private final LocaleService localeService;
    private final ZonaVerdeService zonaVerdeService;

    // Mostra la pagina principale dell'area personale
    @GetMapping
    public String areaPersonale(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        Utente utente = utenteService.findByEmail(email);

        UtenteDTO dto = utenteConverter.toDto(utente);

        model.addAttribute("utente", dto);
        model.addAttribute("ruolo", utente.getRuolo().name());
        model.addAttribute("utenteId", utente.getId());
        model.addAttribute("luoghiPreferiti", preferitiService.findLuoghiPreferiti(utente.getId()));
        model.addAttribute("eventiPreferiti", preferitiService.findEventiPreferiti(utente.getId()));
        model.addAttribute("recensioni",
                recensioneService.findByUtenteId(utente.getId(), Pageable.unpaged()).getContent());

        if (utente.getRuolo() == Ruolo.ADMIN) {
            model.addAttribute("luoghiGestiti", luogoInteresseRepository.findAll());
            model.addAttribute("eventiGestiti", eventoRepository.findAll());
        } else if (utente.getRuolo() == Ruolo.MANAGER) {
            model.addAttribute("luoghiGestiti", luogoInteresseRepository.findByManagerId(utente.getId()));
            model.addAttribute("eventiGestiti", eventoRepository.findByLuogoInteresse_Manager_Id(utente.getId()));
        }

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/{utenteId}/manager/{tipoLuogo}/{luogoId}")
    public String rendiManager(
        @PathVariable Long utenteId,
        @PathVariable String tipoLuogo,
        @PathVariable Long luogoId) {

        Utente utente = utenteService.findById(utenteId);

        // L'utente deve diventare MANAGER
        utenteService.assegnaRuoloManager(utenteId);

        // Assegna il manager al luogo specifico
        switch (tipoLuogo.toLowerCase()) {

            case "biblioteca" ->
                bibliotecaService.assegnaManager(luogoId, utente);

            case "museo" ->
                museoService.assegnaManager(luogoId, utente);

            case "ristorante" ->
                ristoranteService.assegnaManager(luogoId, utente);

            case "locale" ->
                localeService.assegnaManager(luogoId, utente);

            case "zonaverde" ->
                zonaVerdeService.assegnaManager(luogoId, utente);

            default ->
                throw new IllegalArgumentException(
                    "Tipo di luogo non valido: " + tipoLuogo
                );
        }

        return "redirect:/utente/area-persoale";
    }
    
}