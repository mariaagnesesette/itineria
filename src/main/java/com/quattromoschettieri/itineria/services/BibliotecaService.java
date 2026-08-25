package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.converters.BibliotecaConverter;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.BibliotecaRepository;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.specification.BibliotecaSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final CittaRepository cittaRepository;
    private final BibliotecaConverter bibliotecaConverter;

    // =====================================================
    // CREATE
    // =====================================================

    public BibliotecaDTO create(BibliotecaDTO dto, Utente utente) {

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = bibliotecaRepository.findAll().stream()
                .anyMatch(b -> b.getNome().equalsIgnoreCase(dto.getNome())
                        && b.getCitta() != null
                        && b.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già una biblioteca con nome "
                            + dto.getNome()
                            + " nella città di "
                            + citta.getNome());
        }

        Biblioteca biblioteca = bibliotecaConverter.toEntity(dto, citta);

        // Il manager che crea il luogo diventa automaticamente il manager
        // del luogo. L'ADMIN può creare il luogo senza problemi.
        biblioteca.setManager(utente);

        Biblioteca salvata = bibliotecaRepository.save(biblioteca);

        return bibliotecaConverter.toDto(salvata);
    }

    // =====================================================
    // READ
    // =====================================================

    public Page<Biblioteca> findAll(Pageable pageable) {
        return bibliotecaRepository.findAll(pageable);
    }

    public Biblioteca findById(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Biblioteca non trovata: " + id));
    }

    public BibliotecaDTO findByIdDto(Long id) {
        return bibliotecaConverter.toDto(findById(id));
    }

    public Page<Biblioteca> findByNome(
            String nome,
            Pageable pageable) {

        return bibliotecaRepository
                .findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Biblioteca> search(
            BibliotecaDTO bibliotecaDTO,
            Pageable pageable) {

        Specification<Biblioteca> spec = Specification
                .where(BibliotecaSpecification.nomeContains(
                        bibliotecaDTO.getNome()))
                .and(BibliotecaSpecification.isPubblico(
                        bibliotecaDTO.getPubblico()))
                .and(BibliotecaSpecification.hasWifi(
                        bibliotecaDTO.getWifi()))
                .and(BibliotecaSpecification.hasAreaComputer(
                        bibliotecaDTO.getAreaComputer()))
                .and(BibliotecaSpecification.hasAreaBambini(
                        bibliotecaDTO.getAreaBambini()))
                .and(BibliotecaSpecification.hasAccessibilita(
                        bibliotecaDTO.getAccessibilita()))
                .and(BibliotecaSpecification.isSempreAperto(
                        bibliotecaDTO.getSempreAperto()))
                .and(BibliotecaSpecification.inCitta(
                        bibliotecaDTO.getIdCitta()));

        return bibliotecaRepository.findAll(spec, pageable);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public BibliotecaDTO update(
            Long id,
            BibliotecaDTO dto,
            Utente utente) {

        Biblioteca biblioteca = findById(id);

        verificaPermesso(biblioteca, utente);

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = bibliotecaRepository.findAll().stream()
                .anyMatch(b -> !b.getId().equals(id)
                        && b.getNome().equalsIgnoreCase(dto.getNome())
                        && b.getCitta() != null
                        && b.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un'altra biblioteca con nome "
                            + dto.getNome()
                            + " nella città di "
                            + citta.getNome());
        }

        bibliotecaConverter.updateEntity(
                biblioteca,
                dto,
                citta);

        Biblioteca salvata = bibliotecaRepository.save(biblioteca);

        return bibliotecaConverter.toDto(salvata);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void delete(Long id, Utente utente) {

        Biblioteca biblioteca = findById(id);

        verificaPermesso(biblioteca, utente);

        bibliotecaRepository.delete(biblioteca);
    }

    // =====================================================
    // CONTROLLO PERMESSI
    // =====================================================

    private void verificaPermesso(
            Biblioteca biblioteca,
            Utente utente) {

        // ADMIN può modificare/eliminare qualsiasi biblioteca
        if (utente.getRuolo() == Ruolo.ADMIN) {
            return;
        }

        // MANAGER può modificare/eliminare solamente
        // le biblioteche di cui è proprietario
        if (utente.getRuolo() == Ruolo.MANAGER
                && biblioteca.getManager() != null
                && biblioteca.getManager().getId().equals(utente.getId())) {
            return;
        }

        throw new SecurityException(
                "Non hai i permessi per modificare questa biblioteca");
    }

    public void assegnaManager(Long bibliotecaId, Utente manager) {

        Biblioteca biblioteca = findById(bibliotecaId);

        if (manager.getRuolo() != Ruolo.MANAGER) {
                throw new IllegalArgumentException(
                        "L'utente deve avere ruolo MANAGER");
        }

        biblioteca.setManager(manager);

        bibliotecaRepository.save(biblioteca);
        }
}