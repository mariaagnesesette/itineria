package com.quattromoschettieri.itineria.services.documentoService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.utenteDTO.DocumentoDTO;
import com.quattromoschettieri.itineria.converters.DocumentoConverter;
import com.quattromoschettieri.itineria.entities.utente.documento.Documento;
import com.quattromoschettieri.itineria.entities.utente.documento.TipoDocumento;
import com.quattromoschettieri.itineria.repository.utenterepository.DocumentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    private final DocumentoConverter documentoConverter;

    public Page<Documento> findAll(Pageable pageable) {
        return documentoRepository.findAll(pageable);
    }

    public Documento findById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento non trovato"));
    }

    public Documento findByCodiceIdentificativo(String codiceIdentificativo) {
        return documentoRepository.findByCodiceIdentificativo(codiceIdentificativo)
                .orElseThrow(() -> new RuntimeException("Documento non trovato"));
    }

    public Page<Documento> findByTipoDocumento(TipoDocumento tipoDocumento, Pageable pageable) {
        return documentoRepository.findByTipoDocumento(tipoDocumento, pageable);
    }

    public List<Documento> findByUtenteId(Long id) {
        return documentoRepository.findByUtenteId(id);
    }

    public DocumentoDTO save(DocumentoDTO dto) {
        Documento documento = documentoConverter.toEntity(dto);
        Documento salvato = documentoRepository.save(documento);

        return documentoConverter.toDto(salvato);
    }

    public DocumentoDTO update(Long id, DocumentoDTO dto) {
        Documento documento = findById(id);
        documentoConverter.updateEntity(documento, dto);
        Documento aggiornato = documentoRepository.save(documento);

        return documentoConverter.toDto(aggiornato);
    }

    public void delete(Long id) {
        Documento documento = findById(id);

        documentoRepository.delete(documento);
    }
}