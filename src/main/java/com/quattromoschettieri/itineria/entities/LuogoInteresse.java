package com.quattromoschettieri.itineria.entities;

import com.quattromoschettieri.itineria.entities.enums.Accessibilita;
import com.quattromoschettieri.itineria.entities.enums.Tipo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="luoghi_interesse")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class LuogoInteresse extends GenericEntity{

    @Column(name = "nome")
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipoLuogo;

    @Enumerated(EnumType.STRING)
    @Column(name = "accessibilita")
    private Accessibilita accessibilita;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "sempre_aperto")
    private boolean sempreAperto;

    @Column(name = "link_sito")
    private String link;

    @Column(name = "numero_telefono", unique = true)
    private String numero;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_citta",
                referencedColumnName = "id")
    private Citta citta;


}
