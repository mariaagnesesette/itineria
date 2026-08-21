package com.quattromoschettieri.itineria.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.quattromoschettieri.itineria.entities.recensione.Recensione;

public final class RecensioneSpecification {

    private RecensioneSpecification() {}

    public static Specification<Recensione> perUtente(Long idUtente) {
        return (root, query, cb) -> {

            if (idUtente == null) {
                return null;
            }

            return cb.equal(
                    root.get("utente").get("id"),
                    idUtente
            );
        };
    }

    public static Specification<Recensione> perLuogo(Long idLuogoInteresse) {
        return (root, query, cb) -> {

            if (idLuogoInteresse == null) {
                return null;
            }

            return cb.equal(
                    root.get("luogoInteresse").get("id"),
                    idLuogoInteresse
            );
        };
    }

    public static Specification<Recensione> perVoto(Integer voto) {
        return (root, query, cb) -> {

            if (voto == null) {
                return null;
            }

            return cb.equal(
                    root.get("voto"),
                    voto
            );
        };
    }

    public static Specification<Recensione> dopoData(LocalDateTime data) {
        return (root, query, cb) -> {

            if (data == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(
                    root.get("createdAt"),
                    data
            );
        };
    }

    public static Specification<Recensione> primaData(LocalDateTime data) {
        return (root, query, cb) -> {

            if (data == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(
                    root.get("createdAt"),
                    data
            );
        };
    }
}