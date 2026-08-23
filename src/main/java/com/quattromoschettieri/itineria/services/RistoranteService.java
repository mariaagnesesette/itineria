package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.RistoranteDTO;
import com.quattromoschettieri.itineria.converters.RistoranteConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.repository.RistoranteRepository;
import com.quattromoschettieri.itineria.specification.RistoranteSpecification;

import lombok.RequiredArgsConstructor;

//CRUD = CREATE, READ, UPDATE, DELETE
@Service
@RequiredArgsConstructor
public class RistoranteService {

    private final RistoranteRepository ristoranteRepository;
    private final CittaRepository cittaRepository;
    private final RistoranteConverter ristoranteConverter;

    // CREATE
    public RistoranteDTO create(RistoranteDTO dto) {

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException("Città non trovata: " + dto.getIdCitta()));

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
        Ristorante salvato = ristoranteRepository.save(ristorante);
        return ristoranteConverter.toDto(salvato);

    }

    // READ
    public Page<Ristorante> findAll(Pageable pageable) {
        return ristoranteRepository.findAll(pageable);
    }

    public Ristorante findById(Long id) {
        return ristoranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ristorante non trovato: " + id));
    }

    public RistoranteDTO findByDto(Long id) {
        Ristorante ristorante = ristoranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ristorante non trovato: " + id));

        return ristoranteConverter.toDto(ristorante);
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

    //UPDATE
    public RistoranteDTO update(Long id, RistoranteDTO dto){
        Ristorante ristorante = ristoranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ristorante non trovato: " + id));

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException("Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = ristoranteRepository.findAll().stream()
                .anyMatch(b -> !b.getId().equals(id)
                        && b.getNome().equalsIgnoreCase(dto.getNome())
                        && b.getCitta() != null
                        && b.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un'altro ristorante con nome " + dto.getNome() + " nella città di "
                            + citta.getNome());
        }

        ristoranteConverter.updateEntity(ristorante, dto, citta);
        Ristorante salvato = ristoranteRepository.save(ristorante);
        return ristoranteConverter.toDto(salvato);
    }

    //DELETE
    public void delete(Long id) {

        Ristorante ristorante = ristoranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ristorante non trovato: " + id));

        ristoranteRepository.delete(ristorante);
    }
}
