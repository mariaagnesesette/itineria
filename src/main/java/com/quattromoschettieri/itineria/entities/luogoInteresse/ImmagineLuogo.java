package com.quattromoschettieri.itineria.entities.luogoInteresse;

import com.quattromoschettieri.itineria.entities.GenericEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "immagini_luogo")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImmagineLuogo extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_luogo_interesse", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private LuogoInteresse luogoInteresse;

    @Column(name = "file_key", length = 255, nullable = false)
    private String fileKey;

    @Builder.Default
    @Column(name = "ordine", nullable = false)
    private Integer ordine = 0;

}
