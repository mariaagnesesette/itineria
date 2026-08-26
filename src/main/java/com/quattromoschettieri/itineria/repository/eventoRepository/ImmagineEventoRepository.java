package com.quattromoschettieri.itineria.repository.eventoRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.evento.ImmagineEvento;

public interface ImmagineEventoRepository extends JpaRepository<ImmagineEvento, Long> {

    List<ImmagineEvento> findByEventoIdOrderByOrdineAsc(Long eventoId);

}
