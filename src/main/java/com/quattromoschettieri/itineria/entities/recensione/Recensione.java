package com.quattromoschettieri.itineria.entities.recensione;

import com.quattromoschettieri.itineria.entities.GenericEntity;
import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.utente.Utente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recensioni")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Recensione extends GenericEntity{

    @Column(name = "voto", columnDefinition = "TINYINT" , nullable = false)
    @Min(1)
    @Max(5)
    private Integer voto;

    @Column(name = "commento", length = 5000)
    private String commento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente",
                referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_luogo_interesse",
                referencedColumnName = "id",
                nullable = false)
    @EqualsAndHashCode.Exclude
    private LuogoInteresse luogoInteresse;


}
