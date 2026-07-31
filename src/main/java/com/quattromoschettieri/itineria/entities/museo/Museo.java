package com.quattromoschettieri.itineria.entities.museo;

import com.quattromoschettieri.itineria.entities.Citta;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "musei")
@SuperBuilder
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id_luogo_interesse")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Museo extends LuogoInteresse{

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia")
    private TipologiaMuseo tipologia;

    @Column(name = "guide_prenotabili", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean guidaPrenotabile = false;

    @Column(name = "bar_interno", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean barInterno = false;

public Museo(String nome,
             String descrizione,
             Accessibilita accessibilita,
             String indirizzo,
             boolean sempreAperto,
             String link,
             String numero,
             String email,
             Citta citta,
             TipologiaMuseo tipologia,
             boolean guidaPrenotabile,
             boolean barInterno) {

    this.setNome(nome);
    this.setDescrizione(descrizione);
    this.setTipoLuogo(Tipo.MUSEO);
    this.setAccessibilita(accessibilita);
    this.setIndirizzo(indirizzo);
    this.setSempreAperto(sempreAperto);
    this.setLink(link);
    this.setNumero(numero);
    this.setEmail(email);
    this.setCitta(citta);

    this.tipologia = tipologia;
    this.guidaPrenotabile = guidaPrenotabile;
    this.barInterno = barInterno;
}

}
