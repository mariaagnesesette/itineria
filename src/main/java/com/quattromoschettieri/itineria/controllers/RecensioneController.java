package com.quattromoschettieri.itineria.controllers;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.RecensioneService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recensioni")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;
    private final UtenteService utenteService;


    // =========================
    // READ
    // =========================

    // Restituisce tutte le recensioni presenti nel database.
    // Le recensioni sono informazioni pubbliche.
    @GetMapping
    public ResponseEntity<Page<Recensione>> findAll(Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.findAll(pageable)
        );
    }


    // Restituisce una recensione specifica.
    // Le recensioni sono informazioni pubbliche.
    @GetMapping("/{id}")
    public ResponseEntity<Recensione> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recensioneService.findById(id)
        );
    }


    // Restituisce tutte le recensioni relative
    // a uno specifico luogo di interesse.
    // Le recensioni sono informazioni pubbliche.
    @GetMapping("/luogo/{id}")
    public ResponseEntity<Page<Recensione>> findByLuogoInteresseId(
            @PathVariable Long id,
            Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.findByLuogoInteresseId(id, pageable)
        );
    }


    // Restituisce tutte le recensioni dell'utente autenticato.
    // Un utente non può visualizzare quelle di un altro utente
    // tramite questo endpoint.
    @GetMapping("/utente/{id}")
    public ResponseEntity<Page<Recensione>> findByUtenteId(
            @PathVariable Long id,
            Pageable pageable,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(
                authentication.getName()
        );

        if (!utente.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(
                recensioneService.findByUtenteId(id, pageable)
        );
    }


    // Restituisce tutte le recensioni con uno specifico voto.
    // Informazione pubblica.
    @GetMapping("/voto/{voto}")
    public ResponseEntity<Page<Recensione>> findByVoto(
            @PathVariable Integer voto,
            Pageable pageable) {

        return ResponseEntity.ok(
                recensioneService.findByVoto(voto, pageable)
        );
    }


    // =========================
    // CREATE
    // =========================

    // Crea una nuova recensione.
    //
    // L'utente viene ricavato dall'autenticazione.
    // Il client non può scegliere l'utente proprietario
    // della recensione.
    @PostMapping
    public ResponseEntity<RecensioneDTO> save(
            @RequestBody RecensioneDTO dto,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(
                authentication.getName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                recensioneService.save(dto, utente)
        );
    }


    // =========================
    // UPDATE
    // =========================

    // Modifica una recensione.
    //
    // Il controllo che la recensione appartenga
    // all'utente autenticato viene effettuato nel service.
    @PutMapping("/{id}")
    public ResponseEntity<RecensioneDTO> update(
            @PathVariable Long id,
            @RequestBody RecensioneDTO dto,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(
                authentication.getName()
        );

        return ResponseEntity.ok(
                recensioneService.update(id, dto, utente)
        );
    }


    // =========================
    // DELETE
    // =========================

    // Elimina una recensione.
    //
    // Il controllo che la recensione appartenga
    // all'utente autenticato viene effettuato nel service.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(
                authentication.getName()
        );

        recensioneService.delete(id, utente);

        return ResponseEntity.noContent().build();
    }


    // =========================
    // SEARCH
    // =========================

    // Ricerca dinamica delle recensioni.
    //
    // Filtri:
    // - idLuogoInteresse
    // - voto
    // - creataDopo
    // - creataPrima
    //
    // Se viene specificato idUtente, può essere utilizzato
    // solo per cercare le recensioni dell'utente autenticato.
    @GetMapping("/search")
    public ResponseEntity<Page<Recensione>> search(
            @ModelAttribute RecensioneDTO dto,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime creataDopo,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime creataPrima,

            Pageable pageable,
            Authentication authentication) {

        if (dto.getIdUtente() != null) {

            Utente utente = utenteService.findByEmail(
                    authentication.getName()
            );

            if (!utente.getId().equals(dto.getIdUtente())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

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
