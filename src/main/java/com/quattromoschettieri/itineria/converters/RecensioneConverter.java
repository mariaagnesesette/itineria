package com.quattromoschettieri.itineria.converters;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.RecensioneDTO;
import com.quattromoschettieri.itineria.entities.recensione.Recensione;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class RecensioneConverter {

    private final LuogoInteresseRepository luogoInteresseRepository;

       public Recensione toEntity(RecensioneDTO dto, Utente utente) {
        Recensione recensione = Recensione.builder()
                .voto(dto.getVoto())
                .commento(dto.getCommento())
                .utente(utente)
                .luogoInteresse(luogoInteresseRepository
                        .findById(dto.getIdLuogoInteresse())
                        .orElseThrow(() -> new RuntimeException("Luogo di interesse non trovato")))
                .build();

        return recensione;
    }

    public RecensioneDTO toDto(Recensione recensione) {
        RecensioneDTO dto = new RecensioneDTO();

        dto.setId(recensione.getId());
        dto.setVoto(recensione.getVoto());
        dto.setCommento(recensione.getCommento());
        dto.setIdUtente(recensione.getUtente().getId());
        dto.setIdLuogoInteresse(recensione.getLuogoInteresse().getId());

        return dto;
    }

    public void updateEntity(Recensione recensione, RecensioneDTO dto) {
        recensione.setVoto(dto.getVoto());
        recensione.setCommento(dto.getCommento());
    }
}
