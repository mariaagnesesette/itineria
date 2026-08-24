package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.quattromoschettieri.itineria.entities.zonaVerde.ZonaVerde;
import com.quattromoschettieri.itineria.services.ZonaVerdeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/zoneVerdi")
@RequiredArgsConstructor
public class ZonaVerdeController {

    private final ZonaVerdeService zonaVerdeService;

    // READ

    @GetMapping("/filtri")
    public String filterZoneVerdi(
            @ModelAttribute ZonaVerdeDTO zonaVerdeDTO,
            Pageable pageable,
            Model model) {

        Page<ZonaVerde> risultati = zonaVerdeService.search(zonaVerdeDTO, pageable);

        model.addAttribute("risultati", risultati);
        model.addAttribute("zonaVerdeDTO", zonaVerdeDTO);

        return "zoneVerdi/lista";
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

        return "zoneVerdi/lista";
    }

    @GetMapping("/{id}")
    public String detailZonaVerde(
            @PathVariable Long id,
            Model model) {

        ZonaVerde risultato = zonaVerdeService.findById(id);

        model.addAttribute("zonaVerde", risultato);

        return "zoneVerdi/dettaglio";
    }

    @GetMapping("/searchByNome/{nome}")
    public String detailZonaVerdeNome(
            @PathVariable String nome,
            Pageable pageable,
            Model model) {

        Page<ZonaVerde> risultati = zonaVerdeService.findByNome(nome, pageable);

        model.addAttribute("risultati", risultati);

        return "zoneVerdi/byNome";
    }

    // CREATE

    @GetMapping("/nuovo")
    public String formNuovaZonaVerde(Model model) {
        model.addAttribute("zonaVerdeDTO", new ZonaVerdeDTO());
        return "zoneVerdi/form";
    }

    @PostMapping
    public String createZonaVerde(
            @Valid @ModelAttribute ZonaVerdeDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "zoneVerdi/form";
        }

        zonaVerdeService.create(dto);

        return "redirect:/zoneVerdi";
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
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "zoneVerdi/form";
        }

        zonaVerdeService.update(id, dto);

        return "redirect:/zoneVerdi/" + id;
    }

    // DELETE

    @PostMapping("/{id}/delete")
    public String deleteZonaVerde(@PathVariable Long id) {

        zonaVerdeService.delete(id);

        return "redirect:/zoneVerdi";
    }
}