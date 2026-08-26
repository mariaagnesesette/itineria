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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.services.BibliotecaService;
import com.quattromoschettieri.itineria.services.ImmagineLuogoService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/biblioteche")
@RequiredArgsConstructor
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;
    private final UtenteService utenteService;
    private final CittaRepository cittaRepository;
    private final ImmagineLuogoService immagineLuogoService;

    // READ
    // ricerca con filtri
    @GetMapping("/filtri")
    public String filterBiblioteche(@ModelAttribute BibliotecaDTO bibliotecaDTO,
            Pageable pageable,
            Model model) {

        Page<Biblioteca> risultati = bibliotecaService.search(bibliotecaDTO, pageable);

        model.addAttribute("risultati", risultati);
        model.addAttribute("bibliotecaDTO", bibliotecaDTO);

        return "luoghi_interesse/biblioteche";
    }

    @GetMapping
    public String listBiblioteche(Model model, Pageable pageable) {
        // Lista tutte le biblioteche
        Page<Biblioteca> tutteBiblioteche = bibliotecaService.findAll(pageable);

        // Sezioni per tipologie (max 6 card per categoria)
        Pageable limitedPage = PageRequest.of(0, 6);

        BibliotecaDTO filterBibliotecaDTO = new BibliotecaDTO();

        // Biblioteche Pubbliche
        filterBibliotecaDTO.setPubblico(true);
        Page<Biblioteca> biblioPubbliche = bibliotecaService.search(filterBibliotecaDTO, limitedPage);

        // Con WiFi
        filterBibliotecaDTO.setPubblico(null);
        filterBibliotecaDTO.setWifi(true);
        Page<Biblioteca> biblioWifi = bibliotecaService.search(filterBibliotecaDTO, limitedPage);

        // Con Area Computer
        filterBibliotecaDTO.setWifi(null);
        filterBibliotecaDTO.setAreaComputer(true);
        Page<Biblioteca> biblioComputer = bibliotecaService.search(filterBibliotecaDTO, limitedPage);

        // Con Area Bambini
        filterBibliotecaDTO.setAreaComputer(null);
        filterBibliotecaDTO.setAreaBambini(true);
        Page<Biblioteca> biblioBambini = bibliotecaService.search(filterBibliotecaDTO, limitedPage);

        // Sempre Aperto
        filterBibliotecaDTO.setAreaBambini(null);
        filterBibliotecaDTO.setSempreAperto(true);
        Page<Biblioteca> biblio24h = bibliotecaService.search(filterBibliotecaDTO, limitedPage);

        // Accessibili
        filterBibliotecaDTO.setSempreAperto(null);
        filterBibliotecaDTO.setAccessibilita(Accessibilita.COMPLETA);
        Page<Biblioteca> biblioAccessibili = bibliotecaService.search(filterBibliotecaDTO, limitedPage);

        // Passa al template
        model.addAttribute("tutteBiblioteche", tutteBiblioteche.getContent());
        model.addAttribute("biblioPubbliche", biblioPubbliche.getContent());
        model.addAttribute("biblioWifi", biblioWifi.getContent());
        model.addAttribute("biblioComputer", biblioComputer.getContent());
        model.addAttribute("biblioBambini", biblioBambini.getContent());
        model.addAttribute("biblio24h", biblio24h.getContent());
        model.addAttribute("biblioAccessibili", biblioAccessibili.getContent());
        model.addAttribute("risultati", tutteBiblioteche);
        model.addAttribute("bibliotecaDTO", new BibliotecaDTO());

        return "luoghi_interesse/biblioteche";
    }

    // ricerca per id
    @GetMapping("/{id}")
    public String detailBibliotecaId(
            @PathVariable Long id,
            @RequestParam(value = "gestione", required = false, defaultValue = "false") boolean gestione,
            Authentication authentication,
            Model model) {

        Biblioteca risultato = bibliotecaService.findById(id);

        Utente utenteAutenticato = (authentication != null && authentication.isAuthenticated())
                ? utenteService.findByEmail(authentication.getName())
                : null;

        model.addAttribute("biblioteca", risultato);
        model.addAttribute("bibliotecaDTO", bibliotecaService.findByIdDto(id));
        model.addAttribute("citta", cittaRepository.findAll());
        model.addAttribute("immagini", immagineLuogoService.findByLuogoId(id));
        model.addAttribute("puoModificare", gestione && puoModificare(risultato, authentication));
        model.addAttribute("utenteId", utenteAutenticato != null ? utenteAutenticato.getId() : null);
        model.addAttribute("isPreferito", utenteAutenticato != null
                && utenteAutenticato.getLuoghiPreferiti().contains(risultato));

        return "luoghi_interesse/luoghi_dettaglio/bibliotecheDettaglio";
    }

    private boolean puoModificare(Biblioteca biblioteca, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (utente.getRuolo() == Ruolo.ADMIN) {
            return true;
        }

        return utente.getRuolo() == Ruolo.MANAGER
                && biblioteca.getManager() != null
                && biblioteca.getManager().getId().equals(utente.getId());
    }

    // ricerca per nome
    @GetMapping("searchByNome/{nome}")
    public String detailBibliotecaNome(@PathVariable String nome, Model model, Pageable pageable) {

        Page<Biblioteca> risultati = bibliotecaService.findByNome(nome, pageable);

        model.addAttribute("biblioteche", risultati.getContent());
        model.addAttribute("bibliotecaDTO", new Biblioteca());

        return "luoghi_interesse/biblioteche";
    }

    // CREATE
    @GetMapping("/nuovo")
    public String formNuovaBiblioteca(Model model) {
        model.addAttribute("bibliotecaDTO", new BibliotecaDTO());
        return "biblioteche/form";
    }

    @PostMapping
    public String createBiblioteca(
            @Valid @ModelAttribute BibliotecaDTO dto, 
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "biblioteche/form";
        }

        Utente manager =
                utenteService.findByEmail(authentication.getName());

        bibliotecaService.create(dto, manager);
        return "redirect:/biblioteche";
    }

    // UPDATE
    // La modifica avviene direttamente nella pagina di dettaglio del luogo
    @GetMapping("/{id}/modifica")
    public String formModificaBiblioteca(@PathVariable Long id) {
        return "redirect:/biblioteche/" + id;
    }

    @PutMapping("/{id}/modifica")
    public String updateBiblioteca(
            @PathVariable Long id,
            @Valid @ModelAttribute BibliotecaDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erroreModifica", "Controlla i dati inseriti: alcuni campi non sono validi.");
            return "redirect:/biblioteche/" + id + "?gestione=true";
        }

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        try {
            bibliotecaService.update(id, dto, utente);
        } catch (IllegalArgumentException | SecurityException e) {
            redirectAttributes.addFlashAttribute("erroreModifica", e.getMessage());
        }

        return "redirect:/biblioteche/" + id + "?gestione=true";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteBiblioteca(
                @PathVariable Long id, 
                Authentication authentication) {

        Utente utente = 
                utenteService.findByEmail(authentication.getName());

        bibliotecaService.delete(id, utente);

        return "redirect:/biblioteche";
    }

}
