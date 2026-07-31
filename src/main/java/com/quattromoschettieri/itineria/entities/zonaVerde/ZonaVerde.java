package com.quattromoschettieri.itineria.entities.zonaVerde;

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
@Table(name = "zone_verdi")
@SuperBuilder
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id_luogo_interesse")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ZonaVerde extends LuogoInteresse{

    @Column(name = "area_mq", nullable = false, precision = 8, scale = 2 )
    private double areaMq;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia", nullable = false)
    private TipoZonaVerde tipologia;

    @Column(name = "dog_friendly", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean dogFriendly = false;

    @Column(name = "ristoro", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean ristoro = false;

    @Column(name = "ciclabile", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean ciclabile = false;

    public static ZonaVerdeBuilder<?, ?> builder() {
        return new ZonaVerdeBuilderImpl().tipoLuogo(Tipo.ZONA_VERDE);
    }
}
