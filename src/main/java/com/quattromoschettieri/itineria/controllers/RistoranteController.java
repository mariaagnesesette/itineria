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
import org.springframework.web.bind.annotation.RequestParam;

import com.quattromoschettieri.itineria.DTO.RistoranteDTO;
import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.services.ImmagineLuogoService;
import com.quattromoschettieri.itineria.services.RistoranteService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ristoranti")
@RequiredArgsConstructor
public class RistoranteController {

        private final RistoranteService ristoranteService;
        private final UtenteService utenteService;
        private final CittaRepository cittaRepository;
        private final ImmagineLuogoService immagineLuogoService;

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
                        @RequestParam(value = "gestione", required = false, defaultValue = "false") boolean gestione,
                        Authentication authentication,
                        Model model) {

                Ristorante risultato = ristoranteService.findById(id);

                Utente utenteAutenticato = (authentication != null && authentication.isAuthenticated())
                                ? utenteService.findByEmail(authentication.getName())
                                : null;

                model.addAttribute(
                                "ristorante",
                                risultato);

                model.addAttribute("ristoranteDTO", ristoranteService.findByIdDto(id));
                model.addAttribute("citta", cittaRepository.findAll());
                model.addAttribute("immagini", immagineLuogoService.findByLuogoId(id));
                model.addAttribute("puoModificare", gestione && puoModificare(risultato, authentication));
                model.addAttribute("utenteId", utenteAutenticato != null ? utenteAutenticato.getId() : null);
                model.addAttribute("isPreferito", utenteAutenticato != null
                                && utenteAutenticato.getLuoghiPreferiti().contains(risultato));

                return "luoghi_interesse/luoghi_dettaglio/ristorantiDettaglio";
        }

        private boolean puoModificare(Ristorante ristorante, Authentication authentication) {
                if (authentication == null || !authentication.isAuthenticated()) {
                        return false;
                }

                Utente utente = utenteService.findByEmail(authentication.getName());

                if (utente.getRuolo() == Ruolo.ADMIN) {
                        return true;
                }

                return utente.getRuolo() == Ruolo.MANAGER
                                && ristorante.getManager() != null
                                && ristorante.getManager().getId().equals(utente.getId());
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
        // La modifica avviene direttamente nella pagina di dettaglio del luogo

        @GetMapping("/{id}/modifica")
        public String formModificaRistorante(@PathVariable Long id) {
                return "redirect:/ristoranti/" + id;
        }

        @PutMapping("/{id}/modifica")
        public String updateRistorante(
                        @PathVariable Long id,
                        @Valid @ModelAttribute RistoranteDTO dto,
                        BindingResult bindingResult,
                        Authentication authentication,
                        RedirectAttributes redirectAttributes) {

                if (bindingResult.hasErrors()) {
                        redirectAttributes.addFlashAttribute("erroreModifica", "Controlla i dati inseriti: alcuni campi non sono validi.");
                        return "redirect:/ristoranti/" + id + "?gestione=true";
                }

                Utente utente = utenteService.findByEmail(authentication.getName());

                try {
                        ristoranteService.update(id, dto, utente);
                } catch (IllegalArgumentException | SecurityException e) {
                        redirectAttributes.addFlashAttribute("erroreModifica", e.getMessage());
                }

                return "redirect:/ristoranti/" + id + "?gestione=true";
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
