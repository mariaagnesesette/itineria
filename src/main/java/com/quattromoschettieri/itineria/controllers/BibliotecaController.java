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

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.BibliotecaService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/biblioteche")
@RequiredArgsConstructor
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;
    private final UtenteService utenteService;

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
    public String detailBibliotecaId(@PathVariable Long id, Model model) {

        Biblioteca risultato = bibliotecaService.findById(id);

        model.addAttribute("biblioteca", risultato);

        return "luoghi_interesse/luoghi_dettaglio/bibliotecheDettaglio";
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
    @GetMapping("/{id}/modifica")
    public String formModificaBiblioteca(@PathVariable Long id, Model model) {
        model.addAttribute("bibliotecaDTO", bibliotecaService.findByIdDto(id));
        return "biblioteche/form";
    }

    @PutMapping("/{id}/modifica")
    public String updateBiblioteca(
            @PathVariable Long id, 
            @Valid @ModelAttribute BibliotecaDTO dto,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "biblioteche/form";
        }

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        bibliotecaService.update(id, dto, utente);

        return "redirect:/biblioteche/" + id;

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
