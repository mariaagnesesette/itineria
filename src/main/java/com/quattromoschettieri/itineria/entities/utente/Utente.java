package com.quattromoschettieri.itineria.entities.utente;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.quattromoschettieri.itineria.entities.GenericEntity;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.recensione.Recensione;
import com.quattromoschettieri.itineria.entities.utente.documento.Documento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Utente extends GenericEntity{

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "cognome", length = 50, nullable = false)
    private String cognome;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Column(name = "username", length = 30)
    private String username;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false)
    private Ruolo ruolo = Ruolo.USER;

    @OneToMany(mappedBy = "utente")
    @EqualsAndHashCode.Exclude
    private List<Documento> documenti;

    @OneToMany(mappedBy = "utente")
    @EqualsAndHashCode.Exclude
    private List<Recensione> recensioni;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "utente_luoghi_preferiti",
        joinColumns = @JoinColumn(name = "id_utente"),
        inverseJoinColumns = @JoinColumn(name = "id_luogo_interesse")
    )
    @EqualsAndHashCode.Exclude
    private Set<LuogoInteresse> luoghiPreferiti;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "utente_eventi_preferiti",
        joinColumns = @JoinColumn(name = "id_utente"),
        inverseJoinColumns = @JoinColumn(name = "id_evento")
    )
    @EqualsAndHashCode.Exclude
    private Set<Evento> eventiPreferiti;
}
