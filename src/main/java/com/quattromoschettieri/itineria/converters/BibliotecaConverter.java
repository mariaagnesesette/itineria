package com.quattromoschettieri.itineria.converters;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;

@Component
public final class BibliotecaConverter{

    public Biblioteca toEntity(BibliotecaDTO dto, Citta citta) {
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

    public BibliotecaDTO toDto(Biblioteca b) {
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

    public void updateEntity(Biblioteca esistente, BibliotecaDTO dto, Citta citta) {
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

}
