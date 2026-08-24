package com.quattromoschettieri.itineria.converters.eventoConverter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.EventoDTO.DataEventoDTO;
import com.quattromoschettieri.itineria.DTO.EventoDTO.EventoDTO;
import com.quattromoschettieri.itineria.entities.evento.DataEvento;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventoConverter {

    private final DateEventoConverter dateEventoConverter;

    private final LuogoInteresseRepository luogoInteresseRepository;

    public Evento toEntity(EventoDTO dto) {

    Evento evento = Evento.builder()
            .nome(dto.getNome())
            .descrizione(dto.getDescrizione())
            .tipologiaEvento(dto.getTipologiaEvento())
            .prezzo(dto.getPrezzo())
            .prenotazione(Boolean.TRUE.equals(dto.getPrenotazione()))
            .pubblicoEvento(dto.getPubblicoEvento())
            .luogoInteresse(luogoInteresseRepository
                    .findById(dto.getIdLuogoInteresse())
                    .orElseThrow(() -> new RuntimeException("Luogo di interesse non trovato")))
            .build();

    List<DataEvento> dateEvento = dto.getDateEvento() == null
        ? List.of()
        : dto.getDateEvento()
                .stream()
                .map(data -> dateEventoConverter.toEntity(data, evento))
                .toList();

    evento.setDateEvento(dateEvento);

    return evento;
    }

    public EventoDTO toDto(Evento evento) {

    EventoDTO dto = new EventoDTO();

    dto.setNome(evento.getNome());
    dto.setDescrizione(evento.getDescrizione());
    dto.setTipologiaEvento(evento.getTipologiaEvento());
    dto.setPrezzo(evento.getPrezzo());
    dto.setPrenotazione(evento.isPrenotazione());
    dto.setPubblicoEvento(evento.getPubblicoEvento());
    dto.setIdLuogoInteresse(evento.getLuogoInteresse().getId());

    List<DataEventoDTO> dateEvento = evento.getDateEvento()
            .stream()
            .map(dateEventoConverter::toDto)
            .toList();

    dto.setDateEvento(dateEvento);

    return dto;
    }

    public void updateEntity(Evento evento, EventoDTO dto) {

    LuogoInteresse luogoInteresse = luogoInteresseRepository
            .findById(dto.getIdLuogoInteresse())
            .orElseThrow(() ->
                    new RuntimeException("Luogo di interesse non trovato"));

    evento.setNome(dto.getNome());
    evento.setDescrizione(dto.getDescrizione());
    evento.setTipologiaEvento(dto.getTipologiaEvento());
    evento.setPrezzo(dto.getPrezzo());
    evento.setPrenotazione(Boolean.TRUE.equals(dto.getPrenotazione()));
    evento.setPubblicoEvento(dto.getPubblicoEvento());
    evento.setLuogoInteresse(luogoInteresse);
        }
}
