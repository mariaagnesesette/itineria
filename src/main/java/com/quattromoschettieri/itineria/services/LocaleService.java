package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.LocaleDTO;
import com.quattromoschettieri.itineria.converters.LocaleConverter;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.locale.Locale;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
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

    // CREATE
    public LocaleDTO create(LocaleDTO dto, Utente manager) {

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

        locale.setManager(manager);

        Locale salvato = localeRepository.save(locale);

        return localeConverter.toDto(salvato);
    }

    // READ
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

        return localeRepository.findByNomeContainingIgnoreCase(nome, pageable);
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

    // UPDATE
    public LocaleDTO update(
            Long id,
            LocaleDTO dto,
            Utente manager) {

        Locale locale = findById(id);

        verificaPermesso(locale, manager);

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

        localeConverter.updateEntity(locale, dto, citta);

        Locale salvato = localeRepository.save(locale);

        return localeConverter.toDto(salvato);
    }

    // DELETE
    public void delete(Long id, Utente manager) {

        Locale locale = findById(id);

        verificaPermesso(locale, manager);

        localeRepository.delete(locale);
    }

    // CONTROLLO PERMESSI
    private void verificaPermesso(Locale locale, Utente manager) {

        if (manager.getRuolo() == Ruolo.ADMIN) {
            return;
        }

        if (manager.getRuolo() == Ruolo.MANAGER
                && locale.getManager() != null
                && locale.getManager().getId().equals(manager.getId())) {
            return;
        }

        throw new SecurityException(
                "Non hai i permessi per modificare questo locale");
    }

    public void assegnaManager(Long localeId, Utente manager) {

        Locale locale = findById(localeId);

        if (manager.getRuolo() != Ruolo.MANAGER) {
                throw new IllegalArgumentException(
                        "L'utente deve avere ruolo MANAGER");
        }

        locale.setManager(manager);

        localeRepository.save(locale);
        }
}