package com.quattromoschettieri.itineria.DTO;

import java.math.BigDecimal;
import java.util.List;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.museo.PrezzoMuseo;
import com.quattromoschettieri.itineria.entities.museo.TipologiaMuseo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MuseoDTO {

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

    private TipologiaMuseo tipologia;
    private Boolean guidaPrenotabile;
    private Boolean barInterno;
    private List<PrezzoMuseo> prezzi;
    private BigDecimal prezzoMin;
    private BigDecimal prezzoMax;

}
