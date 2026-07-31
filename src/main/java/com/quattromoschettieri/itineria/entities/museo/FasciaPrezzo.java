package com.quattromoschettieri.itineria.entities.museo;

import com.quattromoschettieri.itineria.entities.GenericEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fasce_prezzo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FasciaPrezzo extends GenericEntity{

    @Column(name = "nome", nullable = false)
    private String nome;

}
