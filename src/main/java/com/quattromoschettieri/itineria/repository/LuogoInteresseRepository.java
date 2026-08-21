package com.quattromoschettieri.itineria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;

public interface LuogoInteresseRepository extends JpaRepository<LuogoInteresse, Long> {

    public Optional<LuogoInteresse> findById(Long id);

}
