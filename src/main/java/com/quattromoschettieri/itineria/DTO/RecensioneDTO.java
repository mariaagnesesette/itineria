package com.quattromoschettieri.itineria.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecensioneDTO {

    private Long id;
    
    private Integer voto;

    private String commento;

    private Long idUtente;

    private Long idLuogoInteresse;
}