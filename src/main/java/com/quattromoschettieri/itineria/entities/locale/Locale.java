package com.quattromoschettieri.itineria.entities.locale;

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
@Table(name = "locali")
@SuperBuilder
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Locale extends LuogoInteresse{

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_locale", nullable = false)
    private TipoLocale tipoLocale;

    @Enumerated(EnumType.STRING)
    @Column(name = "atmosfera", nullable = false)
    private Atmosfera atmosfera;

    @Enumerated(EnumType.STRING)
    @Column(name = "fascia_prezzo", nullable = false)
    private FasciaPrezzoLocale fasciaPrezzo;

    @Column(name = "apertura_serale", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean aperturaSerale = false;

    @Column(name = "posti_esterni", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean postiEsterni = false;

    @Column(name = "per_celiaci", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean perCeliaci = false;

    public static LocaleBuilder<?, ?> builder() {
        return new LocaleBuilderImpl().tipoLuogo(Tipo.LOCALE);
    }


}
