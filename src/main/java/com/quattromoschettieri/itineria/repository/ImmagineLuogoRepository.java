package com.quattromoschettieri.itineria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.luogoInteresse.ImmagineLuogo;

public interface ImmagineLuogoRepository extends JpaRepository<ImmagineLuogo, Long> {

    List<ImmagineLuogo> findByLuogoInteresseIdOrderByOrdineAsc(Long luogoInteresseId);

}
