package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.ZonaVerdeDTO;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.entities.zonaVerde.ZonaVerde;
import com.quattromoschettieri.itineria.services.ZonaVerdeService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/zoneVerdi")
@RequiredArgsConstructor
public class ZonaVerdeController {

    private final ZonaVerdeService zonaVerdeService;
    private final UtenteService utenteService;

    // CREATE

    @GetMapping("/nuovo")
    public String formNuovaZonaVerde(Model model) {

        model.addAttribute(
                "zonaVerdeDTO",
                new ZonaVerdeDTO());

        return "zoneVerdi/form";
    }

    @PostMapping
    public String createZonaVerde(
            @Valid @ModelAttribute ZonaVerdeDTO dto,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "zoneVerdi/form";
        }

        Utente manager =
                utenteService.findByEmail(authentication.getName());

        zonaVerdeService.create(dto, manager);

        return "redirect:/zoneVerdi";
    }

    // READ

    @GetMapping("/filtri")
    public String filterZoneVerdi(
            @ModelAttribute("zonaVerdeDTO") ZonaVerdeDTO dto,
            Pageable pageable,
            Model model) {

        Page<ZonaVerde> risultati = zonaVerdeService.search(dto, pageable);

        model.addAttribute("risultati", risultati);
        model.addAttribute("parchi", risultati.getContent());
        model.addAttribute(
                "zonaVerdeDTO",
                dto);

        return "luoghi_interesse/zoneVerdi";
    }

    @GetMapping
    public String listZoneVerdi(Pageable pageable, Model model) {
        Page<ZonaVerde> tutteZone = zonaVerdeService.findAll(pageable);
        Pageable anteprime = PageRequest.of(0, 6);

        ZonaVerdeDTO dtoCiclabili = new ZonaVerdeDTO();
        dtoCiclabili.setCiclabile(true);

        ZonaVerdeDTO dtoCani = new ZonaVerdeDTO();
        dtoCani.setDogFriendly(true);

        ZonaVerdeDTO dtoRistoro = new ZonaVerdeDTO();
        dtoRistoro.setRistoro(true);

        ZonaVerdeDTO dtoSempreAperte = new ZonaVerdeDTO();
        dtoSempreAperte.setSempreAperto(true);
;
        model.addAttribute("tutteZone", tutteZone.getContent());
        model.addAttribute("zoneCiclabili",
                zonaVerdeService.search(dtoCiclabili, anteprime).getContent());
        model.addAttribute("zoneDogFriendly",
                zonaVerdeService.search(dtoCani, anteprime).getContent());
        model.addAttribute("zoneConRistoro",
                zonaVerdeService.search(dtoRistoro, anteprime).getContent());
        model.addAttribute("zoneSempreAperte",
                zonaVerdeService.search(dtoSempreAperte, anteprime).getContent());
        model.addAttribute("risultati", tutteZone);
        model.addAttribute("parchi", tutteZone.getContent());
        model.addAttribute("zonaVerdeDTO", new ZonaVerdeDTO());

        return "luoghi_interesse/zoneVerdi";
    }

    @GetMapping("/{id}")
    public String detailZonaVerde(
            @PathVariable Long id,
            Model model) {

        ZonaVerde risultato = zonaVerdeService.findById(id);

        model.addAttribute(
                "zonaVerde",
                risultato);

        return "luoghi_interesse/luoghi_dettaglio/parchiDettaglio";
    }

    @GetMapping("/searchByNome/{nome}")
    public String detailZonaVerdeNome(
            @PathVariable String nome,
            Pageable pageable,
            Model model) {

        Page<ZonaVerde> risultati = zonaVerdeService.findByNome(nome, pageable);

        model.addAttribute("parchi", risultati.getContent());
        model.addAttribute("zonaVerdeDTO", new ZonaVerde());
        model.addAttribute(
                "risultati",
                risultati);

        return "luoghi_interesse/zoneVerdi";
    }

    // UPDATE

    @GetMapping("/{id}/modifica")
    public String formModificaZonaVerde(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "zonaVerdeDTO",
                zonaVerdeService.findByIdDto(id));

        return "zoneVerdi/form";
    }

    @PutMapping("/{id}/modifica")
    public String updateZonaVerde(
            @PathVariable Long id,
            @Valid @ModelAttribute ZonaVerdeDTO dto,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "zoneVerdi/form";
        }

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        zonaVerdeService.update(id, dto, utente);

        return "redirect:/zoneVerdi/" + id;
    }

    // DELETE

    @PostMapping("/{id}/delete")
    public String deleteZonaVerde(
            @PathVariable Long id,
            Authentication authentication) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        zonaVerdeService.delete(id, utente);

        return "redirect:/zoneVerdi";
    }
}