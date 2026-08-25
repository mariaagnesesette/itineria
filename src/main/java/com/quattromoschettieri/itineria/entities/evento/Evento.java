package com.quattromoschettieri.itineria.entities.evento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.quattromoschettieri.itineria.entities.GenericEntity;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Utente;

import jakarta.persistence.CascadeType;
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

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

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

    @Builder.Default
    @OneToMany(mappedBy = "evento",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private List<DataEvento> dateEvento = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "eventiPreferiti")
    @EqualsAndHashCode.Exclude
    private Set<Utente> utentiPreferitori = new HashSet<>();

    public String getDataTesto() {
        if (dateEvento == null || dateEvento.isEmpty()) {
            return "Data da definire";
        }

        DataEvento prossimaData = dateEvento.stream()
            .min(Comparator.comparing(DataEvento::getDataInizio))
            .orElseThrow();

        DateTimeFormatter giornoMese = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ITALIAN);
        LocalDate inizio = prossimaData.getDataInizio();
        LocalDate fine = prossimaData.getDataFine();

        if (inizio.equals(fine)) {
            return inizio.format(giornoMese);
        }
        if (inizio.getMonth() == fine.getMonth() && inizio.getYear() == fine.getYear()) {
            return inizio.format(DateTimeFormatter.ofPattern("d")) + " - " + fine.format(giornoMese);
        }
        return inizio.format(giornoMese) + " - " + fine.format(giornoMese);
    }

    public boolean isGratuito() {
        return prezzo != null && prezzo.compareTo(BigDecimal.ZERO) == 0;
    }

}
