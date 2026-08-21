package com.quattromoschettieri.itineria.repository.EventoRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.evento.DataEvento;

public interface DataEventoRepository extends JpaRepository<DataEvento, Long> {

      List<DataEvento> findByEventoId(Long eventoId);
}
