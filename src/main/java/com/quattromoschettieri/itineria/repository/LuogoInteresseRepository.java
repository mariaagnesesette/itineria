package com.quattromoschettieri.itineria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Utente;

public interface LuogoInteresseRepository extends JpaRepository<LuogoInteresse, Long> {

    List<LuogoInteresse> findByManager(Utente manager);

    List<LuogoInteresse> findByManagerId(Long managerId);

    List<LuogoInteresse> findByNomeContainingIgnoreCase(String nome);

}
