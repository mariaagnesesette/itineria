package com.quattromoschettieri.itineria.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.eventoDTO.DataEventoDTO;
import com.quattromoschettieri.itineria.DTO.eventoDTO.EventoDTO;
import com.quattromoschettieri.itineria.converters.eventoConverter.DateEventoConverter;
import com.quattromoschettieri.itineria.converters.eventoConverter.EventoConverter;
import com.quattromoschettieri.itineria.entities.evento.DataEvento;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.repository.eventoRepository.DataEventoRepository;
import com.quattromoschettieri.itineria.repository.eventoRepository.EventoRepository;
import com.quattromoschettieri.itineria.specification.EventoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final DataEventoRepository dataEventoRepository;

    private final EventoConverter eventoConverter;

    private final DateEventoConverter dateEventoConverter;


    public Page<Evento> findAll(Pageable pageable) {
    return eventoRepository.findAll(pageable);
    }

    public Evento findById(Long id) {
    return eventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento non trovato"));
    }

    public Page<Evento> findStoricoEventi(Pageable pageable) {
    return eventoRepository.findStoricoEventi(pageable);
    }

    public Page<Evento> findEventiFuturi(Pageable pageable) {
        return eventoRepository.findEventiFuturi(pageable);
    }

    public Page<Evento> findEventiInCorso(Pageable pageable) {
        return eventoRepository.findEventiInCorso(pageable);
    }

   public EventoDTO save(EventoDTO dto) {

    Evento evento = eventoConverter.toEntity(dto);

    Evento salvato = eventoRepository.save(evento);

    return eventoConverter.toDto(salvato);
    }

    public EventoDTO update(Long id, EventoDTO dto) {

    Evento evento = findById(id);


    eventoConverter.updateEntity(evento, dto);

    Evento salvato = eventoRepository.save(evento);

    return eventoConverter.toDto(salvato);
    }
    
    public void delete(Long id) {

    Evento evento = findById(id);

    eventoRepository.delete(evento);
    }

    public DataEventoDTO addDataEvento(Long idEvento, DataEventoDTO dto) {

    Evento evento = findById(idEvento);

    DataEvento dataEvento = dateEventoConverter.toEntity(dto, evento);

    DataEvento salvata = dataEventoRepository.save(dataEvento);

    return dateEventoConverter.toDto(salvata);
    }

    public void deleteDataEvento(Long idEvento, Long idDataEvento) {

    Evento evento = findById(idEvento);

    DataEvento dataEvento = dataEventoRepository.findById(idDataEvento)
            .orElseThrow(() ->
                    new RuntimeException("Data evento non trovata"));

    if (!dataEvento.getEvento().getId().equals(evento.getId())) {
        throw new RuntimeException(
                "La data non appartiene all'evento");
    }

    dataEventoRepository.delete(dataEvento);
    }

    public DataEventoDTO updateDataEvento(
        Long idEvento,
        Long idDataEvento,
        DataEventoDTO dto) {

    Evento evento = findById(idEvento);

    DataEvento dataEvento = dataEventoRepository.findById(idDataEvento)
            .orElseThrow(() ->
                    new RuntimeException("Data evento non trovata"));

    if (!dataEvento.getEvento().getId().equals(evento.getId())) {
        throw new RuntimeException(
                "La data non appartiene all'evento");
    }

    dateEventoConverter.updateDataEvento(dataEvento, dto);

    DataEvento salvata = dataEventoRepository.save(dataEvento);

    return dateEventoConverter.toDto(salvata);
    }

    public Page<Evento> search(
        EventoDTO dto,
        BigDecimal prezzoMinimo,
        BigDecimal prezzoMassimo,
        Pageable pageable) {

    DataEventoDTO data = dto.getDateEvento() != null
        && !dto.getDateEvento().isEmpty()
        ? dto.getDateEvento().get(0)
        : null;

    Specification<Evento> spec = Specification
            .where(EventoSpecification.contieneNome(dto.getNome()))
            .and(EventoSpecification.perTipologia(dto.getTipologiaEvento()))
            .and(EventoSpecification.perPubblico(dto.getPubblicoEvento()))
            .and(EventoSpecification.conPrenotazione(dto.getPrenotazione()))
            .and(EventoSpecification.prezzoTra(prezzoMinimo, prezzoMassimo))
            .and(EventoSpecification.perLuogo(dto.getIdLuogoInteresse()))
            .and(EventoSpecification.nelPeriodo(
                    data != null ? data.getDataInizio() : null,
                    data != null ? data.getDataFine() : null));

    return eventoRepository.findAll(spec, pageable);
    }
}

