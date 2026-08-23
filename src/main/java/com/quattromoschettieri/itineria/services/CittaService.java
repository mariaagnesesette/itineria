package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.CittaDTO;
import com.quattromoschettieri.itineria.converters.CittaConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.citta.Regione;
import com.quattromoschettieri.itineria.repository.CittaRepository;

import lombok.RequiredArgsConstructor;

// Create Read Update Delete
@Service
@RequiredArgsConstructor
public class CittaService {

    private final CittaRepository cittaRepository;
    private final CittaConverter cittaConverter;

    // CREATE
    public CittaDTO create(CittaDTO dto) {
        boolean giaEsistente = cittaRepository.findAll()
                .stream()
                .anyMatch(c -> c.getNome()
                        .equalsIgnoreCase(dto.getNome()));

        if (giaEsistente) {
            throw new IllegalArgumentException("esiste gia la citta: " + dto.getNome());
        }

        Citta citta = cittaConverter.toEntity(dto);
        Citta salvata = cittaRepository.save(citta);
        return cittaConverter.toDto(salvata);
    }

    // READ
    public Page<Citta> findByNome(String nome, Pageable pageable) {
        return cittaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Citta> findByRegione(Regione regione, Pageable pageable) {
        return cittaRepository.findByRegione(regione, pageable);
    }

    public Citta findById(Long id) {
        return cittaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Citta non trovata: " + id));
    }

    public CittaDTO findByIdDto(Long id) {
        Citta citta = cittaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Citta non trovata: " + id));
        return cittaConverter.toDto(citta);
    }

    public Page<Citta> findAll(Pageable pageable) {
        return cittaRepository.findAll(pageable);
    }

    // UPDATE
    public CittaDTO update(Long id, CittaDTO dto) {
        Citta citta = cittaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("citta non trovata: " + id));

        boolean giaEsistente = cittaRepository.findAll().stream()
                .anyMatch(b -> !b.getId().equals(id)
                        && b.getNome().equalsIgnoreCase(dto.getNome()));

        if (giaEsistente) {
            throw new IllegalArgumentException("esiste gia una città con nome: " + dto.getNome());
        }

        cittaConverter.updateEntity(citta, dto);
        Citta salvata = cittaRepository.save(citta);
        return cittaConverter.toDto(salvata);
    }

    // DELETE
    public void delete(Long id) {
        Citta citta = cittaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("citta non trovata: " + id));

        cittaRepository.delete(citta);
    }
}
