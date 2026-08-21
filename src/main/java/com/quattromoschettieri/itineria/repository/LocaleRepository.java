package com.quattromoschettieri.itineria.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quattromoschettieri.itineria.entities.locale.Locale;


public interface LocaleRepository extends JpaRepository<Locale,Long>, JpaSpecificationExecutor<Locale> {

    Page<Locale> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
