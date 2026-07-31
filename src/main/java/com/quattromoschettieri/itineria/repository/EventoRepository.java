package com.quattromoschettieri.itineria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;

public interface EventoRepository extends JpaRepository<Evento, Long>, JpaSpecificationExecutor<Evento>{

    Page<Evento> findByLuogoInteresse(LuogoInteresse luogoInteresse, Pageable pageable);

    Page<Evento> findByLuogoInteresseId(Long id, Pageable pageable);

    Page<Evento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("""
           SELECT e
           FROM Evento e
           JOIN e.dataEvento d
           WHERE d.dataFine < CURRENT_DATE
           """)
    Page<Evento> findStoricoEventi(Pageable pageable);

    @Query("""
           SELECT e
           FROM Evento e
           JOIN e.dataEvento d
           WHERE d.dataInizio >= CURRENT_DATE
           """)
    Page<Evento> findEventiFuturi(Pageable pageable);

    @Query("""
           SELECT e
           FROM Evento e
           JOIN e.dataEvento d
           WHERE CURRENT_DATE BETWEEN d.dataInizio AND d.dataFine
           """)
    Page<Evento> findEventiInCorso(Pageable pageable);

}
