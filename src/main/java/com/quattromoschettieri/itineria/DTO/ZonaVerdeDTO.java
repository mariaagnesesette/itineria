package com.quattromoschettieri.itineria.DTO;

import java.math.BigDecimal;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.zonaVerde.TipoZonaVerde;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ZonaVerdeDTO {

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

    private BigDecimal areaMq;
    private BigDecimal areaMin;
    private BigDecimal areaMax;
    private TipoZonaVerde tipologia;
    private Boolean dogFriendly;
    private Boolean ristoro;
    private Boolean ciclabile;
}