package com.quattromoschettieri.itineria.converters;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.ZonaVerdeDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.zonaVerde.ZonaVerde;

@Component
public final class ZonaVerdeConverter {


    public ZonaVerde toEntity(ZonaVerdeDTO dto, Citta citta) {
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

    public ZonaVerdeDTO toDto(ZonaVerde zonaVerde) {
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

    public void updateEntity(
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
}
