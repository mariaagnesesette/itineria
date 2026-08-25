package com.quattromoschettieri.itineria.controllers;

import org.springframework.data.domain.Page;
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

import com.quattromoschettieri.itineria.DTO.RistoranteDTO;
import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.RistoranteService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ristoranti")
@RequiredArgsConstructor
public class RistoranteController {

        private final RistoranteService ristoranteService;
        private final UtenteService utenteService;

        // CREATE

        @GetMapping("/nuovo")
        public String formNuovoRistorante(Model model) {

                model.addAttribute(
                                "ristoranteDTO",
                                new RistoranteDTO());

                return "ristoranti/form";
        }

        @PostMapping
        public String createRistorante(
                        @Valid @ModelAttribute RistoranteDTO dto,
                        BindingResult bindingResult,
                        Authentication authentication) {

                if (bindingResult.hasErrors()) {
                        return "ristoranti/form";
                }

                Utente manager = utenteService.findByEmail(authentication.getName());

                ristoranteService.create(dto, manager);

                return "redirect:/ristoranti";
        }

        // READ

        @GetMapping
        public String listRistoranti(
                        Pageable pageable,
                        Model model) {

                Page<Ristorante> tuttiRistoranti = ristoranteService.findAll(pageable);

                model.addAttribute(
                                "tuttiRistoranti",
                                tuttiRistoranti.getContent());

                model.addAttribute(
                                "risultati",
                                tuttiRistoranti);

                model.addAttribute(
                                "ristoranti",
                                tuttiRistoranti.getContent());

                model.addAttribute(
                                "ristoranteDTO",
                                new RistoranteDTO());

                return "luoghi_interesse/ristoranti";
        }

        @GetMapping("/filtri")
        public String filterRistoranti(
                        @ModelAttribute("ristoranteDTO") RistoranteDTO dto,
                        Pageable pageable,
                        Model model) {

                Page<Ristorante> risultati = ristoranteService.search(dto, pageable);

                model.addAttribute("risultati", risultati);
                model.addAttribute("ristoranti", risultati.getContent());
                model.addAttribute("ristoranteDTO", dto);

                return "luoghi_interesse/ristoranti";
        }

        @GetMapping("/{id}")
        public String detailRistoranteId(
                        @PathVariable Long id,
                        Model model) {

                Ristorante risultato = ristoranteService.findById(id);

                model.addAttribute(
                                "ristorante",
                                risultato);

                return "luoghi_interesse/luoghi_dettaglio/ristorantiDettaglio";
        }

        @GetMapping("/searchByNome/{nome}")
        public String detailRistoranteNome(
                        @PathVariable String nome,
                        Pageable pageable,
                        Model model) {

                Page<Ristorante> risultati = ristoranteService.findByNome(
                                nome,
                                pageable);

                model.addAttribute("ristoranti", risultati.getContent());
                model.addAttribute("ristoranteDTO", new RistoranteDTO());
                model.addAttribute("risultati", risultati);

                return "luoghi_interesse/ristoranti";
        }

        // UPDATE

        @GetMapping("/{id}/modifica")
        public String formModificaRistorante(
                        @PathVariable Long id,
                        Model model) {

                model.addAttribute(
                                "ristoranteDTO",
                                ristoranteService.findByIdDto(id));

                return "ristoranti/form";
        }

        @PutMapping("/{id}/modifica")
        public String updateRistorante(
                        @PathVariable Long id,
                        @Valid @ModelAttribute RistoranteDTO dto,
                        BindingResult bindingResult,
                        Authentication authentication) {

                if (bindingResult.hasErrors()) {
                        return "ristoranti/form";
                }

                Utente utente = utenteService.findByEmail(authentication.getName());

                ristoranteService.update(id, dto, utente);

                return "redirect:/ristoranti/" + id;
        }

        // DELETE

        @PostMapping("/{id}/delete")
        public String deleteRistorante(
                        @PathVariable Long id,
                        Authentication authentication) {

                Utente utente = utenteService.findByEmail(authentication.getName());

                ristoranteService.delete(id, utente);

                return "redirect:/ristoranti";
        }
}