package com.quattromoschettieri.itineria.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.museo.Museo;
import com.quattromoschettieri.itineria.entities.museo.TipologiaMuseo;

public class MuseoSpecification {

    private MuseoSpecification() {
    }

    public static Specification<Museo> tipologia(TipologiaMuseo tipoMuseo) {
        return (root, query, cb) -> tipoMuseo == null ? null
                : cb.equal(root.get("tipologia"), tipoMuseo);
    }

    public static Specification<Museo> hasGuidaPrenotabile(Boolean guidaPrenotabile) {
        return (root, query, cb) -> guidaPrenotabile == null ? null
                : cb.equal(root.get("guidaPrenotabile"), guidaPrenotabile);
    }

    public static Specification<Museo> hasBarInterno(Boolean barInterno) {
        return (root, query, cb) -> barInterno == null ? null
                : cb.equal(root.get("barInterno"), barInterno);

    }

    // public static Specification<Museo> fasciaPrezzo(Long idFasciaPrezzo) {
    // return (root, query, cb) -> {
    // if (idFasciaPrezzo == null) {
    // return null;
    // }

    // query.distinct(true);

    // var prezzo = root.join("prezzi");
    // return cb.equal(
    // prezzo.get("fasciaPrezzo").get("id"),
    // idFasciaPrezzo);
    // };
    // }

    public static Specification<Museo> fasciaPrezzo(
            BigDecimal prezzoMin,
            BigDecimal prezzoMax) {

        return (root, query, cb) -> {
            if (prezzoMin == null && prezzoMax == null) {
                return null;
            }

            query.distinct(true);

            var prezzo = root.join("prezzi");

            if (prezzoMin == null) {
                return cb.lessThanOrEqualTo(
                        prezzo.get("prezzo"),
                        prezzoMax);
            }

            if (prezzoMax == null) {
                return cb.greaterThanOrEqualTo(
                        prezzo.get("prezzo"),
                        prezzoMin);
            }

            return cb.between(
                    prezzo.get("prezzo"),
                    prezzoMin,
                    prezzoMax);
        };
    }

    public static Specification<Museo> nomeContains(String nome) {
        return (root, query, cb) -> (nome == null || nome.isBlank()) ? null
                : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Museo> hasAccessibilita(Accessibilita accessibilita) {
        return (root, query, cb) -> accessibilita == null ? null
                : cb.equal(root.get("accessibilita"), accessibilita);
    }

    public static Specification<Museo> isSempreAperto(Boolean sempreAperto) {
        return (root, query, cb) -> sempreAperto == null ? null
                : cb.equal(root.get("sempreAperto"), sempreAperto);
    }

    // da verificare se vogliamo tenere queste due query
    public static Specification<Museo> inCitta(Long idCitta) {
        return (root, query, cb) -> {
            if (idCitta == null)
                return null;
            return cb.equal(root.get("citta").get("id"), idCitta);
        };
    }

    public static Specification<Museo> inRegione(String regione) {
        return (root, query, cb) -> {
            if (regione == null || regione.isBlank())
                return null;
            var joinCitta = root.join("citta"); // JOIN esplicito, utile se vuoi fetch/join type
            return cb.equal(joinCitta.get("regione"), regione);
        };
    }
}
