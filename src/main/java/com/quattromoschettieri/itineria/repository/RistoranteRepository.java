package com.quattromoschettieri.itineria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;

public interface RistoranteRepository extends JpaRepository<Ristorante, Long>, JpaSpecificationExecutor<Ristorante>{

    Page<Ristorante> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
