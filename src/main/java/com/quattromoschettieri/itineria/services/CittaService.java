package com.quattromoschettieri.itineria.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.CittaDTO;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.citta.Regione;
import com.quattromoschettieri.itineria.repository.CittaRepository;

import lombok.RequiredArgsConstructor;
// Create Read Update Delete 
@Service
@RequiredArgsConstructor
public class CittaService {

    private final CittaRepository cittaRepository;

    private Citta toEntity(CittaDTO dto){
        Citta c = new Citta();
        c.setNome(dto.getNome());
        c.setRegione(dto.getRegione());
        c.setDescrizione(dto.getDescrizione());

        return c;
    }

    private CittaDTO toDto(Citta c){
        CittaDTO dto = new CittaDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        dto.setRegione(c.getRegione());
        dto.setDescrizione(c.getDescrizione());

        return dto;

    }

    private void updateEntity(Citta esistente, CittaDTO dto){
        esistente.setNome(dto.getNome());
        esistente.setRegione(dto.getRegione());
        esistente.setDescrizione(dto.getDescrizione());
    }

    // CREATE
    public CittaDTO create(CittaDTO dto){

        boolean giaEsistente = cittaRepository
                                    .findAll()
                                    .stream()
                                    .anyMatch(c -> c.getNome()
                                                    .equalsIgnoreCase(dto.getNome()));
        if(giaEsistente){
            throw new IllegalArgumentException("esiste gia la citta: " + dto.getNome());
        }

        Citta citta = toEntity(dto);
        Citta salvata = cittaRepository.save(citta);
        return toDto(salvata);

    }


    //READ
    public Page<Citta> searchByNome(String nome, Pageable pageable){
        return cittaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Citta> findByRegione(Regione regione, Pageable pageable){
        return cittaRepository.findByRegione(regione, pageable);
    }

    public Page<Citta> findAll(Pageable pageable){
        return cittaRepository.findAll(pageable);
    }


}
