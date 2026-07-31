package com.quattromoschettieri.itineria.entities.museo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "prezzi_museo",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_museo_fascia",
                columnNames = {"id_museo","id_fascia_prezzo"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PrezzoMuseo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_museo",
                referencedColumnName = "id",
                nullable = false)
    @EqualsAndHashCode.Exclude
    private Museo museo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fascia_prezzo",
                referencedColumnName = "id",
                nullable = false)
    @EqualsAndHashCode.Exclude
    private FasciaPrezzo fasciaPrezzo;

    @Column(name = "prezzo", nullable = false, precision = 6, scale = 2)
    private Double prezzo;

}
