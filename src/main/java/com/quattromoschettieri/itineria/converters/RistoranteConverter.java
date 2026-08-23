package com.quattromoschettieri.itineria.converters;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.RistoranteDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;

@Component
public final class RistoranteConverter {

     public Ristorante toEntity(RistoranteDTO dto, Citta citta) {
        Ristorante ristorante = new Ristorante();

        ristorante.setNome(dto.getNome());
        ristorante.setDescrizione(dto.getDescrizione());
        ristorante.setAccessibilita(dto.getAccessibilita());
        ristorante.setIndirizzo(dto.getIndirizzo());
        ristorante.setSempreAperto(
                Boolean.TRUE.equals(dto.getSempreAperto()));
        ristorante.setLink(dto.getLink());
        ristorante.setNumero(dto.getNumero());
        ristorante.setEmail(dto.getEmail());
        ristorante.setTipoLuogo(Tipo.RISTORANTE);
        ristorante.setCitta(citta);

        ristorante.setTipoCucina(dto.getTipoCucina());
        ristorante.setFasciaPrezzo(dto.getFasciaPrezzo());
        ristorante.setDogFriendly(
                Boolean.TRUE.equals(dto.getDogFriendly()));
        ristorante.setPerCeliaci(
                Boolean.TRUE.equals(dto.getPerCeliaci()));
        ristorante.setPostiEsterni(
                Boolean.TRUE.equals(dto.getPostiEsterni()));

        return ristorante;
    }

    public RistoranteDTO toDto(Ristorante ristorante) {
        RistoranteDTO dto = new RistoranteDTO();

        dto.setId(ristorante.getId());
        dto.setNome(ristorante.getNome());
        dto.setDescrizione(ristorante.getDescrizione());
        dto.setTipoLuogo(ristorante.getTipoLuogo());
        dto.setAccessibilita(ristorante.getAccessibilita());
        dto.setIndirizzo(ristorante.getIndirizzo());
        dto.setSempreAperto(ristorante.isSempreAperto());
        dto.setLink(ristorante.getLink());
        dto.setNumero(ristorante.getNumero());
        dto.setEmail(ristorante.getEmail());

        if (ristorante.getCitta() != null) {
            dto.setIdCitta(ristorante.getCitta().getId());
        }

        dto.setTipoCucina(ristorante.getTipoCucina());
        dto.setFasciaPrezzo(ristorante.getFasciaPrezzo());
        dto.setDogFriendly(ristorante.isDogFriendly());
        dto.setPerCeliaci(ristorante.isPerCeliaci());
        dto.setPostiEsterni(ristorante.isPostiEsterni());

        return dto;
    }

    public void updateEntity(
            Ristorante esistente,
            RistoranteDTO dto,
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

        esistente.setTipoCucina(dto.getTipoCucina());
        esistente.setFasciaPrezzo(dto.getFasciaPrezzo());
        esistente.setDogFriendly(
                Boolean.TRUE.equals(dto.getDogFriendly()));
        esistente.setPerCeliaci(
                Boolean.TRUE.equals(dto.getPerCeliaci()));
        esistente.setPostiEsterni(
                Boolean.TRUE.equals(dto.getPostiEsterni()));
    }

}
