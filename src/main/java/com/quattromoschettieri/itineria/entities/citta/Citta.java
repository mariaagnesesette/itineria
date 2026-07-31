package com.quattromoschettieri.itineria.entities.citta;

import com.quattromoschettieri.itineria.entities.GenericEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "citta")
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Citta extends GenericEntity {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "regione", nullable = false)
    private Regione regione;

    @Column(name = "descrizione", nullable = false, length = 5000)
    private String descrizione;
}
