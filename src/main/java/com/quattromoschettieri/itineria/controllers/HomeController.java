package com.quattromoschettieri.itineria.controllers;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.evento.ImmagineEvento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.ImmagineLuogo;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.services.EventoService;
import com.quattromoschettieri.itineria.services.ImmagineEventoService;
import com.quattromoschettieri.itineria.services.ImmagineLuogoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final EventoService eventoService;
    private final ImmagineEventoService immagineEventoService;
    private final LuogoInteresseRepository luogoInteresseRepository;
    private final ImmagineLuogoService immagineLuogoService;

    private static final int LUOGHI_PRINCIPALI_MAX = 8;

    private static final List<String> IMMAGINI_EVENTI_FALLBACK = List.of(
        "https://images.unsplash.com/photo-1511192336575-5a79af67a629?q=80&w=600",
        "https://images.unsplash.com/photo-1523240795612-9a054b0db644?q=80&w=600",
        "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?q=80&w=600",
        "https://images.unsplash.com/photo-1490750967868-88aa4486c946?q=80&w=600",
        "https://images.unsplash.com/photo-1465847899084-d164df4dedc6?q=80&w=600"
    );

    @GetMapping("/")
    public String index(Model model) {
        Page<Evento> eventiFuturi = eventoService.findEventiFuturi(PageRequest.of(0, 6));
        List<Evento> eventi = eventiFuturi.getContent();

        Map<Long, String> immaginiEventi = new HashMap<>();
        for (int i = 0; i < eventi.size(); i++) {
            Evento evento = eventi.get(i);
            List<ImmagineEvento> immagini = immagineEventoService.findByEventoId(evento.getId());

            String url = !immagini.isEmpty()
                    ? "/eventi/" + evento.getId() + "/immagini/" + immagini.get(0).getId() + "/file"
                    : IMMAGINI_EVENTI_FALLBACK.get(i % IMMAGINI_EVENTI_FALLBACK.size());

            immaginiEventi.put(evento.getId(), url);
        }

        model.addAttribute("eventi", eventi);
        model.addAttribute("immaginiEventi", immaginiEventi);

        List<LuogoInteresse> tuttiLuoghi = luogoInteresseRepository.findAll();

        Map<Long, String> immaginiLuoghi = new HashMap<>();
        for (LuogoInteresse luogo : tuttiLuoghi) {
            List<ImmagineLuogo> immagini = immagineLuogoService.findByLuogoId(luogo.getId());
            if (!immagini.isEmpty()) {
                immaginiLuoghi.put(luogo.getId(),
                        "/luoghi/" + luogo.getId() + "/immagini/" + immagini.get(0).getId() + "/file");
            }
        }

        List<LuogoInteresse> luoghiPrincipali = tuttiLuoghi.stream()
                .sorted(Comparator.comparing((LuogoInteresse luogo) -> !immaginiLuoghi.containsKey(luogo.getId())))
                .limit(LUOGHI_PRINCIPALI_MAX)
                .toList();

        model.addAttribute("luoghiPrincipali", luoghiPrincipali);
        model.addAttribute("immaginiLuoghi", immaginiLuoghi);

        return "index";
    }

        @GetMapping("/supporto")
    public String supporto() {
        return "footer/supporto";
    }

    @GetMapping("/faq")
    public String faq() {
        return "footer/faq";
    }

    @GetMapping("/contattaci")
    public String contattaci() {
        return "footer/contattaci";
    }

   @GetMapping("/luoghi")
public String luoghi() {
    return "luoghi_interesse/luoghi";
}

@GetMapping("/chi-siamo")
public String chiSiamo(){
    return "footer/chi-siamo";
}

@GetMapping("/privacy")
public String privacy(){
    return "footer/privacy";
}

@GetMapping("/termini")
public String termini(){
    return "footer/termini";
}


}