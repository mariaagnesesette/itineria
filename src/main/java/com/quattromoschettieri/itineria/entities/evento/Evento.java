package com.quattromoschettieri.itineria.entities.evento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.quattromoschettieri.itineria.entities.GenericEntity;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Utente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "eventi")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Evento extends GenericEntity{

    @Column(name = "descrizione", length = 5000, nullable = false)
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia", nullable = false)
    private TipologiaEvento tipologiaEvento;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "prezzo", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;

    @Builder.Default
    @Column(name = "prenotazione", nullable = false)
    private boolean prenotazione = false;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "pubblico", nullable = false)
    private PubblicoEvento pubblicoEvento = PubblicoEvento.TUTTI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_luogo_interesse",
                referencedColumnName = "id",
                nullable = false
    )
    @EqualsAndHashCode.Exclude
    private LuogoInteresse luogoInteresse;

    @OneToMany(mappedBy = "evento")
    @EqualsAndHashCode.Exclude
    private List<DataEvento> dataEvento;

    @ManyToMany(mappedBy = "eventiPreferiti")
    @EqualsAndHashCode.Exclude
    private Set<Utente> utentiPreferitori;

}
