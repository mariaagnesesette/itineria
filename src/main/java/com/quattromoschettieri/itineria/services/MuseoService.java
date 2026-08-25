package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.MuseoDTO;
import com.quattromoschettieri.itineria.converters.MuseoConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.museo.Museo;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.repository.MuseoRepository;
import com.quattromoschettieri.itineria.specification.MuseoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MuseoService {

    private final MuseoRepository museoRepository;
    private final CittaRepository cittaRepository;
    private final MuseoConverter museoConverter;

    // =====================================================
    // CREATE
    // =====================================================

    public MuseoDTO create(MuseoDTO dto, Utente manager) {

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = museoRepository.findAll().stream()
                .anyMatch(m -> m.getNome().equalsIgnoreCase(dto.getNome())
                        && m.getCitta() != null
                        && m.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un museo con nome "
                            + dto.getNome()
                            + " nella città di "
                            + citta.getNome());
        }

        Museo museo = museoConverter.toEntity(dto, citta);

        /*
         * Il manager viene assegnato dal backend.
         * Il frontend non può scegliere liberamente il manager.
         */
        museo.setManager(manager);

        Museo salvato = museoRepository.save(museo);

        return museoConverter.toDto(salvato);
    }

    // =====================================================
    // READ
    // =====================================================

    public Page<Museo> findAll(Pageable pageable) {
        return museoRepository.findAll(pageable);
    }

    public Museo findById(Long id) {
        return museoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Museo non trovato: " + id));
    }

    public MuseoDTO findByIdDto(Long id) {
        Museo museo = findById(id);

        return museoConverter.toDto(museo);
    }

    public Page<Museo> findByNome(
            String nome,
            Pageable pageable) {

        return museoRepository
                .findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Museo> search(
            MuseoDTO museoDTO,
            Pageable pageable) {

        Specification<Museo> spec = Specification
                .where(MuseoSpecification.nomeContains(
                        museoDTO.getNome()))
                .and(MuseoSpecification.tipologia(
                        museoDTO.getTipologia()))
                .and(MuseoSpecification.hasGuidaPrenotabile(
                        museoDTO.getGuidaPrenotabile()))
                .and(MuseoSpecification.hasBarInterno(
                        museoDTO.getBarInterno()))
                .and(MuseoSpecification.fasciaPrezzo(
                        museoDTO.getPrezzoMin(),
                        museoDTO.getPrezzoMax()))
                .and(MuseoSpecification.hasAccessibilita(
                        museoDTO.getAccessibilita()))
                .and(MuseoSpecification.isSempreAperto(
                        museoDTO.getSempreAperto()))
                .and(MuseoSpecification.inCitta(
                        museoDTO.getIdCitta()));

        return museoRepository.findAll(spec, pageable);
    }

    // =====================================================
    // CONTROLLO AUTORIZZAZIONE
    // =====================================================

    private void verificaPermesso(Museo museo, Utente utente) {

        /*
         * ADMIN può modificare qualsiasi museo.
         */
        if (utente.getRuolo() == Ruolo.ADMIN) {
            return;
        }

        /*
         * MANAGER può modificare solamente
         * i musei assegnati a lui.
         */
        if (utente.getRuolo() == Ruolo.MANAGER) {

            if (museo.getManager() != null
                    && museo.getManager().getId().equals(utente.getId())) {
                return;
            }
        }

        throw new SecurityException(
                "Non hai i permessi per modificare questo museo");
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public MuseoDTO update(
            Long id,
            MuseoDTO dto,
            Utente utente) {

        Museo museo = findById(id);

        /*
         * Prima controlliamo il proprietario.
         */
        verificaPermesso(museo, utente);

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Città non trovata: "
                                        + dto.getIdCitta()));

        boolean giaEsistente = museoRepository.findAll().stream()
                .anyMatch(m -> !m.getId().equals(id)
                        && m.getNome().equalsIgnoreCase(dto.getNome())
                        && m.getCitta() != null
                        && m.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un altro museo con nome "
                            + dto.getNome()
                            + " nella città di "
                            + citta.getNome());
        }

        museoConverter.updateEntity(museo, dto, citta);

        /*
         * Non tocchiamo il manager durante l'update.
         *
         * Un manager non deve poter trasferire il museo
         * a un altro manager.
         */
        Museo salvato = museoRepository.save(museo);

        return museoConverter.toDto(salvato);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void delete(Long id, Utente utente) {

        Museo museo = findById(id);

        /*
         * ADMIN: qualsiasi museo.
         * MANAGER: solamente i propri.
         */
        verificaPermesso(museo, utente);

        museoRepository.delete(museo);
    }

    public void assegnaManager(Long museoId, Utente manager) {

        Museo museo = findById(museoId);

        if (manager.getRuolo() != Ruolo.MANAGER) {
                throw new IllegalArgumentException(
                        "L'utente deve avere ruolo MANAGER");
        }

        museo.setManager(manager);

        museoRepository.save(museo);
        }
}