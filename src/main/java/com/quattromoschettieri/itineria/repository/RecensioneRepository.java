package com.quattromoschettieri.itineria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.recensione.Recensione;
import com.quattromoschettieri.itineria.entities.utente.Utente;


public interface RecensioneRepository extends JpaRepository<Recensione, Long>, JpaSpecificationExecutor<Recensione>{

    Page<Recensione> findByLuogoInteresse(LuogoInteresse luogoInteresse, Pageable pageable);

    Page<Recensione> findByLuogoInteresseId(Long id, Pageable pageable);

    Page<Recensione> findByUtente(Utente utente, Pageable pageable);

    Page<Recensione> findByUtenteId(Long id, Pageable pageable);

    
    /*con Pageable si può anche ordinare in base ai parametri
    
    es.Page<Recensione> recensioni = 
    recensioneRepository.findByLuogoInteresseId(
        luogoId,
        PageRequest.of(0,20,Sort.by("voto").descending())
    ); 
    
    perciò non serve scrivere i metodi con Order By per ordinare*/
    Page<Recensione> findByVoto(Integer voto, Pageable pageable);
}
