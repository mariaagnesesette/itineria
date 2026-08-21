package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.RistoranteDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
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

    private Ristorante toEntity(RistoranteDTO dto, Citta citta) {
        Ristorante ristorante = new Ristorante();

        ristorante.setNome(dto.getNome());
        ristorante.setDescrizione(dto.getDescrizione());
        ristorante.setAccessibilita(dto.getAccessibilita());
        ristorante.setIndirizzo(dto.getIndirizzo());
        ristorante.setSempreAperto(
                Boolean.TRUE.equals(dto.getSempreAperto()));
        ristorante.setLink(dto.getLink());
        ristorante.setNumero(dto.getNumero());
        ristorante.setEmail(dto.getEmail());
        ristorante.setTipoLuogo(Tipo.RISTORANTE);
        ristorante.setCitta(citta);

        ristorante.setTipoCucina(dto.getTipoCucina());
        ristorante.setFasciaPrezzo(dto.getFasciaPrezzo());
        ristorante.setDogFriendly(
                Boolean.TRUE.equals(dto.getDogFriendly()));
        ristorante.setPerCeliaci(
                Boolean.TRUE.equals(dto.getPerCeliaci()));
        ristorante.setPostiEsterni(
                Boolean.TRUE.equals(dto.getPostiEsterni()));

        return ristorante;
    }

    private RistoranteDTO toDto(Ristorante ristorante) {
        RistoranteDTO dto = new RistoranteDTO();

        dto.setId(ristorante.getId());
        dto.setNome(ristorante.getNome());
        dto.setDescrizione(ristorante.getDescrizione());
        dto.setTipoLuogo(ristorante.getTipoLuogo());
        dto.setAccessibilita(ristorante.getAccessibilita());
        dto.setIndirizzo(ristorante.getIndirizzo());
        dto.setSempreAperto(ristorante.isSempreAperto());
        dto.setLink(ristorante.getLink());
        dto.setNumero(ristorante.getNumero());
        dto.setEmail(ristorante.getEmail());

        if (ristorante.getCitta() != null) {
            dto.setIdCitta(ristorante.getCitta().getId());
        }

        dto.setTipoCucina(ristorante.getTipoCucina());
        dto.setFasciaPrezzo(ristorante.getFasciaPrezzo());
        dto.setDogFriendly(ristorante.isDogFriendly());
        dto.setPerCeliaci(ristorante.isPerCeliaci());
        dto.setPostiEsterni(ristorante.isPostiEsterni());

        return dto;
    }

    private void updateEntity(
            Ristorante esistente,
            RistoranteDTO dto,
            Citta citta) {

        esistente.setNome(dto.getNome());
        esistente.setDescrizione(dto.getDescrizione());
        esistente.setAccessibilita(dto.getAccessibilita());
        esistente.setIndirizzo(dto.getIndirizzo());
        esistente.setSempreAperto(
                Boolean.TRUE.equals(dto.getSempreAperto()));
        esistente.setLink(dto.getLink());
        esistente.setNumero(dto.getNumero());
        esistente.setEmail(dto.getEmail());
        esistente.setCitta(citta);

        esistente.setTipoCucina(dto.getTipoCucina());
        esistente.setFasciaPrezzo(dto.getFasciaPrezzo());
        esistente.setDogFriendly(
                Boolean.TRUE.equals(dto.getDogFriendly()));
        esistente.setPerCeliaci(
                Boolean.TRUE.equals(dto.getPerCeliaci()));
        esistente.setPostiEsterni(
                Boolean.TRUE.equals(dto.getPostiEsterni()));
    }

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

        Ristorante ristorante = toEntity(dto, citta);
        Ristorante salvato = ristoranteRepository.save(ristorante);
        return toDto(salvato);

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

        return toDto(ristorante);
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

        updateEntity(ristorante, dto, citta);
        Ristorante salvato = ristoranteRepository.save(ristorante);
        return toDto(salvato);
    }

    //DELETE
    public void delete(Long id) {

        Ristorante ristorante = ristoranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ristorante non trovato: " + id));

        ristoranteRepository.delete(ristorante);
    }
}
