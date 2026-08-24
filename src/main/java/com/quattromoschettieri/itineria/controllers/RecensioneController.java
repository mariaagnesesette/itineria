package com.quattromoschettieri.itineria.controllers;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quattromoschettieri.itineria.DTO.RecensioneDTO;
import com.quattromoschettieri.itineria.entities.recensione.Recensione;
import com.quattromoschettieri.itineria.services.RecensioneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recensioni")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;

    // Restituisce tutte le recensioni presenti nel database.
    // Pageable permette di gestire paginazione e ordinamento.
    @GetMapping
    public ResponseEntity<Page<Recensione>> findAll(Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.findAll(pageable)
        );
    }

    // Restituisce una recensione specifica tramite il suo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Recensione> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recensioneService.findById(id)
        );
    }

    // Restituisce tutte le recensioni relative
    // a uno specifico luogo di interesse.
    @GetMapping("/luogo/{id}")
    public ResponseEntity<Page<Recensione>> findByLuogoInteresseId(
            @PathVariable Long id,
            Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.findByLuogoInteresseId(id, pageable)
        );
    }

    // Restituisce tutte le recensioni scritte
    // da uno specifico utente.
    @GetMapping("/utente/{id}")
    public ResponseEntity<Page<Recensione>> findByUtenteId(
            @PathVariable Long id,
            Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.findByUtenteId(id, pageable)
        );
    }

    // Restituisce tutte le recensioni con
    // uno specifico voto.
    @GetMapping("/voto/{voto}")
    public ResponseEntity<Page<Recensione>> findByVoto(
            @PathVariable Integer voto,
            Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.findByVoto(voto, pageable)
        );
    }

    // Crea una nuova recensione.
    // Il DTO contiene i dati necessari per creare l'entity.
    @PostMapping
    public ResponseEntity<RecensioneDTO> save(
            @RequestBody RecensioneDTO dto) {

        return ResponseEntity.ok(
                recensioneService.save(dto)
        );
    }

    // Modifica una recensione esistente.
    @PutMapping("/{id}")
    public ResponseEntity<RecensioneDTO> update(
            @PathVariable Long id,
            @RequestBody RecensioneDTO dto) {

        return ResponseEntity.ok(
                recensioneService.update(id, dto)
        );
    }

    // Elimina una recensione tramite il suo ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        recensioneService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // Ricerca dinamica delle recensioni.
    //
    // I parametri presenti nel RecensioneDTO vengono utilizzati
    // come filtri opzionali:
    // - idUtente
    // - idLuogoInteresse
    // - voto
    //
    // creataDopo e creataPrima permettono invece
    // di filtrare per intervallo temporale.
    @GetMapping("/search")
    public ResponseEntity<Page<Recensione>> search(
            @ModelAttribute RecensioneDTO dto,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime creataDopo,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime creataPrima,

            Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.search(
                        dto,
                        creataDopo,
                        creataPrima,
                        pageable
                )
        );
    }
}
