package com.quattromoschettieri.itineria.converters;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.MuseoDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.museo.Museo;
import com.quattromoschettieri.itineria.entities.museo.PrezzoMuseo;

@Component
public final class MuseoConverter {

    public Museo toEntity(MuseoDTO dto, Citta citta) {
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

    public MuseoDTO toDto(Museo museo) {
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

    public void updateEntity(Museo esistente, MuseoDTO dto, Citta citta) {
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

}
