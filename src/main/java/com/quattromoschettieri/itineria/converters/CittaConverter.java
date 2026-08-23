package com.quattromoschettieri.itineria.converters;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.CittaDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;

@Component
public final class CittaConverter {
    
    public Citta toEntity(CittaDTO dto) {
        Citta c = new Citta();
        c.setNome(dto.getNome());
        c.setRegione(dto.getRegione());
        c.setDescrizione(dto.getDescrizione());

        return c;
    }

    public CittaDTO toDto(Citta c) {
        CittaDTO dto = new CittaDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        dto.setRegione(c.getRegione());
        dto.setDescrizione(c.getDescrizione());

        return dto;

    }

    public void updateEntity(Citta esistente, CittaDTO dto) {
        esistente.setNome(dto.getNome());
        esistente.setRegione(dto.getRegione());
        esistente.setDescrizione(dto.getDescrizione());
    }
}
