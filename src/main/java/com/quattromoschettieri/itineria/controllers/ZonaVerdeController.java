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
import org.springframework.web.bind.annotation.RequestParam;

import com.quattromoschettieri.itineria.DTO.ZonaVerdeDTO;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.entities.zonaVerde.ZonaVerde;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.services.ImmagineLuogoService;
import com.quattromoschettieri.itineria.services.ZonaVerdeService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/zoneVerdi")
@RequiredArgsConstructor
public class ZonaVerdeController {

    private final ZonaVerdeService zonaVerdeService;
    private final UtenteService utenteService;
    private final CittaRepository cittaRepository;
    private final ImmagineLuogoService immagineLuogoService;

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
    public String detailZonaVerdeId(
            @PathVariable Long id,
            @RequestParam(value = "gestione", required = false, defaultValue = "false") boolean gestione,
            Authentication authentication,
            Model model) {

        ZonaVerde risultato = zonaVerdeService.findById(id);

        Utente utenteAutenticato = (authentication != null && authentication.isAuthenticated())
                ? utenteService.findByEmail(authentication.getName())
                : null;

        model.addAttribute(
                "zonaVerde",
                risultato);
        model.addAttribute("zonaVerdeDTO", zonaVerdeService.findByIdDto(id));
        model.addAttribute("citta", cittaRepository.findAll());
        model.addAttribute("immagini", immagineLuogoService.findByLuogoId(id));
        model.addAttribute("puoModificare", gestione && puoModificare(risultato, authentication));
        model.addAttribute("utenteId", utenteAutenticato != null ? utenteAutenticato.getId() : null);
        model.addAttribute("isPreferito", utenteAutenticato != null
                && utenteAutenticato.getLuoghiPreferiti().contains(risultato));

        return "luoghi_interesse/luoghi_dettaglio/parchiDettaglio";
    }

    private boolean puoModificare(ZonaVerde zonaVerde, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (utente.getRuolo() == Ruolo.ADMIN) {
            return true;
        }

        return utente.getRuolo() == Ruolo.MANAGER
                && zonaVerde.getManager() != null
                && zonaVerde.getManager().getId().equals(utente.getId());
    }

    @GetMapping("/searchByNome/{nome}")
    public String detailZonaVerdeNome(
            @PathVariable String nome,
            Pageable pageable,
            Model model) {

        Page<ZonaVerde> risultati = zonaVerdeService.findByNome(nome, pageable);

        model.addAttribute("parchi", risultati.getContent());
        model.addAttribute("zonaVerdeDTO", new ZonaVerdeDTO());
        model.addAttribute(
                "risultati",
                risultati);

        return "luoghi_interesse/zoneVerdi";
    }

    // UPDATE

    // La modifica avviene direttamente nella pagina di dettaglio del luogo
    @GetMapping("/{id}/modifica")
    public String formModificaZonaVerde(@PathVariable Long id) {
        return "redirect:/zoneVerdi/" + id;
    }

    @PutMapping("/{id}/modifica")
    public String updateZonaVerde(
            @PathVariable Long id,
            @Valid @ModelAttribute ZonaVerdeDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erroreModifica", "Controlla i dati inseriti: alcuni campi non sono validi.");
            return "redirect:/zoneVerdi/" + id + "?gestione=true";
        }

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        try {
            zonaVerdeService.update(id, dto, utente);
        } catch (IllegalArgumentException | SecurityException e) {
            redirectAttributes.addFlashAttribute("erroreModifica", e.getMessage());
        }

        return "redirect:/zoneVerdi/" + id + "?gestione=true";
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