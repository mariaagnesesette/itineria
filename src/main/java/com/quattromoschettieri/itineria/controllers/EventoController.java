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

        return "eventi/eventi";
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

        return "eventi/dettaglio";
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

        return "eventi/storico";
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

        return "eventi/futuri";
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

        return "eventi/in-corso";
    }


    // =========================
    // RICERCA EVENTI
    // =========================

    /*
     * Ricerca gli eventi utilizzando i filtri
     * presenti nel form.
     *
     * EventoDTO contiene già i campi utilizzati
     * per la ricerca.
     *
     * Prezzo minimo e massimo vengono invece
     * passati separatamente perché rappresentano
     * i limiti della ricerca e non fanno parte
     * dell'EventoDTO.
     */
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

        /*
         * Manteniamo il DTO nel Model per permettere
         * al form di mantenere i valori inseriti
         * dall'utente.
         */
        model.addAttribute("eventoDTO", dto);

        return "eventi/eventi";
    }


    // =========================
    // CREAZIONE EVENTO
    // =========================

    /*
     * Mostra il form per creare un nuovo evento.
     */
    @GetMapping("/nuovo")
    public String nuovoEvento(Model model) {

        /*
         * Il form viene associato ad un DTO vuoto.
         */
        model.addAttribute(
                "eventoDTO",
                new EventoDTO()
        );

        /*
         * Passiamo gli enum al Model affinché
         * Thymeleaf possa utilizzarli per creare
         * le select del form.
         */
        model.addAttribute(
                "tipologieEvento",
                TipologiaEvento.values()
        );

        model.addAttribute(
                "pubbliciEvento",
                PubblicoEvento.values()
        );

        return "eventi/nuovo";
    }


    /*
     * Salva un nuovo evento.
     */
    @PostMapping
    public String save(
            @ModelAttribute EventoDTO dto,
            RedirectAttributes redirectAttributes) {

        /*
         * Il service si occupa della conversione
         * DTO -> Entity e del salvataggio.
         */
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

    /*
     * Mostra il form per modificare un evento esistente.
     *
     * Il service recupera l'evento e lo converte
     * direttamente in EventoDTO.
     */
    @GetMapping("/{id}/modifica")
    public String modificaEvento(
            @PathVariable Long id,
            Model model) {

        EventoDTO dto = eventoService.findDtoById(id);

        model.addAttribute("eventoDTO", dto);

        /*
         * Gli enum servono al form per costruire
         * le relative select.
         */
        model.addAttribute(
                "tipologieEvento",
                TipologiaEvento.values()
        );

        model.addAttribute(
                "pubbliciEvento",
                PubblicoEvento.values()
        );

        return "eventi/modifica";
    }


    /*
     * Aggiorna un evento esistente.
     */
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

    /*
     * Elimina un evento.
     *
     * Utilizziamo POST perché l'operazione modifica
     * lo stato del database.
     */
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

    /*
     * Mostra il form per aggiungere una nuova data
     * ad un evento.
     */
    @GetMapping("/{id}/date/nuova")
    public String nuovaDataEvento(
            @PathVariable Long id,
            Model model) {

        /*
         * findById verifica che l'evento esista.
         */
        Evento evento = eventoService.findById(id);

        model.addAttribute("evento", evento);

        model.addAttribute(
                "dataEventoDTO",
                new DataEventoDTO()
        );

        return "eventi/date/nuova";
    }


    /*
     * Aggiunge una nuova data all'evento.
     */
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


    /*
     * Mostra il form per modificare una data
     * appartenente all'evento.
     */
    @GetMapping("/{id}/date/{idDataEvento}/modifica")
    public String modificaDataEvento(
            @PathVariable Long id,
            @PathVariable Long idDataEvento,
            Model model) {

        /*
         * Recuperiamo l'evento per mostrarne
         * eventualmente le informazioni nella pagina.
         */
        Evento evento = eventoService.findById(id);

        /*
         * Il service verifica che la data esista
         * e che appartenga effettivamente all'evento.
         *
         * La data viene restituita direttamente
         * come DataEventoDTO per il form.
         */
        DataEventoDTO dto =
                eventoService.findDataEventoById(
                        id,
                        idDataEvento
                );

        model.addAttribute("evento", evento);
        model.addAttribute("dataEventoDTO", dto);

        return "eventi/date/modifica";
    }


    /*
     * Aggiorna una data dell'evento.
     */
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


    /*
     * Elimina una data appartenente all'evento.
     *
     * Utilizziamo POST perché l'operazione modifica
     * il database.
     */
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