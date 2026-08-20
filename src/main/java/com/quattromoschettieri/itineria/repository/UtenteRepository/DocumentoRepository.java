package com.quattromoschettieri.itineria.repository.UtenteRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.entities.utente.documento.Documento;
import com.quattromoschettieri.itineria.entities.utente.documento.TipoDocumento;

public interface DocumentoRepository extends JpaRepository<Documento, Long>{

    Optional<Documento> findByCodiceIdentificativo(String codiceIdentificativo);

    Page<Documento> findByTipoDocumento(TipoDocumento tipoDocumento, Pageable pageable);

    List<Documento> findByUtente(Utente utente);

    List<Documento> findByUtenteId(Long id);

}
