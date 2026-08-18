package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.BibliotecaDTO;
import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.repository.BibliotecaRepository;
import com.quattromoschettieri.itineria.specification.BibliotecaSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;

    public Page<Biblioteca> search(BibliotecaDTO bibliotecaDTO, Pageable pageable){
        Specification<Biblioteca> spec = Specification
                .where(BibliotecaSpecification.nomeContains(bibliotecaDTO.nome()))
                .and(BibliotecaSpecification.isPubblico(bibliotecaDTO.pubblico()))
                .and(BibliotecaSpecification.hasWifi(bibliotecaDTO.wifi()))
                .and(BibliotecaSpecification.hasAreaComputer(bibliotecaDTO.areaComputer()))
                .and(BibliotecaSpecification.hasAreaBambini(bibliotecaDTO.areaBambini()))
                .and(BibliotecaSpecification.hasAccessibilita(bibliotecaDTO.accessibilita()))
                .and(BibliotecaSpecification.isSempreAperto(bibliotecaDTO.sempreAperto()))
                .and(BibliotecaSpecification.inCitta(bibliotecaDTO.idCitta()));
        return bibliotecaRepository.findAll(spec, pageable);
    }

}
