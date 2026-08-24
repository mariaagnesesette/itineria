package com.quattromoschettieri.itineria.services.documentoService;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quattromoschettieri.itineria.DTO.utenteDTO.DocumentoDTO;
import com.quattromoschettieri.itineria.converters.utenteConverter.DocumentoConverter;
import com.quattromoschettieri.itineria.entities.utente.Utente;
import com.quattromoschettieri.itineria.entities.utente.documento.Documento;
import com.quattromoschettieri.itineria.entities.utente.documento.StatoDocumento;
import com.quattromoschettieri.itineria.entities.utente.documento.TipoDocumento;
import com.quattromoschettieri.itineria.repository.utenterepository.DocumentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    private final DocumentoConverter documentoConverter;

    private final FileStorageService fileStorageService;

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

    public List<Documento> findByUtente(Utente utente) {
        return documentoRepository.findByUtente(utente);
    }

    public DocumentoDTO save(DocumentoDTO dto, Utente utente, MultipartFile file) {
        String fileKey = fileStorageService.save(file);

        Documento documento = documentoConverter.toEntity(dto, utente);
        documento.setFileKey(fileKey);
        
        Documento salvato = documentoRepository.save(documento);

        return documentoConverter.toDto(salvato);
    }

    public void delete(Long id) {
        Documento documento = findById(id);

        fileStorageService.delete(documento.getFileKey());

        documentoRepository.delete(documento);
    }

    public DocumentoDTO setStato(Long id, StatoDocumento stato) {
        Documento documento = findById(id);

        documento.setStato(stato);

        Documento aggiornato = documentoRepository.save(documento);

        return documentoConverter.toDto(aggiornato);
    }

    public Resource readFile(Long id) {
        Documento documento = findById(id);

        return fileStorageService.read(documento.getFileKey());
    }
}