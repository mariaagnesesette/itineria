package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.LocaleDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.locale.Locale;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.repository.CittaRepository;
import com.quattromoschettieri.itineria.repository.LocaleRepository;
import com.quattromoschettieri.itineria.specification.LocaleSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocaleService {

    private final LocaleRepository localeRepository;
    private final CittaRepository cittaRepository;

    private Locale toEntity(LocaleDTO dto, Citta citta) {
        Locale locale = new Locale();

        locale.setNome(dto.getNome());
        locale.setDescrizione(dto.getDescrizione());
        locale.setAccessibilita(dto.getAccessibilita());
        locale.setIndirizzo(dto.getIndirizzo());
        locale.setSempreAperto(
                Boolean.TRUE.equals(dto.getSempreAperto()));
        locale.setLink(dto.getLink());
        locale.setNumero(dto.getNumero());
        locale.setEmail(dto.getEmail());
        locale.setTipoLuogo(Tipo.LOCALE);
        locale.setCitta(citta);

        locale.setTipoLocale(dto.getTipoLocale());
        locale.setAtmosfera(dto.getAtmosfera());
        locale.setFasciaPrezzo(dto.getFasciaPrezzo());
        locale.setAperturaSerale(
                Boolean.TRUE.equals(dto.getAperturaSerale()));
        locale.setPostiEsterni(
                Boolean.TRUE.equals(dto.getPostiEsterni()));
        locale.setPerCeliaci(
                Boolean.TRUE.equals(dto.getPerCeliaci()));

        return locale;
    }

    private LocaleDTO toDto(Locale locale) {
        LocaleDTO dto = new LocaleDTO();

        dto.setId(locale.getId());
        dto.setNome(locale.getNome());
        dto.setDescrizione(locale.getDescrizione());
        dto.setTipoLuogo(locale.getTipoLuogo());
        dto.setAccessibilita(locale.getAccessibilita());
        dto.setIndirizzo(locale.getIndirizzo());
        dto.setSempreAperto(locale.isSempreAperto());
        dto.setLink(locale.getLink());
        dto.setNumero(locale.getNumero());
        dto.setEmail(locale.getEmail());

        if (locale.getCitta() != null) {
            dto.setIdCitta(locale.getCitta().getId());
        }

        dto.setTipoLocale(locale.getTipoLocale());
        dto.setAtmosfera(locale.getAtmosfera());
        dto.setFasciaPrezzo(locale.getFasciaPrezzo());
        dto.setAperturaSerale(locale.isAperturaSerale());
        dto.setPostiEsterni(locale.isPostiEsterni());
        dto.setPerCeliaci(locale.isPerCeliaci());

        return dto;
    }

    private void updateEntity(
            Locale esistente,
            LocaleDTO dto,
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

        esistente.setTipoLocale(dto.getTipoLocale());
        esistente.setAtmosfera(dto.getAtmosfera());
        esistente.setFasciaPrezzo(dto.getFasciaPrezzo());
        esistente.setAperturaSerale(
                Boolean.TRUE.equals(dto.getAperturaSerale()));
        esistente.setPostiEsterni(
                Boolean.TRUE.equals(dto.getPostiEsterni()));
        esistente.setPerCeliaci(
                Boolean.TRUE.equals(dto.getPerCeliaci()));
    }

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

        Locale locale = toEntity(dto, citta);
        Locale salvato = localeRepository.save(locale);

        return toDto(salvato);
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
        return toDto(findById(id));
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

        updateEntity(esistente, dto, citta);
        Locale salvato = localeRepository.save(esistente);

        return toDto(salvato);
    }

    public void delete(Long id) {
        localeRepository.delete(findById(id));
    }
}