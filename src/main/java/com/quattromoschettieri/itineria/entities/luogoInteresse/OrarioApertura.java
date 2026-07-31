package com.quattromoschettieri.itineria.entities.luogoInteresse;

import java.time.LocalTime;

import com.quattromoschettieri.itineria.entities.GenericEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "orario_apertura")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrarioApertura extends GenericEntity{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_luogo_interesse",
                referencedColumnName = "id",
                nullable = false)
    @EqualsAndHashCode.Exclude
    private LuogoInteresse luogoInteresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "giorno")
    private Giorno giorno;

    @Column(name = "apertura")
    private LocalTime orarioApertura;

    @Column(name = "chiusura")
    private LocalTime orarioChiusura;

}
