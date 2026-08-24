package com.quattromoschettieri.itineria.controllers;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.services.utenteService.PreferitiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/utenti/{utenteId}/preferiti")
@RequiredArgsConstructor
public class PreferitiController {

    private final PreferitiService preferitiService;

    // Restituisce tutti i luoghi di interesse salvati
    // tra i preferiti dell'utente indicato.
    @GetMapping("/luoghi")
    public ResponseEntity<Set<LuogoInteresse>> findLuoghiPreferiti(
            @PathVariable Long utenteId) {

        return ResponseEntity.ok(
                preferitiService.findLuoghiPreferiti(utenteId)
        );
    }

    // Aggiunge un luogo di interesse ai preferiti
    // dell'utente indicato.
    @PostMapping("/luoghi/{luogoId}")
    public ResponseEntity<Void> aggiungiLuogoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long luogoId) {

        preferitiService.aggiungiLuogoPreferito(utenteId, luogoId);

        return ResponseEntity.ok().build();
    }

    // Rimuove un luogo di interesse dai preferiti
    // dell'utente indicato.
    @DeleteMapping("/luoghi/{luogoId}")
    public ResponseEntity<Void> rimuoviLuogoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long luogoId) {

        preferitiService.rimuoviLuogoPreferito(utenteId, luogoId);

        return ResponseEntity.noContent().build();
    }

    // Restituisce tutti gli eventi salvati
    // tra i preferiti dell'utente indicato.
    @GetMapping("/eventi")
    public ResponseEntity<Set<Evento>> findEventiPreferiti(
            @PathVariable Long utenteId) {

        return ResponseEntity.ok(
                preferitiService.findEventiPreferiti(utenteId)
        );
    }

    // Aggiunge un evento ai preferiti
    // dell'utente indicato.
    @PostMapping("/eventi/{eventoId}")
    public ResponseEntity<Void> aggiungiEventoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long eventoId) {

        preferitiService.aggiungiEventoPreferito(utenteId, eventoId);

        return ResponseEntity.ok().build();
    }

    // Rimuove un evento dai preferiti
    // dell'utente indicato.
    @DeleteMapping("/eventi/{eventoId}")
    public ResponseEntity<Void> rimuoviEventoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long eventoId) {

        preferitiService.rimuoviEventoPreferito(utenteId, eventoId);

        return ResponseEntity.noContent().build();
    }
}
