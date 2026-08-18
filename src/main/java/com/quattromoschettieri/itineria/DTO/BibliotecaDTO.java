package com.quattromoschettieri.itineria.DTO;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;

public record BibliotecaDTO(
        String nome,
        Boolean pubblico,
        Boolean wifi,
        Boolean areaComputer,
        Boolean areaBambini,
        Accessibilita accessibilita,
        Boolean sempreAperto,
        Long idCitta
) {

}
