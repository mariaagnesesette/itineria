package com.quattromoschettieri.itineria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long>, JpaSpecificationExecutor<Biblioteca> {
    Page<Biblioteca> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    
}
