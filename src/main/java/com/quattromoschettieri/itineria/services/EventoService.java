package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.repository.EventoRepository.DataEventoRepository;
import com.quattromoschettieri.itineria.repository.EventoRepository.EventoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final DataEventoRepository dataEventoRepository;

    public Page<Evento> findByLuogoInteresse(
        LuogoInteresse luogoInteresse,
        Pageable pageable) {

    return eventoRepository.findByLuogoInteresse(luogoInteresse, pageable);
    }

    public Page<Evento> findByLuogoInteresseId(
            Long id,
            Pageable pageable) {

        return eventoRepository.findByLuogoInteresseId(id, pageable);
    }

    public Page<Evento> findByNome(
            String nome,
            Pageable pageable) {

        return eventoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Evento> findAll(Pageable pageable) {
    return eventoRepository.findAll(pageable);
    }

    public Evento findById(Long id) {
    return eventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento non trovato"));
    }

    public Page<Evento> findStoricoEventi(Pageable pageable) {
    return eventoRepository.findStoricoEventi(pageable);
    }

    public Page<Evento> findEventiFuturi(Pageable pageable) {
        return eventoRepository.findEventiFuturi(pageable);
    }

    public Page<Evento> findEventiInCorso(Pageable pageable) {
        return eventoRepository.findEventiInCorso(pageable);
    }

    public Evento save(Evento evento) {
    return eventoRepository.save(evento);
    }

    public Evento update(Evento evento) {
    findById(evento.getId());
    return eventoRepository.save(evento);
    }
    
    public void delete(Long id) {
    findById(id);
    eventoRepository.deleteById(id);
    }

}

