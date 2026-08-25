package com.quattromoschettieri.itineria.controllers;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quattromoschettieri.itineria.DTO.eventoDTO.DataEventoDTO;
import com.quattromoschettieri.itineria.DTO.eventoDTO.EventoDTO;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.evento.PubblicoEvento;
import com.quattromoschettieri.itineria.entities.evento.TipologiaEvento;
import com.quattromoschettieri.itineria.services.EventoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;


    // =========================
    // VISUALIZZAZIONE EVENTI
    // =========================

    /*
     * Mostra la pagina principale con tutti gli eventi.
     */
    @GetMapping
    public String findAll(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi = eventoService.findAll(pageable);

        model.addAttribute("eventi", eventi);
        model.addAttribute("eventoDTO", new EventoDTO());

        return "luoghi_interesse/eventi"; // CORRETTO: file eventi.html è in luoghi_interesse/
    }


    /*
     * Mostra il dettaglio di un singolo evento.
     */
    @GetMapping("/{id}")
    public String findById(
            @PathVariable Long id,
            Model model) {

        Evento evento = eventoService.findById(id);

        model.addAttribute("evento", evento);

        return "luoghi_interesse/luoghi_dettaglio/eventoDettaglio"; // CORRETTO: percorso + nome file
    }


    /*
     * Mostra gli eventi conclusi.
     */
    @GetMapping("/storico")
    public String findStoricoEventi(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi =
                eventoService.findStoricoEventi(pageable);

        model.addAttribute("eventi", eventi);

        return "eventi/storico"; // DA VERIFICARE: non visibile nello screenshot
    }


    /*
     * Mostra gli eventi futuri.
     */
    @GetMapping("/futuri")
    public String findEventiFuturi(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi =
                eventoService.findEventiFuturi(pageable);

        model.addAttribute("eventi", eventi);

        return "eventi/futuri"; // DA VERIFICARE: non visibile nello screenshot
    }


    /*
     * Mostra gli eventi attualmente in corso.
     */
    @GetMapping("/in-corso")
    public String findEventiInCorso(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi =
                eventoService.findEventiInCorso(pageable);

        model.addAttribute("eventi", eventi);

        return "eventi/in-corso"; // DA VERIFICARE: non visibile nello screenshot
    }


    // =========================
    // RICERCA EVENTI
    // =========================

    @GetMapping("/search")
    public String search(
            @ModelAttribute EventoDTO dto,

            @RequestParam(required = false)
            BigDecimal prezzoMinimo,

            @RequestParam(required = false)
            BigDecimal prezzoMassimo,

            Pageable pageable,

            Model model) {

        Page<Evento> eventi = eventoService.search(
                dto,
                prezzoMinimo,
                prezzoMassimo,
                pageable
        );

        model.addAttribute("eventi", eventi);

        model.addAttribute("eventoDTO", dto);

        return "luoghi_interesse/eventi"; // CORRETTO: stesso file di findAll
    }


    // =========================
    // CREAZIONE EVENTO
    // =========================

    @GetMapping("/nuovo")
    public String nuovoEvento(Model model) {

        model.addAttribute(
                "eventoDTO",
                new EventoDTO()
        );

        model.addAttribute(
                "tipologieEvento",
                TipologiaEvento.values()
        );

        model.addAttribute(
                "pubbliciEvento",
                PubblicoEvento.values()
        );

        return "eventi/nuovo"; // DA VERIFICARE: non visibile nello screenshot
    }


    @PostMapping
    public String save(
            @ModelAttribute EventoDTO dto,
            RedirectAttributes redirectAttributes) {

        eventoService.save(dto);

        redirectAttributes.addFlashAttribute(
                "messaggio",
                "Evento creato con successo"
        );

        return "redirect:/eventi";
    }


    // =========================
    // MODIFICA EVENTO
    // =========================

    @GetMapping("/{id}/modifica")
    public String modificaEvento(
            @PathVariable Long id,
            Model model) {

        EventoDTO dto = eventoService.findDtoById(id);

        model.addAttribute("eventoDTO", dto);

        model.addAttribute(
                "tipologieEvento",
                TipologiaEvento.values()
        );

        model.addAttribute(
                "pubbliciEvento",
                PubblicoEvento.values()
        );

        return "eventi/modifica"; // DA VERIFICARE: non visibile nello screenshot
    }


    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute EventoDTO dto,
            RedirectAttributes redirectAttributes) {

        eventoService.update(id, dto);

        redirectAttributes.addFlashAttribute(
                "messaggio",
                "Evento modificato con successo"
        );

        return "redirect:/eventi/" + id;
    }


    // =========================
    // ELIMINAZIONE EVENTO
    // =========================

    @PostMapping("/{id}/elimina")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        eventoService.delete(id);

        redirectAttributes.addFlashAttribute(
                "messaggio",
                "Evento eliminato con successo"
        );

        return "redirect:/eventi";
    }


    // =========================
    // GESTIONE DATE EVENTO
    // =========================

    @GetMapping("/{id}/date/nuova")
    public String nuovaDataEvento(
            @PathVariable Long id,
            Model model) {

        Evento evento = eventoService.findById(id);

        model.addAttribute("evento", evento);

        model.addAttribute(
                "dataEventoDTO",
                new DataEventoDTO()
        );

        return "eventi/date/nuova"; // DA VERIFICARE: non visibile nello screenshot
    }


    @PostMapping("/{id}/date")
    public String addDataEvento(
            @PathVariable Long id,
            @ModelAttribute DataEventoDTO dto,
            RedirectAttributes redirectAttributes) {

        eventoService.addDataEvento(id, dto);

        redirectAttributes.addFlashAttribute(
                "messaggio",
                "Data evento aggiunta con successo"
        );

        return "redirect:/eventi/" + id;
    }


    @GetMapping("/{id}/date/{idDataEvento}/modifica")
    public String modificaDataEvento(
            @PathVariable Long id,
            @PathVariable Long idDataEvento,
            Model model) {

        Evento evento = eventoService.findById(id);

        DataEventoDTO dto =
                eventoService.findDataEventoById(
                        id,
                        idDataEvento
                );

        model.addAttribute("evento", evento);
        model.addAttribute("dataEventoDTO", dto);

        return "eventi/date/modifica"; // DA VERIFICARE: non visibile nello screenshot
    }


    @PostMapping("/{id}/date/{idDataEvento}")
    public String updateDataEvento(
            @PathVariable Long id,
            @PathVariable Long idDataEvento,
            @ModelAttribute DataEventoDTO dto,
            RedirectAttributes redirectAttributes) {

        eventoService.updateDataEvento(
                id,
                idDataEvento,
                dto
        );

        redirectAttributes.addFlashAttribute(
                "messaggio",
                "Data evento modificata con successo"
        );

        return "redirect:/eventi/" + id;
    }


    @PostMapping("/{id}/date/{idDataEvento}/elimina")
    public String deleteDataEvento(
            @PathVariable Long id,
            @PathVariable Long idDataEvento,
            RedirectAttributes redirectAttributes) {

        eventoService.deleteDataEvento(
                id,
                idDataEvento
        );

        redirectAttributes.addFlashAttribute(
                "messaggio",
                "Data evento eliminata con successo"
        );

        return "redirect:/eventi/" + id;
    }
}