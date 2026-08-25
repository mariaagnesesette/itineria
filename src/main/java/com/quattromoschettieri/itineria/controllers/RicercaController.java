package com.quattromoschettieri.itineria.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.repository.eventoRepository.EventoRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RicercaController {

    private final LuogoInteresseRepository luogoInteresseRepository;
    private final EventoRepository eventoRepository;

    private static final Map<Tipo, String> BASE_PATH = Map.of(
            Tipo.MUSEO, "/musei/",
            Tipo.BIBLIOTECA, "/biblioteche/",
            Tipo.ZONA_VERDE, "/zoneVerdi/",
            Tipo.RISTORANTE, "/ristoranti/",
            Tipo.LOCALE, "/locali/"
    );

    private static final Map<Tipo, String> ICONA = Map.of(
            Tipo.MUSEO, "fa-landmark",
            Tipo.BIBLIOTECA, "fa-book",
            Tipo.ZONA_VERDE, "fa-tree",
            Tipo.RISTORANTE, "fa-utensils",
            Tipo.LOCALE, "fa-mug-hot"
    );

    private static final Map<Tipo, String> COLORE_ICONA = Map.of(
            Tipo.MUSEO, "category-card__icon--purple",
            Tipo.BIBLIOTECA, "category-card__icon--green",
            Tipo.ZONA_VERDE, "category-card__icon--forest",
            Tipo.RISTORANTE, "category-card__icon--yellow",
            Tipo.LOCALE, "category-card__icon--orange"
    );

    @GetMapping("/ricerca")
    public String ricerca(
            @RequestParam(name = "q", required = false) String q,
            Model model) {

        String query = q == null ? "" : q.trim();

        List<LuogoInteresse> luoghi = query.isEmpty()
                ? List.of()
                : luogoInteresseRepository.findByNomeContainingIgnoreCase(query);

        List<Evento> eventi = query.isEmpty()
                ? List.of()
                : eventoRepository.findByNomeContainingIgnoreCase(query);

        model.addAttribute("q", query);
        model.addAttribute("luoghi", luoghi);
        model.addAttribute("eventi", eventi);
        model.addAttribute("basePath", BASE_PATH);
        model.addAttribute("icona", ICONA);
        model.addAttribute("colore", COLORE_ICONA);
        model.addAttribute("totaleRisultati", luoghi.size() + eventi.size());

        return "ricerca";
    }
}
