package com.quattromoschettieri.itineria.services.utenteService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.utenteDTO.UtenteDTO;
import com.quattromoschettieri.itineria.converters.utenteConverter.UtenteConverter;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.utenteRepository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;

    private final UtenteConverter utenteConverter;

    public void updatePassword(Long id, String vecchiaPassword, String nuovaPassword) {
        Utente utente = findById(id);

        utenteConverter.updatePassword(utente, vecchiaPassword, nuovaPassword);

        utenteRepository.save(utente);
    }

    public Page<Utente> findAll(Pageable pageable) {
        return utenteRepository.findAll(pageable);
    }

    public Utente findById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    public Page<Utente> findByNome(String nome, Pageable pageable) {
        return utenteRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Utente> findByCognome(String cognome, Pageable pageable) {
        return utenteRepository.findByCognomeContainingIgnoreCase(cognome, pageable);
    }

    public Page<Utente> findByRuolo(Ruolo ruolo, Pageable pageable) {
        return utenteRepository.findByRuolo(ruolo, pageable);
    }

    public UtenteDTO save(UtenteDTO dto) {
        Utente utente = utenteConverter.toEntity(dto);
        Utente salvato = utenteRepository.save(utente);

        return utenteConverter.toDto(salvato);
    }

    public UtenteDTO update(Long id, UtenteDTO dto) {
        Utente utente = findById(id);
        utenteConverter.updateEntity(utente, dto);
        Utente aggiornato = utenteRepository.save(utente);

        return utenteConverter.toDto(aggiornato);
    }

    public void delete(Long id) {
        Utente utente = findById(id);

        utenteRepository.delete(utente);
    }
}