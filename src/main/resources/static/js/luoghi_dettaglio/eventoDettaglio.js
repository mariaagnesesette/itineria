/* ==========================================================================
   ITINERARIA — DETTAGLIO EVENTO
   ========================================================================== */

document.addEventListener("DOMContentLoaded", () => {

    /* ======================================================================
       ANIMAZIONE DI ENTRATA
       ====================================================================== */

    const cards = document.querySelectorAll(
        ".event-detail-hero, .event-detail-card"
    );

    cards.forEach((card, index) => {

        card.style.opacity = "0";
        card.style.transform = "translateY(12px)";

        setTimeout(() => {

            card.style.transition =
                "opacity 0.4s ease, transform 0.4s ease";

            card.style.opacity = "1";
            card.style.transform = "translateY(0)";

        }, 80 * index);

    });


    /* ======================================================================
       APERTURA LINK ESTERNI
       Aggiunge un comportamento uniforme ai link del sito del luogo.
       ====================================================================== */

    const externalLinks = document.querySelectorAll(
        ".event-location__link[target='_blank']"
    );

    externalLinks.forEach(link => {

        link.addEventListener("click", () => {

            link.setAttribute("aria-label", "Apri il sito del luogo in una nuova scheda");

        });

    });


    /* ======================================================================
       SCROLL ALLE DATE
       Se viene usato un eventuale link con href="#date-evento".
       ====================================================================== */

    const dateLinks = document.querySelectorAll(
        'a[href="#date-evento"]'
    );

    dateLinks.forEach(link => {

        link.addEventListener("click", event => {

            const target = document.getElementById("date-evento");

            if (!target) {
                return;
            }

            event.preventDefault();

            target.scrollIntoView({
                behavior: "smooth",
                block: "start"
            });

        });

    });


    /* ======================================================================
       SCROLL AL LUOGO
       Se viene usato un eventuale link con href="#luogo-evento".
       ====================================================================== */

    const locationLinks = document.querySelectorAll(
        'a[href="#luogo-evento"]'
    );

    locationLinks.forEach(link => {

        link.addEventListener("click", event => {

            const target = document.getElementById("luogo-evento");

            if (!target) {
                return;
            }

            event.preventDefault();

            target.scrollIntoView({
                behavior: "smooth",
                block: "start"
            });

        });

    });

});