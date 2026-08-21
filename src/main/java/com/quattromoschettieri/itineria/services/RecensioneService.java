package com.quattromoschettieri.itineria.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quattromoschettieri.itineria.DTO.RecensioneDTO;
import com.quattromoschettieri.itineria.entities.recensione.Recensione;
import com.quattromoschettieri.itineria.repository.LuogoInteresseRepository;
import com.quattromoschettieri.itineria.repository.RecensioneRepository;
import com.quattromoschettieri.itineria.repository.utenteRepository.UtenteRepository;
import com.quattromoschettieri.itineria.specification.RecensioneSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecensioneService {

    private final RecensioneRepository recensioneRepository;

    private final UtenteRepository utenteRepository;

    private final LuogoInteresseRepository luogoInteresseRepository;

    private Recensione toEntity(RecensioneDTO dto) {
        Recensione recensione = Recensione.builder()
                .voto(dto.getVoto())
                .commento(dto.getCommento())
                .utente(utenteRepository
                        .findById(dto.getIdUtente())
                        .orElseThrow(() -> new RuntimeException("Utente non trovato")))
                .luogoInteresse(luogoInteresseRepository
                        .findById(dto.getIdLuogoInteresse())
                        .orElseThrow(() -> new RuntimeException("Luogo di interesse non trovato")))
                .build();

        return recensione;
    }

    private RecensioneDTO toDto(Recensione recensione) {
        RecensioneDTO dto = new RecensioneDTO();

        dto.setId(recensione.getId());
        dto.setVoto(recensione.getVoto());
        dto.setCommento(recensione.getCommento());
        dto.setIdUtente(recensione.getUtente().getId());
        dto.setIdLuogoInteresse(recensione.getLuogoInteresse().getId());

        return dto;
    }

    private void updateEntity(Recensione recensione, RecensioneDTO dto) {
        recensione.setVoto(dto.getVoto());
        recensione.setCommento(dto.getCommento());
    }

    public Page<Recensione> findAll(Pageable pageable) {
        return recensioneRepository.findAll(pageable);
    }

    public Recensione findById(Long id) {
        return recensioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recensione non trovata"));
    }

    public Page<Recensione> findByLuogoInteresseId(Long id, Pageable pageable) {
        return recensioneRepository.findByLuogoInteresseId(id, pageable);
    }

    public Page<Recensione> findByUtenteId(Long id, Pageable pageable) {
        return recensioneRepository.findByUtenteId(id, pageable);
    }

    public Page<Recensione> findByVoto(Integer voto, Pageable pageable) {
        return recensioneRepository.findByVoto(voto, pageable);
    }

    public RecensioneDTO save(RecensioneDTO dto) {
        Recensione recensione = toEntity(dto);

        Recensione salvata = recensioneRepository.save(recensione);

        return toDto(salvata);
    }

    public RecensioneDTO update(Long id, RecensioneDTO dto) {
        Recensione recensione = findById(id);

        updateEntity(recensione, dto);

        Recensione salvata = recensioneRepository.save(recensione);

        return toDto(salvata);
    }

    public void delete(Long id) {
        Recensione recensione = findById(id);

        recensioneRepository.delete(recensione);
    }

    public Page<Recensione> search(
        RecensioneDTO dto,
        LocalDateTime creataDopo,
        LocalDateTime creataPrima,
        Pageable pageable) {

        Specification<Recensione> spec = Specification
            .where(RecensioneSpecification.perUtente(dto.getIdUtente()))
            .and(RecensioneSpecification.perLuogo(dto.getIdLuogoInteresse()))
            .and(RecensioneSpecification.perVoto(dto.getVoto()))
            .and(RecensioneSpecification.dopoData(creataDopo))
            .and(RecensioneSpecification.primaData(creataPrima));

         return recensioneRepository.findAll(spec, pageable);
        }
}
