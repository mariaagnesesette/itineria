package com.quattromoschettieri.itineria.specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import com.quattromoschettieri.itineria.entities.evento.DataEvento;
import com.quattromoschettieri.itineria.entities.evento.Evento;
import com.quattromoschettieri.itineria.entities.evento.PubblicoEvento;
import com.quattromoschettieri.itineria.entities.evento.TipologiaEvento;

import jakarta.persistence.criteria.Join;

public final class EventoSpecification {

    private EventoSpecification() {}

    public static Specification<Evento> perTipologia(
        TipologiaEvento tipologiaEvento) {

            return(root, query, cb) ->
                cb.equal(
                    root.get("tipologiaEvento"),
                    tipologiaEvento
                );
        }

    public static Specification<Evento> perPubblico(
        PubblicoEvento pubblicoEvento) {

            return(root, query, cb) ->
                cb.equal(
                    root.get("pubblicoEvento"),
                    pubblicoEvento
                );
        }

    public static Specification<Evento> conPrenotazione(
        boolean prenotazione) {

            return(root, query, cb) ->
                cb.equal(
                    root.get("prenotazione"),
                    prenotazione
                );
            }

    public static Specification<Evento> prezzoMassimo(
        BigDecimal prezzo) {

            return(root, query, cb) ->
                cb.lessThanOrEqualTo(
                    root.get("prezzo"),
                    prezzo
                );
        }

        public static Specification<Evento> perLuogo(
        Long luogoId) {

            return (root, query, cb) ->
                cb.equal(
                    root.get("luogoInteresse").get("id"),
                    luogoId
                );
        }

        public static Specification<Evento> contieneNome(
        String nome) {

            return (root, query, cb) ->
                cb.like(
                    cb.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%"
                );
        }


        public static Specification<Evento> nelPeriodo(
        LocalDate inizio,
        LocalDate fine) {

            return (root, query, cb) -> {

                query.distinct(true);

                Join<Evento, DataEvento> data =
                        root.join("dataEvento");

                return cb.and(
                    cb.lessThanOrEqualTo(
                        data.get("dataInizio"),
                        fine
                    ),
                    cb.greaterThanOrEqualTo(
                        data.get("dataFine"),
                        inizio
                    )
                );
            };
        }

        public static Specification<Evento> nellaData(
        LocalDate dataRicerca) {

            return (root, query, cb) -> {

                query.distinct(true);

                Join<Evento, DataEvento> data =
                        root.join("dateEvento");

                return cb.between(
                    cb.literal(dataRicerca),
                    data.get("dataInizio"),
                    data.get("dataFine")
                );
            };
        }

        public static Specification<Evento> dopoOra(
        LocalTime ora) {

            return (root, query, cb) -> {

                query.distinct(true);

                Join<Evento, DataEvento> data =
                        root.join("dateEvento");

                return cb.greaterThanOrEqualTo(
                    data.get("oraInizio"),
                    ora
                );
            };
        }

        public static Specification<Evento> nellaFasciaOraria(
        LocalTime inizio,
        LocalTime fine) {

            return (root, query, cb) -> {

                query.distinct(true);

                Join<Evento, DataEvento> data =
                        root.join("dateEvento");

                return cb.between(
                        data.get("oraInizio"),
                        inizio,
                        fine
                );
            };
        }

        public static Specification<Evento> attivoAllOra(
        LocalTime ora) {

            return (root, query, cb) -> {

                query.distinct(true);

                Join<Evento, DataEvento> data =
                        root.join("dateEvento");

                return cb.between(
                        cb.literal(ora),
                        data.get("oraInizio"),
                        data.get("oraFine")
                );
            };
        }
}
