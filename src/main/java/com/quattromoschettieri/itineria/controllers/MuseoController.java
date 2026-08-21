package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.MuseoDTO;
import com.quattromoschettieri.itineria.entities.museo.Museo;
import com.quattromoschettieri.itineria.services.MuseoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;;

@Controller
@RequestMapping("/musei")
@RequiredArgsConstructor
public class MuseoController {

    private final MuseoService museoService;

    // CREATE
    @GetMapping("/nuovo")
    public String formNuovoMuseo(Model model) {
        model.addAttribute("museoDTO", new MuseoDTO());
        return "musei/form";
    }

    @PostMapping
    public String createMuseo(@Valid @ModelAttribute MuseoDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "musei/form";
        }

        museoService.create(dto);
        return "redirect:/musei";
    }

    // READ
    // lista generica
    @GetMapping
    public String listaMusei(@ModelAttribute MuseoDTO dto, Pageable pageable, Model model) {

        Page<Museo> risultati = museoService.findAll(pageable);

        model.addAttribute("musei", risultati.getContent());
        model.addAttribute("risultati", risultati);
        model.addAttribute("museoDTO", new MuseoDTO());

        return "luoghi_interesse/musei";
    }

    // ricerca con filtri
    @GetMapping("/filtri")
    public String filterMusei(
            @ModelAttribute("museoDTO") MuseoDTO dto,
            Pageable pageable,
            Model model) {

        Page<Museo> risultati = museoService.search(dto, pageable);

        model.addAttribute("musei", risultati.getContent());
        model.addAttribute("risultati", risultati);

        return "luoghi_interesse/musei";
    }

    // ricerca per id
    @GetMapping("/{id}")
    public String detailMuseoId(@PathVariable Long id, Model model) {

        Museo risultato = museoService.findById(id);

        model.addAttribute("museo", risultato);

        return "biblioteche/dettaglio";
    }

    // ricerca per nome
    @GetMapping("searchByNome/{nome}")
    public String detailMuseoNome(@PathVariable String nome, Model model, Pageable pageable) {

        Page<Museo> risultati = museoService.findByNome(nome, pageable);

        model.addAttribute("risultati", risultati);

        return "musei/byNome";
    }

    // UPDATE
    @GetMapping("/{id}/modifica")
    public String formModificaMuseo(@PathVariable Long id, Model model) {

        model.addAttribute("museoDTO", museoService.findById(id));
        return "musei/form";
    }

    @PutMapping("/{id}/modifica")
    public String updateMuseo(@PathVariable Long id, @Valid @ModelAttribute MuseoDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "musei/form";
        }

        museoService.update(id, dto);

        return "redirect:/musei/" + id;
    }

    // DELETE
    @PostMapping("/{id]/delete")
    public String deleteMuseo(@PathVariable Long id) {

        museoService.delete(id);

        return "redirect:/musei";
    }

}
