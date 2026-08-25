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
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.CittaDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.citta.Regione;
import com.quattromoschettieri.itineria.services.CittaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/citta")
@RequiredArgsConstructor
public class CittaController {

    private final CittaService cittaService;

    // READ
    @GetMapping
    public String listCitta(@ModelAttribute CittaDTO dto, Pageable pageable, Model model) {

        Page<Citta> risultati = cittaService.findAll(pageable);

        model.addAttribute("risultati", risultati);

        return "citta/lista";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {

        Citta risultato = cittaService.findById(id);

        model.addAttribute("citta", risultato);

        return "citta/dettaglio";
    }

    @GetMapping("/regione/{regione}")
    public String findByRegione(@PathVariable Regione regione, Model model, Pageable pageable) {

        Page<Citta> risultati = cittaService.findByRegione(regione, pageable);

        model.addAttribute("risultati", risultati);

        return "citta/regione";

    }

    @GetMapping("/nome/{nome}")
    public String findByNome(@PathVariable String nome, Model model, Pageable pageable) {

        Page<Citta> risultati = cittaService.findByNome(nome, pageable);

        model.addAttribute("risultati", risultati);

        return "citta/nome";
    }

    // CREATE
    @PostMapping("/nuovo")
    public String formNuovaCita(Model model) {

        model.addAttribute("cittaDTO", new CittaDTO());
        return "citta/form";
    }

    @PostMapping
    public String createCitta(@Valid @ModelAttribute CittaDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "citta/form";
        }

        cittaService.create(dto);
        return "redirect:/citta";
    }

    // UPDATE
    @GetMapping("/{id}/modifica")
    public String formModificaCitta(@PathVariable Long id, Model model) {
        model.addAttribute("cittaDTO", cittaService.findByIdDto(id));

        return "citta/form";
    }

    @PostMapping("/{id}/modifica")
    public String updateCitta(@PathVariable Long id, @Valid @ModelAttribute CittaDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "citta/form";
        }

        cittaService.update(id, dto);

        return "redirect:/citta/" + id;
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteCitta(@PathVariable Long id) {

        cittaService.delete(id);

        return "redirect:/citta";
    }
}
