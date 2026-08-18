package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.services.BibliotecaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/biblioteche")
@RequiredArgsConstructor
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @GetMapping
    public String search(@ModelAttribute BibliotecaDTO bibliotecaDTO,
                          Pageable pageable,
                          Model model) {

        Page<Biblioteca> risultati = bibliotecaService.search(bibliotecaDTO, pageable);

        model.addAttribute("risultati", risultati);
        model.addAttribute("bibliotecaDTO", bibliotecaDTO);

        return "biblioteche/lista";
    }



}
