package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.ZonaVerdeDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.zonaVerde.ZonaVerde;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.repository.ZonaVerdeRepository;
import com.quattromoschettieri.itineria.specification.ZonaVerdeSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZonaVerdeService {

    private final ZonaVerdeRepository zonaVerdeRepository;
    private final CittaRepository cittaRepository;

    private ZonaVerde toEntity(ZonaVerdeDTO dto, Citta citta) {
        ZonaVerde zonaVerde = new ZonaVerde();

        zonaVerde.setNome(dto.getNome());
        zonaVerde.setDescrizione(dto.getDescrizione());
        zonaVerde.setAccessibilita(dto.getAccessibilita());
        zonaVerde.setIndirizzo(dto.getIndirizzo());
        zonaVerde.setSempreAperto(
                Boolean.TRUE.equals(dto.getSempreAperto()));
        zonaVerde.setLink(dto.getLink());
        zonaVerde.setNumero(dto.getNumero());
        zonaVerde.setEmail(dto.getEmail());
        zonaVerde.setTipoLuogo(Tipo.ZONA_VERDE);
        zonaVerde.setCitta(citta);

        zonaVerde.setAreaMq(
                dto.getAreaMq() != null ? dto.getAreaMq() : 0.0);
        zonaVerde.setTipologia(dto.getTipologia());
        zonaVerde.setDogFriendly(
                Boolean.TRUE.equals(dto.getDogFriendly()));
        zonaVerde.setRistoro(
                Boolean.TRUE.equals(dto.getRistoro()));
        zonaVerde.setCiclabile(
                Boolean.TRUE.equals(dto.getCiclabile()));

        return zonaVerde;
    }

    private ZonaVerdeDTO toDto(ZonaVerde zonaVerde) {
        ZonaVerdeDTO dto = new ZonaVerdeDTO();

        dto.setId(zonaVerde.getId());
        dto.setNome(zonaVerde.getNome());
        dto.setDescrizione(zonaVerde.getDescrizione());
        dto.setTipoLuogo(zonaVerde.getTipoLuogo());
        dto.setAccessibilita(zonaVerde.getAccessibilita());
        dto.setIndirizzo(zonaVerde.getIndirizzo());
        dto.setSempreAperto(zonaVerde.isSempreAperto());
        dto.setLink(zonaVerde.getLink());
        dto.setNumero(zonaVerde.getNumero());
        dto.setEmail(zonaVerde.getEmail());

        if (zonaVerde.getCitta() != null) {
            dto.setIdCitta(zonaVerde.getCitta().getId());
        }

        dto.setAreaMq(zonaVerde.getAreaMq());
        dto.setTipologia(zonaVerde.getTipologia());
        dto.setDogFriendly(zonaVerde.isDogFriendly());
        dto.setRistoro(zonaVerde.isRistoro());
        dto.setCiclabile(zonaVerde.isCiclabile());

        return dto;
    }

    private void updateEntity(
            ZonaVerde esistente,
            ZonaVerdeDTO dto,
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

        esistente.setAreaMq(
                dto.getAreaMq() != null ? dto.getAreaMq() : 0.0);
        esistente.setTipologia(dto.getTipologia());
        esistente.setDogFriendly(
                Boolean.TRUE.equals(dto.getDogFriendly()));
        esistente.setRistoro(
                Boolean.TRUE.equals(dto.getRistoro()));
        esistente.setCiclabile(
                Boolean.TRUE.equals(dto.getCiclabile()));
    }

    //CREATE
    public ZonaVerdeDTO create(ZonaVerdeDTO dto) {
        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = zonaVerdeRepository.findAll().stream()
                .anyMatch(z -> z.getNome().equalsIgnoreCase(dto.getNome())
                        && z.getCitta() != null
                        && z.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già una zona verde con nome " + dto.getNome()
                            + " nella città di " + citta.getNome());
        }

        ZonaVerde zonaVerde = toEntity(dto, citta);
        ZonaVerde salvata = zonaVerdeRepository.save(zonaVerde);

        return toDto(salvata);
    }

    //READ
    public Page<ZonaVerde> findAll(Pageable pageable) {
        return zonaVerdeRepository.findAll(pageable);
    }

    public ZonaVerde findById(Long id) {
        return zonaVerdeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Zona verde non trovata: " + id));
    }

    public ZonaVerdeDTO findByIdDto(Long id) {
        return toDto(findById(id));
    }

    public Page<ZonaVerde> findByNome(
            String nome,
            Pageable pageable) {

        return zonaVerdeRepository
                .findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<ZonaVerde> search(
            ZonaVerdeDTO zonaVerdeDTO,
            Pageable pageable) {

        Specification<ZonaVerde> spec = Specification
                .where(ZonaVerdeSpecification.nomeContains(
                        zonaVerdeDTO.getNome()))
                .and(ZonaVerdeSpecification.areaMq(
                        zonaVerdeDTO.getAreaMin(),
                        zonaVerdeDTO.getAreaMax()))
                .and(ZonaVerdeSpecification.tipologia(
                        zonaVerdeDTO.getTipologia()))
                .and(ZonaVerdeSpecification.isDogFriendly(
                        zonaVerdeDTO.getDogFriendly()))
                .and(ZonaVerdeSpecification.hasRistoro(
                        zonaVerdeDTO.getRistoro()))
                .and(ZonaVerdeSpecification.isCiclabile(
                        zonaVerdeDTO.getCiclabile()))
                .and(ZonaVerdeSpecification.hasAccessibilita(
                        zonaVerdeDTO.getAccessibilita()))
                .and(ZonaVerdeSpecification.isSempreAperto(
                        zonaVerdeDTO.getSempreAperto()))
                .and(ZonaVerdeSpecification.inCitta(
                        zonaVerdeDTO.getIdCitta()));

        return zonaVerdeRepository.findAll(spec, pageable);
    }

    //UPDATE
    public ZonaVerdeDTO update(Long id, ZonaVerdeDTO dto) {
        ZonaVerde esistente = findById(id);

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = zonaVerdeRepository.findAll().stream()
                .anyMatch(z -> !z.getId().equals(id)
                        && z.getNome().equalsIgnoreCase(dto.getNome())
                        && z.getCitta() != null
                        && z.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un'altra zona verde con nome "
                            + dto.getNome() + " nella città di "
                            + citta.getNome());
        }

        updateEntity(esistente, dto, citta);
        ZonaVerde salvata = zonaVerdeRepository.save(esistente);

        return toDto(salvata);
    }

    //DELETE
    public void delete(Long id) {
        zonaVerdeRepository.delete(findById(id));
    }
}