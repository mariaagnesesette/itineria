package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.MuseoDTO;
import com.quattromoschettieri.itineria.converters.MuseoConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.museo.Museo;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.repository.MuseoRepository;
import com.quattromoschettieri.itineria.specification.MuseoSpecification;

import lombok.RequiredArgsConstructor;

//CRUD = CREATE, READ, UPDATE, DELETE
@Service
@RequiredArgsConstructor
public class MuseoService {

    private final MuseoRepository museoRepository;
    private final CittaRepository cittaRepository;
    private final MuseoConverter museoConverter;


    // CREATE
    public MuseoDTO create(MuseoDTO dto) {

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException("Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = museoRepository.findAll().stream()
                .anyMatch(m -> m.getNome().equalsIgnoreCase(dto.getNome())
                        && m.getCitta() != null
                        && m.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un museo con nome " + dto.getNome() + " nella città di " + citta.getNome());
        }

        Museo museo = museoConverter.toEntity(dto, citta);
        Museo salvato = museoRepository.save(museo);
        return museoConverter.toDto(salvato);
    }

    // READ
    public Page<Museo> findAll(Pageable pageable) {
        return museoRepository.findAll(pageable);
    }

    public Museo findById(Long id) {
        return museoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Museo non trovato: " + id));
    }

    public MuseoDTO findByIdDto(Long id) {
        Museo museo = museoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Museo non trovato: " + id));

        return museoConverter.toDto(museo);
    }

    public Page<Museo> findByNome(String nome, Pageable pageable) {
        return museoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Museo> search(MuseoDTO museoDTO, Pageable pageable) {
        Specification<Museo> spec = Specification
                .where(MuseoSpecification.nomeContains(museoDTO.getNome()))
                .and(MuseoSpecification.tipologia(museoDTO.getTipologia()))
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

    // UPDATE
    public MuseoDTO update(Long id, MuseoDTO dto) {
        Museo museo = museoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Museo non trovato: " + id));

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException("Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = museoRepository.findAll().stream()
                .anyMatch(b -> !b.getId().equals(id)
                        && b.getNome().equalsIgnoreCase(dto.getNome())
                        && b.getCitta() != null
                        && b.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un'altro museo con nome " + dto.getNome() + " nella città di "
                            + citta.getNome());
        }

        museoConverter.updateEntity(museo, dto, citta);
        Museo salvato = museoRepository.save(museo);
        return museoConverter.toDto(salvato);
    }

    // DELETE
    public void delete(Long id) {

        Museo museo = museoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Museo non trovato: " + id));

        museoRepository.delete(museo);
    }
}
