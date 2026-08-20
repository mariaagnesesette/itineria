package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.services.BibliotecaService;
import com.quattromoschettieri.itineria.services.CittaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/biblioteche")
@RequiredArgsConstructor
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;
    private final CittaService cittaService;

    //READ
    //ricerca con filtri
    @GetMapping("/filtri")
    public String filtriBiblioteche(@ModelAttribute BibliotecaDTO bibliotecaDTO,
            Pageable pageable,
            Model model) {

        Page<Biblioteca> risultati = bibliotecaService.search(bibliotecaDTO, pageable);

        model.addAttribute("risultati", risultati);
        model.addAttribute("bibliotecaDTO", bibliotecaDTO);

        return "biblioteche/lista";
    }

    //lista generica
    @GetMapping
    public String listaBiblioteche(
            @ModelAttribute BibliotecaDTO bibliotecaDTO,
            Pageable pageable,
            Model model) {

        Page<Biblioteca> risultati = bibliotecaService.findAll(pageable);

        model.addAttribute("risultati", risultati);

        return "biblioteche/lista";
    }

    //ricerca per id
    @GetMapping("/{id}")
    public String dettaglioBiblioteca(@PathVariable Long id, Model model) {

        Biblioteca risultato = bibliotecaService.findById(id);

        model.addAttribute("biblioteca", risultato);

        return "biblioteche/dettaglio";
    }

    //ricerca per nome
    @GetMapping("/{nome}")
    public String dettaglioBibliotecaNome(@PathVariable String nome, Model model, Pageable pageable) {
        
        Page<Biblioteca> risultati = bibliotecaService.findByNome(nome, pageable);

        model.addAttribute("risultati", risultati);

        return "biblioteche/byNome";
    }

    //CREATE

    @PostMapping
    public String creaBiblioteca(@Valid @ModelAttribute BibliotecaDTO dto, BindingResult bindingResult) {
        
        if(bindingResult.hasErrors()){
            return "biblioteche/form";
        }

        bibliotecaService.create(dto);
        return "redirect:/biblioteche";
    }

    //MODIFCA

    

}
