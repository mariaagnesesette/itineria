package com.quattromoschettieri.itineria.DTO;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BibliotecaDTO {

    private Long id;

    private String nome;
    private String descrizione;
    private Tipo tipoLuogo;
    private Accessibilita accessibilita;
    private String indirizzo;
    private Boolean sempreAperto;
    private String link;
    private String numero;
    private String email;

    private Long idCitta;

    private Boolean pubblico;
    private Boolean wifi;
    private Boolean areaComputer;
    private Boolean areaBambini;
}
