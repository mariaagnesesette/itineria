package com.quattromoschettieri.itineria.specification;

import org.springframework.data.jpa.domain.Specification;

import com.quattromoschettieri.itineria.entities.luogoInteresse.Accessibilita;
import com.quattromoschettieri.itineria.entities.ristorante.FasciaPrezzoRistorante;
import com.quattromoschettieri.itineria.entities.ristorante.Ristorante;
import com.quattromoschettieri.itineria.entities.ristorante.TipoCucina;

public class RistoranteSpecification {

    private RistoranteSpecification(){

    }

    public static Specification<Ristorante> tipoCucina(TipoCucina tipoCucina){
        return(root, query, cb) -> tipoCucina == null ? null
                : cb.equal(root.get("tipoCucina"), tipoCucina);
    }

    public static Specification<Ristorante> fasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo){
        return(root, query, cb) -> fasciaPrezzo == null ? null
                : cb.equal(root.get("fasciaPrezzo"), fasciaPrezzo);
    }

    public static Specification<Ristorante> isDogFriendly(Boolean dogFriendly){
        return(root, query, cb) -> dogFriendly == null ? null
            : cb.equal(root.get("dogFriendly"), dogFriendly);
    }

    public static Specification<Ristorante> isPerCeliaci(Boolean perCeliaci){
        return(root, query, cb) -> perCeliaci == null ? null
            : cb.equal(root.get("perCeliaci"), perCeliaci);
    }

        public static Specification<Ristorante> hasPostiEsterni(Boolean postiEsterni){
        return(root, query, cb) -> postiEsterni == null ? null
            : cb.equal(root.get("postiEsterni"), postiEsterni);
    }

        public static Specification<Ristorante> nomeContains(String nome) {
        return (root, query, cb) -> (nome == null || nome.isBlank()) ? null
                : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Ristorante> hasAccessibilita(Accessibilita accessibilita) {
        return (root, query, cb) -> accessibilita == null ? null
                : cb.equal(root.get("accessibilita"), accessibilita);
    }

    public static Specification<Ristorante> isSempreAperto(Boolean sempreAperto) {
        return (root, query, cb) -> sempreAperto == null ? null
                : cb.equal(root.get("sempreAperto"), sempreAperto);
    }

    // da verificare se vogliamo tenere queste due query
    public static Specification<Ristorante> inCitta(Long idCitta) {
        return (root, query, cb) -> {
            if (idCitta == null)
                return null;
            return cb.equal(root.get("citta").get("id"), idCitta);
        };
    }

    public static Specification<Ristorante> inRegione(String regione) {
        return (root, query, cb) -> {
            if (regione == null || regione.isBlank())
                return null;
            var joinCitta = root.join("citta"); // JOIN esplicito, utile se vuoi fetch/join type
            return cb.equal(joinCitta.get("regione"), regione);
        };
    }

}
