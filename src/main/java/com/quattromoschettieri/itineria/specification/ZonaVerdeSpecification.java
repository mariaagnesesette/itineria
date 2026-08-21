package com.quattromoschettieri.itineria.specification;

import org.springframework.data.jpa.domain.Specification;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.zonaVerde.TipoZonaVerde;
import com.quattromoschettieri.itineria.entities.zonaVerde.ZonaVerde;

public final class ZonaVerdeSpecification {

    private ZonaVerdeSpecification() {
    }

    public static Specification<ZonaVerde> tipologia(
            TipoZonaVerde tipologia) {

        return (root, query, cb) ->
                tipologia == null
                        ? null
                        : cb.equal(root.get("tipologia"), tipologia);
    }

    public static Specification<ZonaVerde> areaMq(
            Double areaMin,
            Double areaMax) {

        return (root, query, cb) -> {
            if (areaMin == null && areaMax == null) {
                return null;
            }

            if (areaMin == null) {
                return cb.lessThanOrEqualTo(
                        root.get("areaMq"),
                        areaMax);
            }

            if (areaMax == null) {
                return cb.greaterThanOrEqualTo(
                        root.get("areaMq"),
                        areaMin);
            }

            return cb.between(
                    root.get("areaMq"),
                    areaMin,
                    areaMax);
        };
    }

    public static Specification<ZonaVerde> isDogFriendly(
            Boolean dogFriendly) {

        return (root, query, cb) ->
                dogFriendly == null
                        ? null
                        : cb.equal(root.get("dogFriendly"), dogFriendly);
    }

    public static Specification<ZonaVerde> hasRistoro(
            Boolean ristoro) {

        return (root, query, cb) ->
                ristoro == null
                        ? null
                        : cb.equal(root.get("ristoro"), ristoro);
    }

    public static Specification<ZonaVerde> isCiclabile(
            Boolean ciclabile) {

        return (root, query, cb) ->
                ciclabile == null
                        ? null
                        : cb.equal(root.get("ciclabile"), ciclabile);
    }

    public static Specification<ZonaVerde> nomeContains(String nome) {

        return (root, query, cb) ->
                nome == null || nome.isBlank()
                        ? null
                        : cb.like(
                                cb.lower(root.get("nome")),
                                "%" + nome.toLowerCase() + "%");
    }

    public static Specification<ZonaVerde> hasAccessibilita(
            Accessibilita accessibilita) {

        return (root, query, cb) ->
                accessibilita == null
                        ? null
                        : cb.equal(
                                root.get("accessibilita"),
                                accessibilita);
    }

    public static Specification<ZonaVerde> isSempreAperto(
            Boolean sempreAperto) {

        return (root, query, cb) ->
                sempreAperto == null
                        ? null
                        : cb.equal(
                                root.get("sempreAperto"),
                                sempreAperto);
    }

    public static Specification<ZonaVerde> inCitta(Long idCitta) {

        return (root, query, cb) -> {
            if (idCitta == null) {
                return null;
            }

            return cb.equal(
                    root.get("citta").get("id"),
                    idCitta);
        };
    }

    public static Specification<ZonaVerde> inRegione(String regione) {

        return (root, query, cb) -> {
            if (regione == null || regione.isBlank()) {
                return null;
            }

            var joinCitta = root.join("citta");
            return cb.equal(joinCitta.get("regione"), regione);
        };
    }
}