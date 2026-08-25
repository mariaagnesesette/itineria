package com.quattromoschettieri.itineria.entities.luogoInteresse;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;

import com.quattromoschettieri.itineria.entities.GenericEntity;
import com.quattromoschettieri.itineria.entities.citta.Citta;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.recensione.Recensione;
import com.quattromoschettieri.itineria.entities.utente.Utente;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="luoghi_interesse",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_luogo_nome_citta",
            columnNames = {"nome", "id_citta"}
        )
    }
)
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "orariApertura")
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
    @EqualsAndHashCode.Exclude
    private Citta citta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_manager")
    @EqualsAndHashCode.Exclude
    private Utente manager;

    @OneToMany(mappedBy = "luogoInteresse", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<OrarioApertura> orariApertura = new ArrayList<>();

    @OneToMany(mappedBy = "luogoInteresse")
    @EqualsAndHashCode.Exclude
    private List<Recensione> recensioni;

    @OneToMany(mappedBy = "luogoInteresse")
    @EqualsAndHashCode.Exclude
    private List<Evento> eventi;

    @ManyToMany(mappedBy = "luoghiPreferiti")
    @EqualsAndHashCode.Exclude
    private Set<Utente> utentiPreferiti;

}
