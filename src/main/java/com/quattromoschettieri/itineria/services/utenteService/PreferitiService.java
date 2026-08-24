package com.quattromoschettieri.itineria.services.utenteService;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.repository.eventoRepository.EventoRepository;
import com.quattromoschettieri.itineria.repository.utenteRepository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreferitiService {

    private final UtenteRepository utenteRepository;
    private final LuogoInteresseRepository luogoInteresseRepository;
    private final EventoRepository eventoRepository;

    public Set<LuogoInteresse> findLuoghiPreferiti(Long utenteId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        return utente.getLuoghiPreferiti();
    }

    public Set<Evento> findEventiPreferiti(Long utenteId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        return utente.getEventiPreferiti();
    }

    public void aggiungiLuogoPreferito(Long utenteId, Long luogoId) {

        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        LuogoInteresse luogo = luogoInteresseRepository.findById(luogoId)
                .orElseThrow(() -> new RuntimeException("Luogo non trovato"));

        utente.getLuoghiPreferiti().add(luogo);

        utenteRepository.save(utente);
    }

    public void rimuoviLuogoPreferito(Long utenteId, Long luogoId) {

        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        LuogoInteresse luogo = luogoInteresseRepository.findById(luogoId)
                .orElseThrow(() -> new RuntimeException("Luogo non trovato"));

        utente.getLuoghiPreferiti().remove(luogo);

        utenteRepository.save(utente);
    }

    public void aggiungiEventoPreferito(Long utenteId, Long eventoId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        utente.getEventiPreferiti().add(evento);

        utenteRepository.save(utente);
    }

    public void rimuoviEventoPreferito(Long utenteId, Long eventoId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        utente.getEventiPreferiti().remove(evento);

        utenteRepository.save(utente);
    }
}
