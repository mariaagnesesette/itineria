package com.quattromoschettieri.itineria.entities.biblioteca;

import com.quattromoschettieri.itineria.entities.luogoInteresse.LuogoInteresse;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Tipo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "biblioteche")
@SuperBuilder
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id_luogo_interesse")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Biblioteca extends LuogoInteresse{

    @Column(name = "pubblico", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean pubblico = false;

    @Column(name = "wifi", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean wifi = false;

    @Column(name = "area_computer", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean areaComputer = false;

    @Column(name = "area_bambini", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean areaBambini = false;

    public static BibliotecaBuilder<?, ?> builder() {
        return new BibliotecaBuilderImpl().tipoLuogo(Tipo.BIBLIOTECA);
    }

}
