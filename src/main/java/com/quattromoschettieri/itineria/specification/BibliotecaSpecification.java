package com.quattromoschettieri.itineria.specification;

import org.springframework.data.jpa.domain.Specification;

import com.quattromoschettieri.itineria.entities.biblioteca.Biblioteca;
import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;

public final class BibliotecaSpecification {

    private BibliotecaSpecification(){}

    public static Specification<Biblioteca> isPubblico(Boolean pubblico){
        return(root, query, cb) ->
            pubblico == null ? null : cb.equal(root.get("pubblico"), pubblico);
    }

    public static Specification<Biblioteca> hasWifi(Boolean wifi){
        return(root, query, cb) ->
            wifi == null ? null : cb.equal(root.get("wifi"), wifi);
    }

    public static Specification<Biblioteca> hasAreaComputer(Boolean areaComputer){
        return(root, query, cb) ->
            areaComputer == null ? null : cb.equal(root.get("areaComputer"), areaComputer);
    }

    public static Specification<Biblioteca> hasAreaBambini(Boolean areaBambini){
        return(root, query, cb) ->
            areaBambini == null ? null : cb.equal(root.get("areaBambini"), areaBambini);
    }

    //query ereditate da LuogoInteresse
    public static Specification<Biblioteca> nomeContains(String nome){
        return(root, query, cb) ->
            (nome == null || nome.isBlank()) ? null :
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

        public static Specification<Biblioteca> hasAccessibilita(Accessibilita accessibilita) {
        return (root, query, cb) ->
            accessibilita == null ? null : cb.equal(root.get("accessibilita"), accessibilita);
    }

    public static Specification<Biblioteca> isSempreAperto(Boolean sempreAperto) {
        return (root, query, cb) ->
            sempreAperto == null ? null : cb.equal(root.get("sempreAperto"), sempreAperto);
    }

    //da verificare se vogliamo tenere queste due query
        public static Specification<Biblioteca> inCitta(Long idCitta) {
        return (root, query, cb) -> {
            if (idCitta == null) return null;
            return cb.equal(root.get("citta").get("id"), idCitta);
        };
    }

    public static Specification<Biblioteca> inRegione(String regione) {
        return (root, query, cb) -> {
            if (regione == null || regione.isBlank()) return null;
            var joinCitta = root.join("citta"); // JOIN esplicito, utile se vuoi fetch/join type
            return cb.equal(joinCitta.get("regione"), regione);
        };
    }
}
