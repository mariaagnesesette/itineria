package com.quattromoschettieri.itineria.DTO.eventoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.quattromoschettieri.itineria.entities.evento.PubblicoEvento;
import com.quattromoschettieri.itineria.entities.evento.TipologiaEvento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventoDTO {
    private String nome;
    private String descrizione;
    private TipologiaEvento tipologiaEvento;
    private BigDecimal prezzo;
    private Boolean prenotazione;
    private PubblicoEvento pubblicoEvento;
    private Long idLuogoInteresse;
    private List<DataEventoDTO> dateEvento;
}
