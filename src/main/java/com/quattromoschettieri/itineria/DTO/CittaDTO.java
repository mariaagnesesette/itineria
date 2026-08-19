package com.quattromoschettieri.itineria.DTO;

import com.quattromoschettieri.itineria.entities.citta.Regione;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CittaDTO {

    private Long id;
    private String nome;
    private Regione regione;
    private String descrizione;

}
