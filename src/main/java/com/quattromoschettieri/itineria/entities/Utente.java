package com.quattromoschettieri.itineria.entities;

import java.time.LocalDate;
import java.util.List;

import com.quattromoschettieri.itineria.entities.enums.Ruolo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class Utente extends GenericEntity{

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "cognome", length = 50, nullable = false)
    private String cognome;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Column(name = "nickname", length = 30)
    private String nickname;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false)
    private Ruolo ruolo = Ruolo.USER;

    @OneToMany(mappedBy = "utente")
    private List<Documento> documenti;





}
