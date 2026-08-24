package com.quattromoschettieri.itineria.converters.eventoConverter;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.eventoDTO.DataEventoDTO;
import com.quattromoschettieri.itineria.entities.evento.DataEvento;
import com.quattromoschettieri.itineria.entities.evento.Evento;

@Component
public final class DateEventoConverter {

    public DataEvento toEntity(DataEventoDTO dto, Evento evento) {

    return DataEvento.builder()
            .dataInizio(dto.getDataInizio())
            .dataFine(dto.getDataFine())
            .oraInizio(dto.getOraInizio())
            .oraFine(dto.getOraFine())
            .evento(evento)
            .build();
    }
    
    public DataEventoDTO toDto(DataEvento dataEvento) {

    DataEventoDTO dto = new DataEventoDTO();

    dto.setId(dataEvento.getId());
    dto.setDataInizio(dataEvento.getDataInizio());
    dto.setDataFine(dataEvento.getDataFine());
    dto.setOraInizio(dataEvento.getOraInizio());
    dto.setOraFine(dataEvento.getOraFine());

    return dto;
    }
    
    public void updateDataEvento(DataEvento dataEvento, DataEventoDTO dto) {

    dataEvento.setDataInizio(dto.getDataInizio());
    dataEvento.setDataFine(dto.getDataFine());
    dataEvento.setOraInizio(dto.getOraInizio());
    dataEvento.setOraFine(dto.getOraFine());
    }
}
