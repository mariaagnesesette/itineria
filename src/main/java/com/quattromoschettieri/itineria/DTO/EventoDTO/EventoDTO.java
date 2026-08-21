package com.quattromoschettieri.itineria.DTO.EventoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.quattromoschettieri.itineria.entities.evento.PubblicoEvento;
import com.quattromoschettieri.itineria.entities.evento.TipologiaEvento;

public record EventoDTO (
        String nome,
        String descrizione,
        TipologiaEvento tipologiaEvento,
        BigDecimal prezzo,
        boolean prenotazione,
        PubblicoEvento pubblicoEvento,
        Long idLuogoInteresse,
        List<DataEventoDTO> date
)   {

}
