package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.LocaleDTO;
import com.quattromoschettieri.itineria.converters.LocaleConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.locale.Locale;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.repository.LocaleRepository;
import com.quattromoschettieri.itineria.specification.LocaleSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocaleService {

    private final LocaleRepository localeRepository;
    private final CittaRepository cittaRepository;
    private final LocaleConverter localeConverter;



    public LocaleDTO create(LocaleDTO dto) {
        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = localeRepository.findAll().stream()
                .anyMatch(l -> l.getNome().equalsIgnoreCase(dto.getNome())
                        && l.getCitta() != null
                        && l.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un locale con nome " + dto.getNome()
                            + " nella città di " + citta.getNome());
        }

        Locale locale = localeConverter.toEntity(dto, citta);
        Locale salvato = localeRepository.save(locale);

        return localeConverter.toDto(salvato);
    }

    public Page<Locale> findAll(Pageable pageable) {
        return localeRepository.findAll(pageable);
    }

    public Locale findById(Long id) {
        return localeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Locale non trovato: " + id));
    }

    public LocaleDTO findByIdDto(Long id) {
        return localeConverter.toDto(findById(id));
    }

    public Page<Locale> findByNome(
            String nome,
            Pageable pageable) {

        return localeRepository
                .findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Locale> search(
            LocaleDTO localeDTO,
            Pageable pageable) {

        Specification<Locale> spec = Specification
                .where(LocaleSpecification.nomeContains(
                        localeDTO.getNome()))
                .and(LocaleSpecification.tipoLocale(
                        localeDTO.getTipoLocale()))
                .and(LocaleSpecification.atmosfera(
                        localeDTO.getAtmosfera()))
                .and(LocaleSpecification.fasciaPrezzo(
                        localeDTO.getFasciaPrezzo()))
                .and(LocaleSpecification.isAperturaSerale(
                        localeDTO.getAperturaSerale()))
                .and(LocaleSpecification.hasPostiEsterni(
                        localeDTO.getPostiEsterni()))
                .and(LocaleSpecification.isPerCeliaci(
                        localeDTO.getPerCeliaci()))
                .and(LocaleSpecification.hasAccessibilita(
                        localeDTO.getAccessibilita()))
                .and(LocaleSpecification.isSempreAperto(
                        localeDTO.getSempreAperto()))
                .and(LocaleSpecification.inCitta(
                        localeDTO.getIdCitta()));

        return localeRepository.findAll(spec, pageable);
    }

    public LocaleDTO update(Long id, LocaleDTO dto) {
        Locale esistente = findById(id);

        Citta citta = cittaRepository.findById(dto.getIdCitta())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Città non trovata: " + dto.getIdCitta()));

        boolean giaEsistente = localeRepository.findAll().stream()
                .anyMatch(l -> !l.getId().equals(id)
                        && l.getNome().equalsIgnoreCase(dto.getNome())
                        && l.getCitta() != null
                        && l.getCitta().getId().equals(dto.getIdCitta()));

        if (giaEsistente) {
            throw new IllegalArgumentException(
                    "Esiste già un altro locale con nome " + dto.getNome()
                            + " nella città di " + citta.getNome());
        }

        localeConverter.updateEntity(esistente, dto, citta);
        Locale salvato = localeRepository.save(esistente);

        return localeConverter.toDto(salvato);
    }

    public void delete(Long id) {
        localeRepository.delete(findById(id));
    }
}