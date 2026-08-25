package com.quattromoschettieri.itineria.services.utenteService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.utenteDTO.UtenteDTO;
import com.quattromoschettieri.itineria.converters.utenteConverter.UtenteConverter;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.utenteRepository.UtenteRepository;
import com.quattromoschettieri.itineria.services.documentoService.DocumentoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final UtenteConverter utenteConverter;
    private final DocumentoService documentoService;


    // =====================================================
    // READ
    // =====================================================

    public Page<Utente> findAll(Pageable pageable) {
        return utenteRepository.findAll(pageable);
    }

    public Utente findById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Utente non trovato"));
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Utente non trovato"));
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Utente non trovato"));
    }

    public Page<Utente> findByNome(
            String nome,
            Pageable pageable) {

        return utenteRepository
                .findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Utente> findByCognome(
            String cognome,
            Pageable pageable) {

        return utenteRepository
                .findByCognomeContainingIgnoreCase(cognome, pageable);
    }

    public Page<Utente> findByRuolo(
            Ruolo ruolo,
            Pageable pageable) {

        return utenteRepository.findByRuolo(ruolo, pageable);
    }


    // =====================================================
    // REGISTRAZIONE
    // =====================================================

    public UtenteDTO save(UtenteDTO dto) {

        Utente utente =
                utenteConverter.toEntity(dto);

        Utente salvato =
                utenteRepository.save(utente);

        return utenteConverter.toDto(salvato);
    }


    // =====================================================
    // UPDATE
    // =====================================================

    public UtenteDTO update(
            Long id,
            UtenteDTO dto) {

        Utente utente = findById(id);

        utenteConverter.updateEntity(
                utente,
                dto
        );

        Utente aggiornato =
                utenteRepository.save(utente);

        return utenteConverter.toDto(aggiornato);
    }


    // =====================================================
    // CAMBIO PASSWORD
    // =====================================================

    public void updatePassword(
            Long id,
            String vecchiaPassword,
            String nuovaPassword) {

        Utente utente = findById(id);

        utenteConverter.updatePassword(
                utente,
                vecchiaPassword,
                nuovaPassword
        );

        utenteRepository.save(utente);
    }


    // =====================================================
    // DELETE
    // =====================================================

    public void delete(Long id) {

        Utente utente = findById(id);

        // Prima eliminiamo i documenti dell'utente
        documentoService.deleteAllByUtente(utente);

        // Poi eliminiamo l'utente
        utenteRepository.delete(utente);
    }

    public void assegnaRuoloManager(Long utenteId) {

        if (!utenteRepository.existsById(utenteId)) {
            throw new RuntimeException("Utente non trovato");
        }
        if (utenteRepository.findById(utenteId).get().getRuolo() != Ruolo.USER) {
            throw new RuntimeException("Solo gli utenti con ruolo USER possono essere promossi a MANAGER");
        }

        Utente utente = findById(utenteId);
        utente.setRuolo(Ruolo.MANAGER);
        utenteRepository.save(utente);
    }
}