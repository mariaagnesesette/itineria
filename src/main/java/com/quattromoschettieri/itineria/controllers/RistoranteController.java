package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
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

import com.quattromoschettieri.itineria.DTO.RistoranteDTO;
import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;
import com.quattromoschettieri.itineria.services.RistoranteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ristoranti")
@RequiredArgsConstructor
public class RistoranteController {

    private final RistoranteService ristoranteService;

    // CREATE
    @GetMapping("/nuovo")
    public String formNuovoRistorante(Model model) {
        model.addAttribute("ristoranteDTO", new RistoranteDTO());
        return "ristoranti/form";
    }

    @PostMapping
    public String createRistorante(@Valid @ModelAttribute RistoranteDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "musei/form";
        }

        ristoranteService.create(dto);
        return "redirect:/ristoranti";
    }

    // READ
    // lista generica
    @GetMapping
    public String listaRistoranti(@ModelAttribute RistoranteDTO dto, Pageable pageable, Model model) {

        Page<Ristorante> risultati = ristoranteService.findAll(pageable);

        model.addAttribute("risultati", risultati);

        return "ristoranti/lista";
    }

    // ricerca con filtri
    @GetMapping("/filtri")
    public String filterMusei(@ModelAttribute RistoranteDTO dto, Pageable pageable, Model model) {

        Page<Ristorante> risultati = ristoranteService.search(dto, pageable);

        model.addAttribute("risultati", risultati);
        model.addAttribute("ristoranteDTO", dto);

        return "ristoranti/lista";
    }

    // ricerca per id
    @GetMapping("/{id}")
    public String detailRistoranteId(@PathVariable Long id, Model model) {

        Ristorante risultato = ristoranteService.findById(id);

        model.addAttribute("ristorante", risultato);

        return "ristoranti/dettaglio";
    }

    // ricerca per nome
    @GetMapping("searchByNome/{nome}")
    public String detailRistoranteNome(@PathVariable String nome, Model model, Pageable pageable) {

        Page<Ristorante> risultati = ristoranteService.findByNome(nome, pageable);

        model.addAttribute("risultati", risultati);

        return "ristoranti/byNome";
    }

    // UPDATE
        @GetMapping("/{id}/modifica")
    public String formModificaRistorante(@PathVariable Long id, Model model) {

        model.addAttribute("museoDTO", ristoranteService.findById(id));
        return "ristoranti/form";
    }

    @PutMapping("/{id}/modifica")
    public String updateRistoranti(@PathVariable Long id, @Valid @ModelAttribute RistoranteDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "ristoranti/form";
        }

        ristoranteService.update(id, dto);

        return "redirect:/ristoranti/" + id;
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteRistorante(@PathVariable Long id) {

        ristoranteService.delete(id);

        return "redirect:/ristoranti";
    }

}
