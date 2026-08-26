package com.quattromoschettieri.itineria.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.evento.ImmagineEvento;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.eventoRepository.EventoRepository;
import com.quattromoschettieri.itineria.repository.eventoRepository.ImmagineEventoRepository;
import com.quattromoschettieri.itineria.services.documentoService.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImmagineEventoService {

    private final ImmagineEventoRepository immagineEventoRepository;
    private final EventoRepository eventoRepository;
    private final FileStorageService fileStorageService;

    @Transactional(readOnly = true)
    public List<ImmagineEvento> findByEventoId(Long eventoId) {
        return immagineEventoRepository.findByEventoIdOrderByOrdineAsc(eventoId);
    }

    @Transactional
    public void upload(Long eventoId, List<MultipartFile> files, Utente utente, boolean impostaComeCopertina) {
        Evento evento = findEvento(eventoId);
        verificaPermesso(evento, utente);

        int prossimoOrdine = immagineEventoRepository
                .findByEventoIdOrderByOrdineAsc(eventoId).size();

        ImmagineEvento primaCaricata = null;

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }

            String fileKey = fileStorageService.saveImmagine(file);

            ImmagineEvento immagine = ImmagineEvento.builder()
                    .evento(evento)
                    .fileKey(fileKey)
                    .ordine(prossimoOrdine++)
                    .build();

            immagine = immagineEventoRepository.save(immagine);

            if (primaCaricata == null) {
                primaCaricata = immagine;
            }
        }

        if (impostaComeCopertina && primaCaricata != null) {
            spostaInCopertina(primaCaricata, eventoId);
        }
    }

    @Transactional
    public void setCopertina(Long immagineId, Utente utente) {
        ImmagineEvento copertina = findImmagine(immagineId);
        verificaPermesso(copertina.getEvento(), utente);

        spostaInCopertina(copertina, copertina.getEvento().getId());
    }

    private void spostaInCopertina(ImmagineEvento copertina, Long eventoId) {
        List<ImmagineEvento> immagini = immagineEventoRepository
                .findByEventoIdOrderByOrdineAsc(eventoId);

        copertina.setOrdine(0);

        int ordine = 1;
        for (ImmagineEvento immagine : immagini) {
            if (immagine.getId().equals(copertina.getId())) {
                continue;
            }
            immagine.setOrdine(ordine++);
        }

        immagineEventoRepository.saveAll(immagini);
        immagineEventoRepository.save(copertina);
    }

    @Transactional
    public void delete(Long immagineId, Utente utente) {
        ImmagineEvento immagine = findImmagine(immagineId);

        verificaPermesso(immagine.getEvento(), utente);

        fileStorageService.delete(immagine.getFileKey());
        immagineEventoRepository.delete(immagine);
    }

    @Transactional(readOnly = true)
    public Resource readFile(Long immagineId) {
        ImmagineEvento immagine = findImmagine(immagineId);
        return fileStorageService.read(immagine.getFileKey());
    }

    @Transactional(readOnly = true)
    public String contentTypeFor(Long immagineId) {
        ImmagineEvento immagine = findImmagine(immagineId);
        String key = immagine.getFileKey().toLowerCase();

        if (key.endsWith(".png")) {
            return "image/png";
        }
        if (key.endsWith(".webp")) {
            return "image/webp";
        }
        return "image/jpeg";
    }

    private Evento findEvento(Long eventoId) {
        return eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento non trovato: " + eventoId));
    }

    private ImmagineEvento findImmagine(Long immagineId) {
        return immagineEventoRepository.findById(immagineId)
                .orElseThrow(() -> new IllegalArgumentException("Immagine non trovata: " + immagineId));
    }

    private void verificaPermesso(Evento evento, Utente utente) {
        if (utente.getRuolo() == Ruolo.ADMIN) {
            return;
        }

        if (utente.getRuolo() == Ruolo.MANAGER
                && evento.getLuogoInteresse() != null
                && evento.getLuogoInteresse().getManager() != null
                && evento.getLuogoInteresse().getManager().getId().equals(utente.getId())) {
            return;
        }

        throw new SecurityException("Non hai i permessi per modificare le immagini di questo evento");
    }
}
