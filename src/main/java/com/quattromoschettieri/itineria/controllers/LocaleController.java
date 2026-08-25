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

import com.quattromoschettieri.itineria.DTO.LocaleDTO;
import com.quattromoschettieri.itineria.entities.locale.Locale;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.LocaleService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/locali")
@RequiredArgsConstructor
public class LocaleController {

    private final LocaleService localeService;
    private final UtenteService utenteService;

    // CREATE

    @GetMapping("/nuovo")
    public String formNuovoLocale(Model model) {

        model.addAttribute(
                "localeDTO",
                new LocaleDTO());

        return "locali/form";
    }

    @PostMapping
    public String createLocale(
            @Valid @ModelAttribute LocaleDTO dto,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "locali/form";
        }

        Utente manager =
                utenteService.findByEmail(authentication.getName());

        localeService.create(dto, manager);

        return "redirect:/locali";
    }

    // READ

    @GetMapping("/filtri")
    public String filterLocali(
            @ModelAttribute("localeDTO") LocaleDTO dto,
            Pageable pageable,
            Model model) {

        Page<Locale> risultati =
                localeService.search(dto, pageable);

        model.addAttribute(
                "risultati",
                risultati);

        model.addAttribute(
                "localeDTO",
                dto);

        return "locali/lista";
    }

    @GetMapping
    public String listLocali(Pageable pageable, Model model) {
        Page<Locale> tuttiLocali = localeService.findAll(pageable);
        Pageable anteprime = PageRequest.of(0, 6);

        LocaleDTO dtoSerali = new LocaleDTO();
        dtoSerali.setAperturaSerale(true);

        LocaleDTO dtoEsterni = new LocaleDTO();
        dtoEsterni.setPostiEsterni(true);

        LocaleDTO dtoCeliaci = new LocaleDTO();
        dtoCeliaci.setPerCeliaci(true);

        LocaleDTO dtoSempreAperti = new LocaleDTO();
        dtoSempreAperti.setSempreAperto(true);

        model.addAttribute("tuttiLocali", tuttiLocali.getContent());
        model.addAttribute("localiSerali",
                localeService.search(dtoSerali, anteprime).getContent());
        model.addAttribute("localiEsterni",
                localeService.search(dtoEsterni, anteprime).getContent());
        model.addAttribute("localiCeliaci",
                localeService.search(dtoCeliaci, anteprime).getContent());
        model.addAttribute("localiSempreAperti",
                localeService.search(dtoSempreAperti, anteprime).getContent());
        model.addAttribute("risultati", tuttiLocali);
        model.addAttribute("localeDTO", new LocaleDTO());

        return "luoghi_interesse/locali";
    }

    @GetMapping("/{id}")
    public String detailLocale(
            @PathVariable Long id,
            Model model) {

        Locale locale =
                localeService.findById(id);

        model.addAttribute(
                "locale",
                locale);

        return "luoghi_interesse/luoghi_dettaglio/luoghiDettaglio";
    }

    @GetMapping("/searchByNome/{nome}")
    public String detailLocaleNome(
            @PathVariable String nome,
            Pageable pageable,
            Model model) {

        Page<Locale> risultati = localeService.findByNome(nome, pageable);

        model.addAttribute("locali", risultati.getContent());
        model.addAttribute("localiDTO", new LocaleDTO());
        model.addAttribute("risultati", risultati);

        return "luoghi_interesse/locali";
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
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "locali/form";
        }

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        localeService.update(id, dto, utente);

        return "redirect:/locali/" + id;
    }

    // DELETE

    @PostMapping("/{id}/delete")
    public String deleteLocale(
            @PathVariable Long id,
            Authentication authentication) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        localeService.delete(id, utente);

        return "redirect:/locali";
    }
}