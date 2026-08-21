package com.quattromoschettieri.itineria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quattromoschettieri.itineria.entities.museo.Museo;

public interface MuseoRepository extends JpaRepository<Museo,Long>, JpaSpecificationExecutor<Museo> {

    Page<Museo> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
