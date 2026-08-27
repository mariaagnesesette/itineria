package com.quattromoschettieri.itineria.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quattromoschettieri.itineria.DTO.eventoDTO.DataEventoDTO;
import com.quattromoschettieri.itineria.DTO.eventoDTO.EventoDTO;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.evento.ImmagineEvento;
import com.quattromoschettieri.itineria.entities.evento.PubblicoEvento;
import com.quattromoschettieri.itineria.entities.evento.TipologiaEvento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.services.EventoService;
import com.quattromoschettieri.itineria.services.ImmagineEventoService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/eventi")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    private final UtenteService utenteService;

    private final ImmagineEventoService immagineEventoService;

    private final LuogoInteresseRepository luogoInteresseRepository;


    // =========================
    // METODI DI SUPPORTO
    // =========================

    private void aggiungiFiltri(Model model) {

        model.addAttribute(
                "tipologieEvento",
                TipologiaEvento.values()
        );

        model.addAttribute(
                "pubbliciEvento",
                PubblicoEvento.values()
        );
    }


    private void aggiungiRisultati(
            Model model,
            Page<Evento> eventi) {

        model.addAttribute("eventi", eventi);
        model.addAttribute("risultati", eventi);
    }


    // =========================
    // VISUALIZZAZIONE EVENTI
    // =========================

    @GetMapping
    public String findAll(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi =
                eventoService.findAll(pageable);

        aggiungiRisultati(model, eventi);
        aggiungiFiltri(model);

        model.addAttribute(
                "eventoDTO",
                new EventoDTO()
        );

        return "luoghi_interesse/eventi";
    }


    // =========================
    // DETTAGLIO
    // =========================

    @GetMapping("/{id}")
    public String findById(
            @PathVariable Long id,
            @RequestParam(value = "gestione", required = false, defaultValue = "false") boolean gestione,
            Authentication authentication,
            Model model) {

        Evento evento =
                eventoService.findById(id);

        Utente utenteAutenticato = (authentication != null && authentication.isAuthenticated())
                ? utenteService.findByEmail(authentication.getName())
                : null;

        List<ImmagineEvento> immagini = immagineEventoService.findByEventoId(id);

        boolean puoModificare = gestione && puoModificare(evento, authentication);

        model.addAttribute("evento", evento);
        model.addAttribute("immagini", immagini);
        model.addAttribute("utenteId", utenteAutenticato != null ? utenteAutenticato.getId() : null);
        model.addAttribute("isPreferito", utenteAutenticato != null
                && utenteAutenticato.getEventiPreferiti().contains(evento));
        model.addAttribute("puoModificare", puoModificare);
        model.addAttribute("eventoDTO", eventoService.findDtoById(id));

        if (puoModificare) {
            model.addAttribute("luoghi", luoghiGestibili(authentication));
            aggiungiFiltri(model);
        }

        return "luoghi_interesse/luoghi_dettaglio/eventoDettaglio";
    }

    private boolean puoModificare(Evento evento, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (utente.getRuolo() == Ruolo.ADMIN) {
            return true;
        }

        return utente.getRuolo() == Ruolo.MANAGER
                && evento.getLuogoInteresse() != null
                && evento.getLuogoInteresse().getManager() != null
                && evento.getLuogoInteresse().getManager().getId().equals(utente.getId());
    }


    // =========================
    // EVENTI STORICI
    // =========================

    @GetMapping("/storici")
    public String findStoricoEventi(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi =
                eventoService.findStoricoEventi(pageable);

        aggiungiRisultati(model, eventi);
        aggiungiFiltri(model);

        model.addAttribute(
                "eventoDTO",
                new EventoDTO()
        );

        return "luoghi_interesse/eventi";
    }


    // =========================
    // EVENTI FUTURI
    // =========================

    @GetMapping("/futuri")
    public String findEventiFuturi(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi =
                eventoService.findEventiFuturi(pageable);

        aggiungiRisultati(model, eventi);
        aggiungiFiltri(model);

        model.addAttribute(
                "eventoDTO",
                new EventoDTO()
        );

        return "luoghi_interesse/eventi";
    }


    // =========================
    // EVENTI IN CORSO
    // =========================

    @GetMapping("/in-corso")
    public String findEventiInCorso(
            Pageable pageable,
            Model model) {

        Page<Evento> eventi =
                eventoService.findEventiInCorso(pageable);

        aggiungiRisultati(model, eventi);
        aggiungiFiltri(model);

        model.addAttribute(
                "eventoDTO",
                new EventoDTO()
        );

        return "luoghi_interesse/eventi";
    }


    // =========================
    // RICERCA / FILTRI
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

        Page<Evento> eventi =
                eventoService.search(
                        dto,
                        prezzoMinimo,
                        prezzoMassimo,
                        pageable
                );

        aggiungiRisultati(model, eventi);
        aggiungiFiltri(model);

        model.addAttribute("eventoDTO", dto);
        model.addAttribute("prezzoMinimo", prezzoMinimo);
        model.addAttribute("prezzoMassimo", prezzoMassimo);

        return "luoghi_interesse/eventi";
    }


    // =========================
    // CREAZIONE EVENTO
    // =========================

    @GetMapping("/nuovo")
    public String nuovoEvento(
            Authentication authentication,
            Model model) {

        EventoDTO dto = new EventoDTO();
        dto.setPrezzo(BigDecimal.ZERO);

        DataEventoDTO dataIniziale = new DataEventoDTO();
        List<DataEventoDTO> dateEvento = new ArrayList<>();
        dateEvento.add(dataIniziale);
        dto.setDateEvento(dateEvento);

        model.addAttribute(
                "eventoDTO",
                dto
        );

        model.addAttribute(
                "luoghi",
                luoghiGestibili(authentication)
        );

        aggiungiFiltri(model);

        return "eventi/nuovo";
    }


    private List<LuogoInteresse> luoghiGestibili(Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (utente.getRuolo() == Ruolo.ADMIN) {
            return luogoInteresseRepository.findAll();
        }

        return luogoInteresseRepository.findByManagerId(utente.getId());
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

        return "redirect:/utente#eventi-gestiti";
    }


    // =========================
    // MODIFICA EVENTO
    // =========================

    // La modifica avviene direttamente nella pagina di dettaglio dell'evento
    @GetMapping("/{id}/modifica")
    public String modificaEvento(@PathVariable Long id) {
        return "redirect:/eventi/" + id + "?gestione=true";
    }


    @PutMapping("/{id}/modifica")
    public String update(
            @PathVariable Long id,
            @ModelAttribute EventoDTO dto,
            RedirectAttributes redirectAttributes) {

        try {
            eventoService.update(id, dto);
            redirectAttributes.addFlashAttribute(
                    "messaggio",
                    "Evento modificato con successo"
            );
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(
                    "erroreModifica",
                    "Controlla i dati inseriti: alcuni campi non sono validi."
            );
        }

        return "redirect:/eventi/" + id + "?gestione=true";
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

        return "redirect:/utente#eventi-gestiti";
    }


    // =========================
    // GESTIONE DATE
    // =========================

    @GetMapping("/{id}/date/nuova")
    public String nuovaDataEvento(
            @PathVariable Long id,
            Model model) {

        Evento evento =
                eventoService.findById(id);

        model.addAttribute(
                "evento",
                evento
        );

        model.addAttribute(
                "dataEventoDTO",
                new DataEventoDTO()
        );

        return "eventi/date/nuova";
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

        return "redirect:/eventi/" + id + "?gestione=true";
    }


    @GetMapping("/{id}/date/{idDataEvento}/modifica")
    public String modificaDataEvento(
            @PathVariable Long id,
            @PathVariable Long idDataEvento,
            Model model) {

        Evento evento =
                eventoService.findById(id);

        DataEventoDTO dto =
                eventoService.findDataEventoById(
                        id,
                        idDataEvento
                );

        model.addAttribute(
                "evento",
                evento
        );

        model.addAttribute(
                "dataEventoDTO",
                dto
        );

        return "eventi/date/modifica";
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

        return "redirect:/eventi/" + id + "?gestione=true";
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

        return "redirect:/eventi/" + id + "?gestione=true";
    }
}