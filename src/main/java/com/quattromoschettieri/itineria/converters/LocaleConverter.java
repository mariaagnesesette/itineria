package com.quattromoschettieri.itineria.converters;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.LocaleDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.locale.Locale;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;

@Component
public final class LocaleConverter {

        public Locale toEntity(LocaleDTO dto, Citta citta) {
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

    public LocaleDTO toDto(Locale locale) {
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

    public void updateEntity(
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
}
