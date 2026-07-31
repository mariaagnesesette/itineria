package com.quattromoschettieri.itineria.entities.evento;

public enum PubblicoEvento {

    TUTTI(0, null),
    BAMBINI(0, 12),
    ADOLESCENTI(13, 17),
    GIOVANI(18, 25),
    ADULTI(26, 64),
    OVER_65(65, null);

    private final Integer etaMin;
    private final Integer etaMax;

    PubblicoEvento(Integer etaMin, Integer etaMax) {
        this.etaMin = etaMin;
        this.etaMax = etaMax;
    }

    public Integer getEtaMin() {
        return etaMin;
    }

    public Integer getEtaMax() {
        return etaMax;
    }
}