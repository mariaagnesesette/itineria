package com.quattromoschettieri.itineria.controllers;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quattromoschettieri.itineria.DTO.eventoDTO.EventoDTO;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.evento.PubblicoEvento;
import com.quattromoschettieri.itineria.entities.evento.TipologiaEvento;
import com.quattromoschettieri.itineria.services.EventoService;

@Controller
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // LISTA EVENTI FUTURI
    @GetMapping
    public String listEventi(Model model, Pageable pageable) {

        Page<Evento> risultati =
                eventoService.findEventiFuturi(pageable);

        model.addAttribute("eventi", risultati.getContent());
        model.addAttribute("risultati", risultati);

        model.addAttribute("eventoDTO", new EventoDTO());

        model.addAttribute(
                "tipologieEvento",
                TipologiaEvento.values()
        );

        model.addAttribute(
                "pubbliciEvento",
                PubblicoEvento.values()
        );

        return "luoghi_interesse/eventi";
    }


    // FILTRI
    @GetMapping("/filtri")
    public String filtraEventi(
            @ModelAttribute("eventoDTO") EventoDTO dto,

            @RequestParam(required = false)
            BigDecimal prezzoMinimo,

            @RequestParam(required = false)
            BigDecimal prezzoMassimo,

            Pageable pageable,

            Model model) {

        Page<Evento> risultati =
                eventoService.search(
                        dto,
                        prezzoMinimo,
                        prezzoMassimo,
                        pageable
                );

        model.addAttribute(
                "eventi",
                risultati.getContent()
        );

        model.addAttribute(
                "risultati",
                risultati
        );

        model.addAttribute(
                "tipologieEvento",
                TipologiaEvento.values()
        );

        model.addAttribute(
                "pubbliciEvento",
                PubblicoEvento.values()
        );

        model.addAttribute(
                "eventoDTO",
                dto
        );

        return "luoghi_interesse/eventi";
    }


    // EVENTI FUTURI
    @GetMapping("/futuri")
    public String eventiFuturi(
            Model model,
            Pageable pageable) {

        Page<Evento> risultati =
                eventoService.findEventiFuturi(pageable);

        preparaLista(model, risultati);

        return "luoghi_interesse/eventi";
    }


    // EVENTI IN CORSO
    @GetMapping("/in-corso")
    public String eventiInCorso(
            Model model,
            Pageable pageable) {

        Page<Evento> risultati =
                eventoService.findEventiInCorso(pageable);

        preparaLista(model, risultati);

        return "luoghi_interesse/eventi";
    }


    // EVENTI STORICI
    @GetMapping("/storici")
    public String eventiStorici(
            Model model,
            Pageable pageable) {

        Page<Evento> risultati =
                eventoService.findStoricoEventi(pageable);

        preparaLista(model, risultati);

        return "luoghi_interesse/eventi";
    }


    // DETTAGLIO
    @GetMapping("/{id}")
    public String dettaglioEvento(
            @PathVariable Long id,
            Model model) {

        Evento evento =
                eventoService.findById(id);

        model.addAttribute(
                "evento",
                evento
        );

        return "luoghi_interesse/luoghi_dettaglio/eventoDettaglio";
    }


    // FRAGMENT DETTAGLIO
    @GetMapping("/{id}/fragment")
    public String eventoFragment(
            @PathVariable Long id,
            Model model) {

        Evento evento =
                eventoService.findById(id);

        model.addAttribute(
                "evento",
                evento
        );

        return "luoghi_interesse/luoghi_dettaglio/eventoDettaglio :: eventoDetailFragment";
    }


    // METODO DI SUPPORTO
    private void preparaLista(
            Model model,
            Page<Evento> risultati) {

        model.addAttribute(
                "eventi",
                risultati.getContent()
        );

        model.addAttribute(
                "risultati",
                risultati
        );

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
    }
}