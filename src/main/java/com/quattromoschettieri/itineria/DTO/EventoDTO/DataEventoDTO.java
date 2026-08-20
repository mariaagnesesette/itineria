package com.quattromoschettieri.itineria.DTO.EventoDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record DataEventoDTO(
        LocalDate dataInizio,
        LocalDate dataFine,
        LocalTime oraInizio,
        LocalTime oraFine
) {

}
