package com.quattromoschettieri.itineria.DTO.utenteDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UtenteDTO {

    private Long id;

    private String nome;

    private String cognome;

    private LocalDate dataNascita;

    private String username;

    private String email;

    private String password;
}