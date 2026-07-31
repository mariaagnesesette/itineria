package com.quattromoschettieri.itineria.entities.utente.documento;

import com.quattromoschettieri.itineria.entities.GenericEntity;
import com.quattromoschettieri.itineria.entities.utente.Utente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documenti")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Documento extends GenericEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoDocumento tipoDocumento;

    /*
     * Riferimento al file salvato nello storage.
     * Esempio:
     * users/15/documenti/carta_identita_abc123.jpg
     */
    @Column(name = "file_key", length = 255, nullable = false)
    private String fileKey;

    @Column(name = "codice_identificativo", length = 20, nullable = false)
    private String codiceIdentificativo;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoDocumento stato = StatoDocumento.IN_ATTESA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", 
                referencedColumnName = "id",
                nullable = false)
    private Utente utente;


}
