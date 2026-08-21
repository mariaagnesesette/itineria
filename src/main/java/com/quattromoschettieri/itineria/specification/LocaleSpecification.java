package com.quattromoschettieri.itineria.specification;

import org.springframework.data.jpa.domain.Specification;

import com.quattromoschettieri.itineria.entities.locale.Atmosfera;
import com.quattromoschettieri.itineria.entities.locale.FasciaPrezzoLocale;
import com.quattromoschettieri.itineria.entities.locale.Locale;
import com.quattromoschettieri.itineria.entities.locale.TipoLocale;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;

public final class LocaleSpecification {

    private LocaleSpecification() {
    }

    public static Specification<Locale> tipoLocale(
            TipoLocale tipoLocale) {

        return (root, query, cb) ->
                tipoLocale == null
                        ? null
                        : cb.equal(root.get("tipoLocale"), tipoLocale);
    }

    public static Specification<Locale> atmosfera(
            Atmosfera atmosfera) {

        return (root, query, cb) ->
                atmosfera == null
                        ? null
                        : cb.equal(root.get("atmosfera"), atmosfera);
    }

    public static Specification<Locale> fasciaPrezzo(
            FasciaPrezzoLocale fasciaPrezzo) {

        return (root, query, cb) ->
                fasciaPrezzo == null
                        ? null
                        : cb.equal(
                                root.get("fasciaPrezzo"),
                                fasciaPrezzo);
    }

    public static Specification<Locale> isAperturaSerale(
            Boolean aperturaSerale) {

        return (root, query, cb) ->
                aperturaSerale == null
                        ? null
                        : cb.equal(
                                root.get("aperturaSerale"),
                                aperturaSerale);
    }

    public static Specification<Locale> hasPostiEsterni(
            Boolean postiEsterni) {

        return (root, query, cb) ->
                postiEsterni == null
                        ? null
                        : cb.equal(
                                root.get("postiEsterni"),
                                postiEsterni);
    }

    public static Specification<Locale> isPerCeliaci(
            Boolean perCeliaci) {

        return (root, query, cb) ->
                perCeliaci == null
                        ? null
                        : cb.equal(
                                root.get("perCeliaci"),
                                perCeliaci);
    }

    public static Specification<Locale> nomeContains(String nome) {

        return (root, query, cb) ->
                nome == null || nome.isBlank()
                        ? null
                        : cb.like(
                                cb.lower(root.get("nome")),
                                "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Locale> hasAccessibilita(
            Accessibilita accessibilita) {

        return (root, query, cb) ->
                accessibilita == null
                        ? null
                        : cb.equal(
                                root.get("accessibilita"),
                                accessibilita);
    }

    public static Specification<Locale> isSempreAperto(
            Boolean sempreAperto) {

        return (root, query, cb) ->
                sempreAperto == null
                        ? null
                        : cb.equal(
                                root.get("sempreAperto"),
                                sempreAperto);
    }

    public static Specification<Locale> inCitta(Long idCitta) {

        return (root, query, cb) -> {
            if (idCitta == null) {
                return null;
            }

            return cb.equal(
                    root.get("citta").get("id"),
                    idCitta);
        };
    }

    public static Specification<Locale> inRegione(String regione) {

        return (root, query, cb) -> {
            if (regione == null || regione.isBlank()) {
                return null;
            }

            var joinCitta = root.join("citta");
            return cb.equal(joinCitta.get("regione"), regione);
        };
    }
}