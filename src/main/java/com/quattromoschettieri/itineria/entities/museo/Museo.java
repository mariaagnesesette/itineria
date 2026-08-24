package com.quattromoschettieri.itineria.entities.museo;

import java.util.ArrayList;
import java.util.List;

import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Museo extends LuogoInteresse {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia")
    private TipologiaMuseo tipologia;

    @Column(name = "guide_prenotabili", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean guidaPrenotabile = false;

    @Column(name = "bar_interno", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean barInterno = false;

    @OneToMany(mappedBy = "museo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<PrezzoMuseo> prezzi = new ArrayList<>();

    public static MuseoBuilder<?, ?> builder() {
        return new MuseoBuilderImpl().tipoLuogo(Tipo.MUSEO);
    }

}
