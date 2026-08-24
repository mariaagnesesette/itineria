package com.quattromoschettieri.itineria.entities.ristorante;

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
@Table(name = "ristoranti")
@SuperBuilder
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Ristorante extends LuogoInteresse{

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cucina", nullable = false )
    private TipoCucina tipoCucina;

    @Enumerated(EnumType.STRING)
    @Column(name = "fascia_prezzo", nullable = false)
    private FasciaPrezzoRistorante fasciaPrezzo;

    @Column(name = "dog_friendly", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean dogFriendly = false;

    @Column(name = "per_celiaci", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean perCeliaci = false;

    @Column(name = "posti_esterni", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean postiEsterni = false;

    public static RistoranteBuilder<?, ?> builder() {
        return new RistoranteBuilderImpl().tipoLuogo(Tipo.RISTORANTE);
    }


}
