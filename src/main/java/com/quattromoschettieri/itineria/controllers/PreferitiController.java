package com.quattromoschettieri.itineria.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.utenteService.PreferitiService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/utenti/{utenteId}/preferiti")
@RequiredArgsConstructor
public class PreferitiController {

    private final PreferitiService preferitiService;
    private final UtenteService utenteService;

    // Restituisce tutti i luoghi di interesse salvati
    // tra i preferiti dell'utente autenticato.
    @GetMapping("/luoghi")
    public ResponseEntity<Set<LuogoInteresse>> findLuoghiPreferiti(
            @PathVariable Long utenteId,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (!utente.getId().equals(utenteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(
                preferitiService.findLuoghiPreferiti(utenteId)
        );
    }

    // Aggiunge un luogo di interesse ai preferiti
    // dell'utente autenticato.
    @PostMapping("/luoghi/{luogoId}")
    public ResponseEntity<Void> aggiungiLuogoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long luogoId,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (!utente.getId().equals(utenteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        preferitiService.aggiungiLuogoPreferito(utenteId, luogoId);

        return ResponseEntity.ok().build();
    }

    // Rimuove un luogo di interesse dai preferiti
    // dell'utente autenticato.
    @DeleteMapping("/luoghi/{luogoId}")
    public ResponseEntity<Void> rimuoviLuogoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long luogoId,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (!utente.getId().equals(utenteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        preferitiService.rimuoviLuogoPreferito(utenteId, luogoId);

        return ResponseEntity.noContent().build();
    }

    // Restituisce tutti gli eventi salvati
    // tra i preferiti dell'utente autenticato.
    @GetMapping("/eventi")
    public ResponseEntity<Set<Evento>> findEventiPreferiti(
            @PathVariable Long utenteId,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (!utente.getId().equals(utenteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(
                preferitiService.findEventiPreferiti(utenteId)
        );
    }

    // Aggiunge un evento ai preferiti
    // dell'utente autenticato.
    @PostMapping("/eventi/{eventoId}")
    public ResponseEntity<Void> aggiungiEventoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long eventoId,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (!utente.getId().equals(utenteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        preferitiService.aggiungiEventoPreferito(utenteId, eventoId);

        return ResponseEntity.ok().build();
    }

    // Rimuove un evento dai preferiti
    // dell'utente autenticato.
    @DeleteMapping("/eventi/{eventoId}")
    public ResponseEntity<Void> rimuoviEventoPreferito(
            @PathVariable Long utenteId,
            @PathVariable Long eventoId,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());

        if (!utente.getId().equals(utenteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        preferitiService.rimuoviEventoPreferito(utenteId, eventoId);

        return ResponseEntity.noContent().build();
    }
}