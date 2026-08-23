package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.ZonaVerdeDTO;
import com.quattromoschettieri.itineria.converters.ZonaVerdeConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
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
    private final ZonaVerdeConverter zonaVerdeConverter;

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

        ZonaVerde zonaVerde = zonaVerdeConverter.toEntity(dto, citta);
        ZonaVerde salvata = zonaVerdeRepository.save(zonaVerde);

        return zonaVerdeConverter.toDto(salvata);
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
        return zonaVerdeConverter.toDto(findById(id));
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

        zonaVerdeConverter.updateEntity(esistente, dto, citta);
        ZonaVerde salvata = zonaVerdeRepository.save(esistente);

        return zonaVerdeConverter.toDto(salvata);
    }

    //DELETE
    public void delete(Long id) {
        zonaVerdeRepository.delete(findById(id));
    }
}