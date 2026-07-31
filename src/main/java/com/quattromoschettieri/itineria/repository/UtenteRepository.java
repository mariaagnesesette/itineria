package com.quattromoschettieri.itineria.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);

    Optional<Utente> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Page<Utente> findByNomeContainingIgnoreCase(
        String nome,
        Pageable pageable);

    Page<Utente> findByCognomeContainingIgnoreCase(
        String cognome,
        Pageable pageable);
        
    Page<Utente> findByRuolo(Ruolo ruolo, Pageable pageable);
}
