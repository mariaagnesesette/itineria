package com.quattromoschettieri.itineria.controllers;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.services.ImmagineLuogoService;
import com.quattromoschettieri.itineria.services.utenteService.UtenteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/luoghi/{luogoId}/immagini")
@RequiredArgsConstructor
public class ImmagineLuogoController {

    private final ImmagineLuogoService immagineLuogoService;
    private final UtenteService utenteService;

    @PostMapping
    public String upload(
            @PathVariable Long luogoId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("redirectUrl") String redirectUrl,
            @RequestParam(value = "impostaCopertina", required = false, defaultValue = "false") boolean impostaCopertina,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());
        immagineLuogoService.upload(luogoId, files, utente, impostaCopertina);

        return "redirect:" + safeRedirect(redirectUrl);
    }

    @PostMapping("/{immagineId}/copertina")
    public String copertina(
            @PathVariable Long luogoId,
            @PathVariable Long immagineId,
            @RequestParam("redirectUrl") String redirectUrl,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());
        immagineLuogoService.setCopertina(immagineId, utente);

        return "redirect:" + safeRedirect(redirectUrl);
    }

    @PostMapping("/{immagineId}/elimina")
    public String elimina(
            @PathVariable Long luogoId,
            @PathVariable Long immagineId,
            @RequestParam("redirectUrl") String redirectUrl,
            Authentication authentication) {

        Utente utente = utenteService.findByEmail(authentication.getName());
        immagineLuogoService.delete(immagineId, utente);

        return "redirect:" + safeRedirect(redirectUrl);
    }

    @GetMapping("/{immagineId}/file")
    public ResponseEntity<Resource> file(
            @PathVariable Long luogoId,
            @PathVariable Long immagineId) {

        Resource resource = immagineLuogoService.readFile(immagineId);
        MediaType mediaType = MediaType.parseMediaType(immagineLuogoService.contentTypeFor(immagineId));

        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }

    private String safeRedirect(String redirectUrl) {
        if (redirectUrl == null || !redirectUrl.startsWith("/") || redirectUrl.startsWith("//")) {
            return "/";
        }
        return redirectUrl;
    }
}
