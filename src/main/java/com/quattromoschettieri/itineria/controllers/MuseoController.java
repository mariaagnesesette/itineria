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
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.services.ImmagineLuogoService;
import com.quattromoschettieri.itineria.services.MuseoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;


@Controller
@RequestMapping("/musei")
@RequiredArgsConstructor
public class MuseoController {

    private final MuseoService museoService;
    private final UtenteService utenteService;
    private final CittaRepository cittaRepository;
    private final ImmagineLuogoService immagineLuogoService;

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
                Page<Museo> tuttiMusei = museoService.findAll(Pageable.unpaged());
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
            @RequestParam(value = "gestione", required = false, defaultValue = "false") boolean gestione,
            Authentication authentication,
            Model model) {

        Museo risultato =
                museoService.findById(id);

        Utente utenteAutenticato = (authentication != null && authentication.isAuthenticated())
                ? utenteService.findByEmail(authentication.getName())
                : null;

        model.addAttribute("museo", risultato);
        model.addAttribute("museoDTO", museoService.findByIdDto(id));
        model.addAttribute("citta", cittaRepository.findAll());
        model.addAttribute("immagini", immagineLuogoService.findByLuogoId(id));
        model.addAttribute("puoModificare", gestione && puoModificare(risultato, authentication));
        model.addAttribute("utenteId", utenteAutenticato != null ? utenteAutenticato.getId() : null);
        model.addAttribute("isPreferito", utenteAutenticato != null
                && utenteAutenticato.getLuoghiPreferiti().contains(risultato));

        return "luoghi_interesse/luoghi_dettaglio/museoDettaglio";
    }

    private boolean puoModificare(Museo museo, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (utente.getRuolo() == Ruolo.ADMIN) {
            return true;
        }

        return utente.getRuolo() == Ruolo.MANAGER
                && museo.getManager() != null
                && museo.getManager().getId().equals(utente.getId());
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
    // La modifica avviene direttamente nella pagina di dettaglio del luogo
    @GetMapping("/{id}/modifica")
    public String formModificaMuseo(@PathVariable Long id) {
        return "redirect:/musei/" + id;
    }

    @PutMapping("/{id}/modifica")
    public String updateMuseo(
            @PathVariable Long id,
            @Valid @ModelAttribute MuseoDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erroreModifica", "Controlla i dati inseriti: alcuni campi non sono validi.");
            return "redirect:/musei/" + id + "?gestione=true";
        }

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        try {
            museoService.update(id, dto, utente);
        } catch (IllegalArgumentException | SecurityException e) {
            redirectAttributes.addFlashAttribute("erroreModifica", e.getMessage());
        }

        return "redirect:/musei/" + id + "?gestione=true";
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