package com.quattromoschettieri.itineria.entities.evento;

public enum TipologiaEvento {

    MOSTRA("fa-palette"),
    CONCERTO("fa-music"),
    SPETTACOLO_TEATRALE("fa-masks-theater"),
    CONFERENZA("fa-chalkboard-user"),
    LABORATORIO("fa-flask"),
    VISITA_GUIDATA("fa-person-walking-luggage"),
    PROIEZIONE("fa-film"),
    FESTIVAL("fa-star"),
    PRESENTAZIONE_LIBRO("fa-book"),
    WORKSHOP("fa-screwdriver-wrench"),
    EVENTO_BAMBINI("fa-child-reaching"),
    EVENTO_SPECIALE("fa-sparkles");

    private final String icona;

    TipologiaEvento(String icona) {
        this.icona = icona;
    }

    public String getIcona() {
        return icona;
    }
}
