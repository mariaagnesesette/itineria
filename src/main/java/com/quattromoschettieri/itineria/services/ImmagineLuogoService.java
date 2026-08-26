package com.quattromoschettieri.itineria.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.quattromoschettieri.itineria.entities.luogoInteresse.ImmagineLuogo;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.ImmagineLuogoRepository;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.services.documentoService.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImmagineLuogoService {

    private final ImmagineLuogoRepository immagineLuogoRepository;
    private final LuogoInteresseRepository luogoInteresseRepository;
    private final FileStorageService fileStorageService;

    @Transactional(readOnly = true)
    public List<ImmagineLuogo> findByLuogoId(Long luogoId) {
        return immagineLuogoRepository.findByLuogoInteresseIdOrderByOrdineAsc(luogoId);
    }

    @Transactional
    public void upload(Long luogoId, List<MultipartFile> files, Utente utente) {
        upload(luogoId, files, utente, false);
    }

    @Transactional
    public void upload(Long luogoId, List<MultipartFile> files, Utente utente, boolean impostaComeCopertina) {
        LuogoInteresse luogo = findLuogo(luogoId);
        verificaPermesso(luogo, utente);

        int prossimoOrdine = immagineLuogoRepository
                .findByLuogoInteresseIdOrderByOrdineAsc(luogoId).size();

        ImmagineLuogo primaCaricata = null;

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }

            String fileKey = fileStorageService.saveImmagine(file);

            ImmagineLuogo immagine = ImmagineLuogo.builder()
                    .luogoInteresse(luogo)
                    .fileKey(fileKey)
                    .ordine(prossimoOrdine++)
                    .build();

            immagine = immagineLuogoRepository.save(immagine);

            if (primaCaricata == null) {
                primaCaricata = immagine;
            }
        }

        if (impostaComeCopertina && primaCaricata != null) {
            spostaInCopertina(primaCaricata, luogoId);
        }
    }

    @Transactional
    public void setCopertina(Long immagineId, Utente utente) {
        ImmagineLuogo copertina = findImmagine(immagineId);
        verificaPermesso(copertina.getLuogoInteresse(), utente);

        spostaInCopertina(copertina, copertina.getLuogoInteresse().getId());
    }

    private void spostaInCopertina(ImmagineLuogo copertina, Long luogoId) {
        List<ImmagineLuogo> immagini = immagineLuogoRepository
                .findByLuogoInteresseIdOrderByOrdineAsc(luogoId);

        copertina.setOrdine(0);

        int ordine = 1;
        for (ImmagineLuogo immagine : immagini) {
            if (immagine.getId().equals(copertina.getId())) {
                continue;
            }
            immagine.setOrdine(ordine++);
        }

        immagineLuogoRepository.saveAll(immagini);
        immagineLuogoRepository.save(copertina);
    }

    @Transactional
    public void delete(Long immagineId, Utente utente) {
        ImmagineLuogo immagine = findImmagine(immagineId);

        verificaPermesso(immagine.getLuogoInteresse(), utente);

        fileStorageService.delete(immagine.getFileKey());
        immagineLuogoRepository.delete(immagine);
    }

    @Transactional(readOnly = true)
    public Resource readFile(Long immagineId) {
        ImmagineLuogo immagine = findImmagine(immagineId);
        return fileStorageService.read(immagine.getFileKey());
    }

    @Transactional(readOnly = true)
    public String contentTypeFor(Long immagineId) {
        ImmagineLuogo immagine = findImmagine(immagineId);
        String key = immagine.getFileKey().toLowerCase();

        if (key.endsWith(".png")) {
            return "image/png";
        }
        if (key.endsWith(".webp")) {
            return "image/webp";
        }
        return "image/jpeg";
    }

    private LuogoInteresse findLuogo(Long luogoId) {
        return luogoInteresseRepository.findById(luogoId)
                .orElseThrow(() -> new IllegalArgumentException("Luogo non trovato: " + luogoId));
    }

    private ImmagineLuogo findImmagine(Long immagineId) {
        return immagineLuogoRepository.findById(immagineId)
                .orElseThrow(() -> new IllegalArgumentException("Immagine non trovata: " + immagineId));
    }

    private void verificaPermesso(LuogoInteresse luogo, Utente utente) {
        if (utente.getRuolo() == Ruolo.ADMIN) {
            return;
        }

        if (utente.getRuolo() == Ruolo.MANAGER
                && luogo.getManager() != null
                && luogo.getManager().getId().equals(utente.getId())) {
            return;
        }

        throw new SecurityException("Non hai i permessi per modificare le immagini di questo luogo");
    }
}
