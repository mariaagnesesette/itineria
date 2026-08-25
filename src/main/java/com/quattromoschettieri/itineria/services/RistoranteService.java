package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.RistoranteDTO;
import com.quattromoschettieri.itineria.converters.RistoranteConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.repository.RistoranteRepository;
import com.quattromoschettieri.itineria.specification.RistoranteSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RistoranteService {

    private final RistoranteRepository ristoranteRepository;
    private final CittaRepository cittaRepository;
    private final RistoranteConverter ristoranteConverter;

    // CREATE
    public RistoranteDTO create(RistoranteDTO dto, Utente manager) {

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = ristoranteRepository.findAll().stream()
                .anyMatch(r -> r.getNome().equalsIgnoreCase(dto.getNome())
                        && r.getCitta() != null
                        && r.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un ristorante con nome " + dto.getNome()
                            + " nella città di " + citta.getNome());
        }

        Ristorante ristorante = ristoranteConverter.toEntity(dto, citta);

        ristorante.setManager(manager);

        Ristorante salvato = ristoranteRepository.save(ristorante);

        return ristoranteConverter.toDto(salvato);
    }

    // READ
    public Page<Ristorante> findAll(Pageable pageable) {
        return ristoranteRepository.findAll(pageable);
    }

    public Ristorante findById(Long id) {
        return ristoranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Ristorante non trovato: " + id));
    }

    public RistoranteDTO findByIdDto(Long id) {
        return ristoranteConverter.toDto(findById(id));
    }

    public Page<Ristorante> findByNome(String nome, Pageable pageable) {
        return ristoranteRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Ristorante> search(
            RistoranteDTO ristoranteDTO,
            Pageable pageable) {

        Specification<Ristorante> spec = Specification
                .where(RistoranteSpecification.nomeContains(
                        ristoranteDTO.getNome()))
                .and(RistoranteSpecification.tipoCucina(
                        ristoranteDTO.getTipoCucina()))
                .and(RistoranteSpecification.fasciaPrezzo(
                        ristoranteDTO.getFasciaPrezzo()))
                .and(RistoranteSpecification.isDogFriendly(
                        ristoranteDTO.getDogFriendly()))
                .and(RistoranteSpecification.isPerCeliaci(
                        ristoranteDTO.getPerCeliaci()))
                .and(RistoranteSpecification.hasPostiEsterni(
                        ristoranteDTO.getPostiEsterni()))
                .and(RistoranteSpecification.hasAccessibilita(
                        ristoranteDTO.getAccessibilita()))
                .and(RistoranteSpecification.isSempreAperto(
                        ristoranteDTO.getSempreAperto()))
                .and(RistoranteSpecification.inCitta(
                        ristoranteDTO.getIdCitta()));

        return ristoranteRepository.findAll(spec, pageable);
    }

    // UPDATE
    public RistoranteDTO update(
            Long id,
            RistoranteDTO dto,
            Utente manager) {

        Ristorante ristorante = findById(id);

        verificaPermesso(ristorante, manager);

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = ristoranteRepository.findAll().stream()
                .anyMatch(r -> !r.getId().equals(id)
                        && r.getNome().equalsIgnoreCase(dto.getNome())
                        && r.getCitta() != null
                        && r.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un altro ristorante con nome " + dto.getNome()
                            + " nella città di " + citta.getNome());
        }

        ristoranteConverter.updateEntity(ristorante, dto, citta);

        Ristorante salvato = ristoranteRepository.save(ristorante);

        return ristoranteConverter.toDto(salvato);
    }

    // DELETE
    public void delete(Long id, Utente manager) {

        Ristorante ristorante = findById(id);

        verificaPermesso(ristorante, manager);

        ristoranteRepository.delete(ristorante);
    }

    // CONTROLLO PERMESSI
    private void verificaPermesso(Ristorante ristorante, Utente manager) {

        if (manager.getRuolo() == Ruolo.ADMIN) {
            return;
        }

        if (manager.getRuolo() == Ruolo.MANAGER
                && ristorante.getManager() != null
                && ristorante.getManager().getId().equals(manager.getId())) {
            return;
        }

        throw new SecurityException(
                "Non hai i permessi per modificare questo ristorante");
    }

    public void assegnaManager(Long ristoranteId, Utente manager) {

        Ristorante ristorante = findById(ristoranteId);

        if (manager.getRuolo() != Ruolo.MANAGER) {
                throw new IllegalArgumentException(
                        "L'utente deve avere ruolo MANAGER");
        }

        ristorante.setManager(manager);

        ristoranteRepository.save(ristorante);
        }
}
