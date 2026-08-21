package com.quattromoschettieri.itineria.DTO;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;
import com.quattromoschettieri.itineria.entities.ristorante.FasciaPrezzoRistorante;
import com.quattromoschettieri.itineria.entities.ristorante.TipoCucina;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RistoranteDTO {

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

    private TipoCucina tipoCucina;
    private FasciaPrezzoRistorante fasciaPrezzo;
    private Boolean dogFriendly;
    private Boolean perCeliaci;
    private Boolean postiEsterni;
}