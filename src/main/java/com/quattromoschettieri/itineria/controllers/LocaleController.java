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

import com.quattromoschettieri.itineria.DTO.LocaleDTO;
import com.quattromoschettieri.itineria.entities.locale.Locale;
import com.quattromoschettieri.itineria.services.LocaleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/locali")
@RequiredArgsConstructor
public class LocaleController {

    private final LocaleService localeService;

    // READ

    @GetMapping("/filtri")
    public String filterLocali(
            @ModelAttribute LocaleDTO localeDTO,
            Pageable pageable,
            Model model) {

        Page<Locale> risultati =
                localeService.search(localeDTO, pageable);

        model.addAttribute("risultati", risultati);
        model.addAttribute("localeDTO", localeDTO);

        return "locali/lista";
    }

    @GetMapping
    public String listLocali(
            Pageable pageable,
            Model model) {

        Page<Locale> risultati =
                localeService.findAll(pageable);

        model.addAttribute("risultati", risultati);

        return "locali/lista";
    }

    @GetMapping("/{id}")
    public String detailLocale(
            @PathVariable Long id,
            Model model) {

        Locale locale = localeService.findById(id);

        model.addAttribute("locale", locale);

        return "locali/dettaglio";
    }

    @GetMapping("/searchByNome/{nome}")
    public String detailLocaleNome(
            @PathVariable String nome,
            Pageable pageable,
            Model model) {

        Page<Locale> risultati =
                localeService.findByNome(nome, pageable);

        model.addAttribute("risultati", risultati);

        return "locali/byNome";
    }

    // CREATE

    @GetMapping("/nuovo")
    public String formNuovoLocale(Model model) {
        model.addAttribute("localeDTO", new LocaleDTO());

        return "locali/form";
    }

    @PostMapping
    public String createLocale(
            @Valid @ModelAttribute LocaleDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "locali/form";
        }

        localeService.create(dto);

        return "redirect:/locali";
    }

    // UPDATE

    @GetMapping("/{id}/modifica")
    public String formModificaLocale(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "localeDTO",
                localeService.findByIdDto(id));

        return "locali/form";
    }

    @PutMapping("/{id}/modifica")
    public String updateLocale(
            @PathVariable Long id,
            @Valid @ModelAttribute LocaleDTO dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "locali/form";
        }

        localeService.update(id, dto);

        return "redirect:/locali/" + id;
    }

    // DELETE

    @PostMapping("/{id}/delete")
    public String deleteLocale(@PathVariable Long id) {

        localeService.delete(id);

        return "redirect:/locali";
    }
}