package com.quattromoschettieri.itineria.DTO.EventoDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataEventoDTO {

    private Long id;
    
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private LocalTime oraInizio;
    private LocalTime oraFine;
}

