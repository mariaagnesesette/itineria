package com.quattromoschettieri.itineria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.citta.Regione;

public interface CittaRepository extends JpaRepository<Citta, Long>{

    Page<Citta> findByRegione(Regione regione, Pageable pageable);

    Page<Citta> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
