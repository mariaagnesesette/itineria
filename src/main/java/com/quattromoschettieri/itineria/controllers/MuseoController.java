package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.MuseoDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.museo.Museo;
import com.quattromoschettieri.itineria.services.MuseoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;


@Controller
@RequestMapping("/musei")
@RequiredArgsConstructor
public class MuseoController {

    private final MuseoService museoService;
    private final UtenteService utenteService;

    // CREATE
    @GetMapping("/nuovo")
    public String formNuovoMuseo(Model model) {

        model.addAttribute("museoDTO", new MuseoDTO());

        return "musei/form";
    }

    @PostMapping
    public String createMuseo(
            @Valid @ModelAttribute MuseoDTO dto,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "musei/form";
        }

        Utente manager =
                utenteService.findByEmail(authentication.getName());

        museoService.create(dto, manager);

        return "redirect:/musei";
    }

    // READ
    // Lista generica
    @GetMapping
    public String listMusei(Pageable pageable, Model model) {
        Page<Museo> tuttiMusei = museoService.findAll(pageable);
        Pageable anteprime = PageRequest.of(0, 6);

        MuseoDTO dtoGuidati = new MuseoDTO();
        dtoGuidati.setGuidaPrenotabile(true);

        MuseoDTO dtoBar = new MuseoDTO();
        dtoBar.setBarInterno(true);

        MuseoDTO dtoAccessibili = new MuseoDTO();
        dtoAccessibili.setAccessibilita(Accessibilita.COMPLETA);

        MuseoDTO dtoSempreAperti = new MuseoDTO();
        dtoSempreAperti.setSempreAperto(true);

        model.addAttribute("tuttiMusei", tuttiMusei.getContent());
        model.addAttribute("museiGuidati",
                museoService.search(dtoGuidati, anteprime).getContent());
        model.addAttribute("museiConBar",
                museoService.search(dtoBar, anteprime).getContent());
        model.addAttribute("museiAccessibili",
                museoService.search(dtoAccessibili, anteprime).getContent());
        model.addAttribute("museiSempreAperti",
                museoService.search(dtoSempreAperti, anteprime).getContent());
        model.addAttribute("musei", tuttiMusei.getContent());
        //
        model.addAttribute("risultati", tuttiMusei);
        model.addAttribute("museoDTO", new MuseoDTO());

        return "luoghi_interesse/musei";
    }

    // Ricerca con filtri
    @GetMapping("/filtri")
    public String filterMusei(
            @ModelAttribute("museoDTO") MuseoDTO dto,
            Pageable pageable,
            Model model) {

        Page<Museo> risultati =
                museoService.search(dto, pageable);

        model.addAttribute("musei", risultati.getContent());
        model.addAttribute("risultati", risultati);
        //
        model.addAttribute("museoDTO", dto);

        return "luoghi_interesse/musei";
    }

    // Ricerca per id
    @GetMapping("/{id}")
    public String detailMuseoId(
            @PathVariable Long id,
            Model model) {

        Museo risultato =
                museoService.findById(id);

        model.addAttribute("museo", risultato);

        return "musei/dettaglio";
    }

    // Ricerca per nome
    @GetMapping("/searchByNome/{nome}")
    public String detailMuseoNome(
            @PathVariable String nome,
            Model model,
            Pageable pageable) {

        Page<Museo> risultati =
                museoService.findByNome(nome, pageable);

        model.addAttribute("musei", risultati.getContent());
        model.addAttribute("bibliotecaDTO", new Biblioteca());

        return "musei/byNome";
    }

    // UPDATE
    @GetMapping("/{id}/modifica")
    public String formModificaMuseo(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "museoDTO",
                museoService.findByIdDto(id));

        return "musei/form";
    }

    @PutMapping("/{id}/modifica")
    public String updateMuseo(
            @PathVariable Long id,
            @Valid @ModelAttribute MuseoDTO dto,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "musei/form";
        }

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        museoService.update(id, dto, utente);

        return "redirect:/musei/" + id;
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteMuseo(
            @PathVariable Long id,
            Authentication authentication) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        museoService.delete(id, utente);

        return "redirect:/musei";
    }
}