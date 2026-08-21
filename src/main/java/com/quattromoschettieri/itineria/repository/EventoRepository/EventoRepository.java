package com.quattromoschettieri.itineria.repository.EventoRepository;

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
           JOIN e.dateEvento d
           GROUP BY e
           HAVING MAX(d.dataFine) < CURRENT_DATE
           """)
    Page<Evento> findStoricoEventi(Pageable pageable);

    @Query("""
           SELECT DISTINCT e
           FROM Evento e
           JOIN e.dateEvento d
           WHERE d.dataInizio > CURRENT_DATE
           """)
    Page<Evento> findEventiFuturi(Pageable pageable);

    @Query("""
           SELECT DISTINCT e
           FROM Evento e
           JOIN e.dateEvento d
           WHERE
               (
                   CURRENT_DATE > d.dataInizio
                   AND CURRENT_DATE < d.dataFine
               )
               OR
               (
                   CURRENT_DATE = d.dataInizio
                   AND CURRENT_TIME >= d.oraInizio
               )
               OR
               (
                   CURRENT_DATE = d.dataFine
                   AND CURRENT_TIME <= d.oraFine
               )
               OR
               (
                   CURRENT_DATE = d.dataInizio
                   AND CURRENT_DATE = d.dataFine
                   AND CURRENT_TIME >= d.oraInizio
                   AND CURRENT_TIME <= d.oraFine
               )
           """)
    Page<Evento> findEventiInCorso(Pageable pageable);

}