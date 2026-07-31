package com.quattromoschettieri.itineria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.museo.Museo;

public interface MuseoRepository extends JpaRepository<Museo,Long>{

    public List<Museo> findAllByCittaId(Long idCitta);
}
