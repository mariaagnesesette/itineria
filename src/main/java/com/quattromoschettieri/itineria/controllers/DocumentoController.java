package com.quattromoschettieri.itineria.controllers;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.quattromoschettieri.itineria.DTO.utenteDTO.DocumentoDTO;
import com.quattromoschettieri.itineria.converters.utenteConverter.DocumentoConverter;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.entities.utente.documento.Documento;
import com.quattromoschettieri.itineria.services.documentoService.DocumentoService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/documenti")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;
    private final UtenteService utenteService;
    private final DocumentoConverter documentoConverter;


    // =========================
    // LISTA DOCUMENTI
    // =========================

    // Mostra tutti i documenti dell'utente autenticato.
    @GetMapping
    public String listaDocumenti(
            Authentication authentication,
            Model model) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        List<DocumentoDTO> documenti =
                documentoService.findByUtente(utente)
                        .stream()
                        .map(documentoConverter::toDto)
                        .toList();

        model.addAttribute("documenti", documenti);

        return "utente/documenti";
    }


    // =========================
    // NUOVO DOCUMENTO
    // =========================

    // Mostra il form per caricare un nuovo documento.
    @GetMapping("/nuovo")
    public String nuovoDocumento(Model model) {

        model.addAttribute(
                "documento",
                new DocumentoDTO()
        );

        return "utente/documento-form";
    }


    // =========================
    // DETTAGLIO
    // =========================

    // Mostra il dettaglio di un documento.
    //
    // L'utente può visualizzare solo i propri documenti.
    @GetMapping("/{id}")
    public String dettaglioDocumento(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        Documento documento =
                documentoService.findById(id);

        if (!documento.getUtente().getId().equals(utente.getId())) {
            return "redirect:/documenti";
        }

        DocumentoDTO dto =
                documentoConverter.toDto(documento);

        model.addAttribute("documento", dto);

        return "utente/documento-dettaglio";
    }


    // =========================
    // CREAZIONE
    // =========================

    // Carica un nuovo documento.
    //
    // L'utente viene ricavato dall'autenticazione.
    // Il fileKey viene generato dal backend.
    @PostMapping
    public String salvaDocumento(
            Authentication authentication,
            DocumentoDTO dto,
            @RequestParam("file") MultipartFile file) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        documentoService.save(
                dto,
                utente,
                file
        );

        return "redirect:/documenti";
    }


    // =========================
    // ELIMINAZIONE
    // =========================

    // Elimina un documento.
    //
    // Il controllo di proprietà viene effettuato
    // dal DocumentoService.
    @PostMapping("/{id}/elimina")
    public String eliminaDocumento(
            @PathVariable Long id,
            Authentication authentication) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        documentoService.delete(
                id,
                utente
        );

        return "redirect:/documenti";
    }


    // =========================
    // FILE
    // =========================

    // Permette di visualizzare/scaricare il file.
    //
    // Il controllo di proprietà viene effettuato
    // dal DocumentoService.
    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> fileDocumento(
            @PathVariable Long id,
            Authentication authentication) {

        Utente utente =
                utenteService.findByEmail(authentication.getName());

        Resource file =
                documentoService.readFile(
                        id,
                        utente
                );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}