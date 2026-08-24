package com.quattromoschettieri.itineria.converters.utenteConverter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.utenteDTO.UtenteDTO;
import com.quattromoschettieri.itineria.entities.utente.Ruolo;
import com.quattromoschettieri.itineria.entities.utente.Utente;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class UtenteConverter{

    private final PasswordEncoder passwordEncoder;

    public Utente toEntity(UtenteDTO dto) {
        Utente utente = Utente.builder()
                .nome(dto.getNome())
                .cognome(dto.getCognome())
                .dataNascita(dto.getDataNascita())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .ruolo(Ruolo.USER)
                .build();

        return utente;
    }

    public UtenteDTO toDto(Utente utente) {
        UtenteDTO dto = new UtenteDTO();

        dto.setId(utente.getId());
        dto.setNome(utente.getNome());
        dto.setCognome(utente.getCognome());
        dto.setDataNascita(utente.getDataNascita());
        dto.setUsername(utente.getUsername());
        dto.setEmail(utente.getEmail());

        return dto;
    }

    public void updateEntity(Utente utente, UtenteDTO dto) {
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setDataNascita(dto.getDataNascita());
        utente.setUsername(dto.getUsername());
        utente.setEmail(dto.getEmail());
    }

    public void updatePassword(
            Utente utente,
            String vecchiaPassword,
            String nuovaPassword) {

        if (!passwordEncoder.matches(vecchiaPassword, utente.getPassword())) {
            throw new RuntimeException("Password attuale non corretta");
        }

        utente.setPassword(passwordEncoder.encode(nuovaPassword));
    }
}
