package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.repository.BibliotecaRepository;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.specification.BibliotecaSpecification;

import lombok.RequiredArgsConstructor;

// CRUD = Create, Read, Update, Delete
@Service
@RequiredArgsConstructor
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final CittaRepository cittaRepository;

    // trasformazione di un dto in entity
    private Biblioteca toEntity(BibliotecaDTO dto, Citta citta) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNome(dto.getNome());
        biblioteca.setDescrizione(dto.getDescrizione());
        biblioteca.setAccessibilita(dto.getAccessibilita());
        biblioteca.setIndirizzo(dto.getIndirizzo());
        biblioteca.setSempreAperto(Boolean.TRUE.equals(dto.getSempreAperto()));
        biblioteca.setLink(dto.getLink());
        biblioteca.setNumero(dto.getNumero());
        biblioteca.setEmail(dto.getEmail());
        biblioteca.setTipoLuogo(Tipo.BIBLIOTECA);
        biblioteca.setCitta(citta);
        biblioteca.setPubblico(Boolean.TRUE.equals(dto.getPubblico()));
        biblioteca.setWifi(Boolean.TRUE.equals(dto.getWifi()));
        biblioteca.setAreaComputer(Boolean.TRUE.equals(dto.getAreaComputer()));
        biblioteca.setAreaBambini(Boolean.TRUE.equals(dto.getAreaBambini()));
        return biblioteca;
    }

    // trasformazione di un entity in dto
    private BibliotecaDTO toDto(Biblioteca b) {
        BibliotecaDTO dto = new BibliotecaDTO();
        dto.setId(b.getId());
        dto.setNome(b.getNome());
        dto.setIdCitta(b.getCitta().getId());
        dto.setIndirizzo(b.getIndirizzo());
        dto.setDescrizione(b.getDescrizione());
        dto.setLink(b.getLink());
        dto.setNumero(b.getNumero());
        dto.setEmail(b.getEmail());
        dto.setPubblico(b.isPubblico());
        dto.setWifi(b.isWifi());
        dto.setAreaComputer(b.isAreaComputer());
        dto.setAreaBambini(b.isAreaBambini());
        dto.setAccessibilita(b.getAccessibilita());
        dto.setSempreAperto(b.isSempreAperto());
        return dto;
    }

    // update entity per accorciare il metodo di update
    private void updateEntity(Biblioteca esistente, BibliotecaDTO dto, Citta citta) {
        esistente.setNome(dto.getNome());
        esistente.setDescrizione(dto.getDescrizione());
        esistente.setAccessibilita(dto.getAccessibilita());
        esistente.setIndirizzo(dto.getIndirizzo());
        esistente.setSempreAperto(Boolean.TRUE.equals(dto.getSempreAperto()));
        esistente.setLink(dto.getLink());
        esistente.setNumero(dto.getNumero());
        esistente.setEmail(dto.getEmail());
        esistente.setCitta(citta);
        esistente.setPubblico(Boolean.TRUE.equals(dto.getPubblico()));
        esistente.setWifi(Boolean.TRUE.equals(dto.getWifi()));
        esistente.setAreaComputer(Boolean.TRUE.equals(dto.getAreaComputer()));
        esistente.setAreaBambini(Boolean.TRUE.equals(dto.getAreaBambini()));
    }
    
    //CREATE
    public BibliotecaDTO create(BibliotecaDTO dto) {

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException("Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = bibliotecaRepository.findAll().stream()
                .anyMatch(b -> b.getNome().equalsIgnoreCase(dto.getNome())
                        && b.getCitta() != null
                        && b.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già una biblioteca con nome " + dto.getNome() + " nella città di " + citta.getNome());
        }

        Biblioteca biblioteca = toEntity(dto, citta);
        Biblioteca salvata = bibliotecaRepository.save(biblioteca);
        return toDto(salvata);
    }

    //READ
    // ricerca biblioteca tramite nome
    public Page<Biblioteca> findByNome(String nome, Pageable pageable) {

        return bibliotecaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    // da tutte le biblioteche presenti a db
    public Page<Biblioteca> findAll(Pageable pageable) {
        return bibliotecaRepository.findAll(pageable);
    }

    // ricerca biblioteca tramite filtri
    public Page<Biblioteca> search(BibliotecaDTO bibliotecaDTO, Pageable pageable) {
        Specification<Biblioteca> spec = Specification
                .where(BibliotecaSpecification.nomeContains(bibliotecaDTO.getNome()))
                .and(BibliotecaSpecification.isPubblico(bibliotecaDTO.getPubblico()))
                .and(BibliotecaSpecification.hasWifi(bibliotecaDTO.getWifi()))
                .and(BibliotecaSpecification.hasAreaComputer(bibliotecaDTO.getAreaComputer()))
                .and(BibliotecaSpecification.hasAreaBambini(bibliotecaDTO.getAreaBambini()))
                .and(BibliotecaSpecification.hasAccessibilita(bibliotecaDTO.getAccessibilita()))
                .and(BibliotecaSpecification.isSempreAperto(bibliotecaDTO.getSempreAperto()))
                .and(BibliotecaSpecification.inCitta(bibliotecaDTO.getIdCitta()));

        return bibliotecaRepository.findAll(spec, pageable);
    }

    // ricerca biblioteca tramite id
    public Biblioteca findById(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Biblioteca non trovata: " + id));
    }
    public BibliotecaDTO findByIdDto(Long id) {
        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Biblioteca non trovata: " + id));
        return toDto(biblioteca);
    }

    //UPDATE
    public BibliotecaDTO update(Long id, BibliotecaDTO dto) {

        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Biblioteca non trovata: " + id));

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException("Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = bibliotecaRepository.findAll().stream()
                .anyMatch(b -> !b.getId().equals(id)
                        && b.getNome().equalsIgnoreCase(dto.getNome())
                        && b.getCitta() != null
                        && b.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un'altra biblioteca con nome " + dto.getNome() + " nella città di "
                            + citta.getNome());
        }

        updateEntity(biblioteca, dto, citta);
        Biblioteca salvata = bibliotecaRepository.save(biblioteca);
        return toDto(salvata);
    }

    //DELETE
    public void delete(Long id) {

        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Biblioteca non trovata: " + id));

        bibliotecaRepository.delete(biblioteca);
    }
    
}
