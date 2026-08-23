package com.quattromoschettieri.itineria.DTO.utenteDTO;

import com.quattromoschettieri.itineria.entities.utente.documento.StatoDocumento;
import com.quattromoschettieri.itineria.entities.utente.documento.TipoDocumento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DocumentoDTO {

    private Long id;

    private TipoDocumento tipoDocumento;

    private String fileKey;

    private String codiceIdentificativo;

    private StatoDocumento stato;

    private Long idUtente;
}