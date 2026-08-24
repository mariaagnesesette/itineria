package com.quattromoschettieri.itineria.converters.utenteConverter;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.utenteDTO.DocumentoDTO;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.entities.utente.documento.Documento;
<<<<<<< HEAD:src/main/java/com/quattromoschettieri/itineria/converters/utenteConverter/DocumentoConverter.java
=======
import com.quattromoschettieri.itineria.repository.utenterepository.UtenteRepository;

import lombok.RequiredArgsConstructor;
>>>>>>> giulia:src/main/java/com/quattromoschettieri/itineria/converters/DocumentoConverter.java

@Component
public class DocumentoConverter {

    public Documento toEntity(DocumentoDTO dto, Utente utente) {

        return Documento.builder()
                .tipoDocumento(dto.getTipoDocumento())
                .codiceIdentificativo(dto.getCodiceIdentificativo())
                .utente(utente)
                .build();
    }

    public DocumentoDTO toDto(Documento documento) {

        DocumentoDTO dto = new DocumentoDTO();

        dto.setId(documento.getId());
        dto.setTipoDocumento(documento.getTipoDocumento());
        dto.setCodiceIdentificativo(documento.getCodiceIdentificativo());
        dto.setFileKey(documento.getFileKey());
        dto.setStato(documento.getStato());
        dto.setIdUtente(documento.getUtente().getId());

        return dto;
    }
}