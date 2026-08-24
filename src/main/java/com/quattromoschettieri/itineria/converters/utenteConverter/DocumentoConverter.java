package com.quattromoschettieri.itineria.converters.utenteConverter;

import org.springframework.stereotype.Component;

import com.quattromoschettieri.itineria.DTO.utenteDTO.DocumentoDTO;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.entities.utente.documento.Documento;
import com.quattromoschettieri.itineria.repository.utenteRepository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocumentoConverter {

    private final UtenteRepository utenteRepository;

    public Documento toEntity(DocumentoDTO dto) {

        Utente utente = utenteRepository.findById(dto.getIdUtente())
                .orElseThrow(() ->
                        new RuntimeException("Utente non trovato"));

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
        dto.setIdUtente(documento.getUtente().getId());

        return dto;
    }

}