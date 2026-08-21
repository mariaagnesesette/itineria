package com.quattromoschettieri.itineria.services;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.MuseoDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.museo.Museo;
import com.quattromoschettieri.itineria.entities.museo.PrezzoMuseo;
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

    private Museo toEntity(MuseoDTO dto, Citta citta) {
        Museo museo = new Museo();

        museo.setNome(dto.getNome());
        museo.setDescrizione(dto.getDescrizione());
        museo.setAccessibilita(dto.getAccessibilita());
        museo.setIndirizzo(dto.getIndirizzo());
        museo.setSempreAperto(Boolean.TRUE.equals(dto.getSempreAperto()));
        museo.setLink(dto.getLink());
        museo.setNumero(dto.getNumero());
        museo.setEmail(dto.getEmail());
        museo.setTipoLuogo(Tipo.MUSEO);
        museo.setCitta(citta);

        museo.setTipologia(dto.getTipologia());
        museo.setGuidaPrenotabile(
                Boolean.TRUE.equals(dto.getGuidaPrenotabile()));
        museo.setBarInterno(
                Boolean.TRUE.equals(dto.getBarInterno()));

        if (dto.getPrezzi() != null) {
            museo.setPrezzi(new ArrayList<>(dto.getPrezzi()));

            for (PrezzoMuseo prezzo : museo.getPrezzi()) {
                prezzo.setMuseo(museo);
            }
        }

        return museo;
    }

    private MuseoDTO toDto(Museo museo) {
        MuseoDTO dto = new MuseoDTO();

        dto.setId(museo.getId());
        dto.setNome(museo.getNome());
        dto.setDescrizione(museo.getDescrizione());
        dto.setTipoLuogo(museo.getTipoLuogo());
        dto.setAccessibilita(museo.getAccessibilita());
        dto.setIndirizzo(museo.getIndirizzo());
        dto.setSempreAperto(museo.isSempreAperto());
        dto.setLink(museo.getLink());
        dto.setNumero(museo.getNumero());
        dto.setEmail(museo.getEmail());

        if (museo.getCitta() != null) {
            dto.setIdCitta(museo.getCitta().getId());
        }

        dto.setTipologia(museo.getTipologia());
        dto.setGuidaPrenotabile(museo.isGuidaPrenotabile());
        dto.setBarInterno(museo.isBarInterno());
        dto.setPrezzi(museo.getPrezzi());

        return dto;
    }

    private void updateEntity(Museo esistente, MuseoDTO dto, Citta citta) {
        esistente.setNome(dto.getNome());
        esistente.setDescrizione(dto.getDescrizione());
        esistente.setAccessibilita(dto.getAccessibilita());
        esistente.setIndirizzo(dto.getIndirizzo());
        esistente.setSempreAperto(dto.getSempreAperto());
        esistente.setLink(dto.getLink());
        esistente.setNumero(dto.getNumero());
        esistente.setEmail(dto.getEmail());
        esistente.setCitta(citta);

        esistente.setTipologia(dto.getTipologia());
        esistente.setGuidaPrenotabile(
                Boolean.TRUE.equals(dto.getGuidaPrenotabile()));
        esistente.setBarInterno(
                Boolean.TRUE.equals(dto.getBarInterno()));

        if (dto.getPrezzi() != null) {
            esistente.getPrezzi().clear();
            esistente.getPrezzi().addAll(dto.getPrezzi());

            for (PrezzoMuseo prezzo : esistente.getPrezzi()) {
                prezzo.setMuseo(esistente);
            }
        }
    }

    public MuseoDTO findByIdDto(Long id) {
        Museo museo = museoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Museo non trovato: " + id));

        return toDto(museo);
    }

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

        Museo museo = toEntity(dto, citta);
        Museo salvato = museoRepository.save(museo);
        return toDto(salvato);
    }

    // READ
    public Page<Museo> findAll(Pageable pageable) {
        return museoRepository.findAll(pageable);
    }

    public Museo findById(Long id) {
        return museoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Museo non trovato: " + id));
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

        updateEntity(museo, dto, citta);
        Museo salvato = museoRepository.save(museo);
        return toDto(salvato);
    }

    //DELETE
    public void delete(Long id){

        Museo museo = museoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Museo non trovato: " +id));

        museoRepository.delete(museo);
    }
}
