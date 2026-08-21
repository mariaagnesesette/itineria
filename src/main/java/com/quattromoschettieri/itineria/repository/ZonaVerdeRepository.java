package com.quattromoschettieri.itineria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quattromoschettieri.itineria.entities.zonaVerde.ZonaVerde;

public interface ZonaVerdeRepository extends JpaRepository<ZonaVerde, Long>, JpaSpecificationExecutor<ZonaVerde>{

    Page<ZonaVerde> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
