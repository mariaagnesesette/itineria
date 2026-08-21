package com.quattromoschettieri.itineria.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.eventoDTO.DataEventoDTO;
import com.quattromoschettieri.itineria.DTO.eventoDTO.EventoDTO;
import com.quattromoschettieri.itineria.entities.evento.DataEvento;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.repository.eventoRepository.DataEventoRepository;
import com.quattromoschettieri.itineria.repository.eventoRepository.EventoRepository;
import com.quattromoschettieri.itineria.specification.EventoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final LuogoInteresseRepository luogoInteresseRepository;

    private final DataEventoRepository dataEventoRepository;

private Evento toEntity(EventoDTO dto) {

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
                .map(data -> toEntity(data, evento))
                .toList();

    evento.setDateEvento(dateEvento);

    return evento;
    }

    private DataEvento toEntity(DataEventoDTO dto, Evento evento) {

    return DataEvento.builder()
            .dataInizio(dto.getDataInizio())
            .dataFine(dto.getDataFine())
            .oraInizio(dto.getOraInizio())
            .oraFine(dto.getOraFine())
            .evento(evento)
            .build();
    }

    private EventoDTO toDto(Evento evento) {

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
            .map(this::toDto)
            .toList();

    dto.setDateEvento(dateEvento);

    return dto;
    }

    private DataEventoDTO toDto(DataEvento dataEvento) {

    DataEventoDTO dto = new DataEventoDTO();

    dto.setId(dataEvento.getId());
    dto.setDataInizio(dataEvento.getDataInizio());
    dto.setDataFine(dataEvento.getDataFine());
    dto.setOraInizio(dataEvento.getOraInizio());
    dto.setOraFine(dataEvento.getOraFine());

    return dto;
    }

    private void updateEntity(
        Evento evento,
        EventoDTO dto,
        LuogoInteresse luogoInteresse) {

    evento.setNome(dto.getNome());
    evento.setDescrizione(dto.getDescrizione());
    evento.setTipologiaEvento(dto.getTipologiaEvento());
    evento.setPrezzo(dto.getPrezzo());
    evento.setPrenotazione(Boolean.TRUE.equals(dto.getPrenotazione()));    evento.setPubblicoEvento(dto.getPubblicoEvento());
    evento.setLuogoInteresse(luogoInteresse);
    }

    private void updateDataEvento(DataEvento dataEvento, DataEventoDTO dto) {

    dataEvento.setDataInizio(dto.getDataInizio());
    dataEvento.setDataFine(dto.getDataFine());
    dataEvento.setOraInizio(dto.getOraInizio());
    dataEvento.setOraFine(dto.getOraFine());
    }

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

    Evento evento = toEntity(dto);

    Evento salvato = eventoRepository.save(evento);

    return toDto(salvato);
    }

    public EventoDTO update(Long id, EventoDTO dto) {

    Evento evento = findById(id);

    LuogoInteresse luogoInteresse = luogoInteresseRepository
            .findById(dto.getIdLuogoInteresse())
            .orElseThrow(() ->
                    new RuntimeException("Luogo di interesse non trovato"));

    updateEntity(evento, dto, luogoInteresse);

    Evento salvato = eventoRepository.save(evento);

    return toDto(salvato);
    }
    
    public void delete(Long id) {

    Evento evento = findById(id);

    eventoRepository.delete(evento);
    }

    public DataEventoDTO addDataEvento(Long idEvento, DataEventoDTO dto) {

    Evento evento = findById(idEvento);

    DataEvento dataEvento = toEntity(dto, evento);

    evento.getDateEvento().add(dataEvento);

    DataEvento salvata = dataEventoRepository.save(dataEvento);

    return toDto(salvata);
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

    evento.getDateEvento().remove(dataEvento);

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

    updateDataEvento(dataEvento, dto);

    DataEvento salvata = dataEventoRepository.save(dataEvento);

    return toDto(salvata);
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

