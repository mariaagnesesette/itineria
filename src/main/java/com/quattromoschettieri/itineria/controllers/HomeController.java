package com.quattromoschettieri.itineria.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.services.EventoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final EventoService eventoService;

    private static final List<String> IMMAGINI_EVENTI = List.of(
        "https://images.unsplash.com/photo-1511192336575-5a79af67a629?q=80&w=600",
        "https://images.unsplash.com/photo-1523240795612-9a054b0db644?q=80&w=600",
        "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?q=80&w=600",
        "https://images.unsplash.com/photo-1490750967868-88aa4486c946?q=80&w=600",
        "https://images.unsplash.com/photo-1465847899084-d164df4dedc6?q=80&w=600"
    );

    @GetMapping("/")
    public String index(Model model) {
        Page<Evento> eventiFuturi = eventoService.findEventiFuturi(PageRequest.of(0, 6));

        model.addAttribute("eventi", eventiFuturi.getContent());
        model.addAttribute("immaginiEventi", IMMAGINI_EVENTI);

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