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

/* ==========================================================================
   CAROSELLO GALLERIA
   ========================================================================== */

(function () {
    document.querySelectorAll('.gallery-carousel').forEach(carousel => {
        const track = carousel.querySelector('.gallery-carousel-track');
        const slides = Array.from(carousel.querySelectorAll('.gallery-carousel-slide'));
        const prevBtn = carousel.querySelector('.gallery-carousel-arrow--prev');
        const nextBtn = carousel.querySelector('.gallery-carousel-arrow--next');
        const dotsWrap = carousel.querySelector('.gallery-carousel-dots');
        let index = 0;

        if (slides.length === 0) return;

        slides.forEach((_, i) => {
            const dot = document.createElement('button');
            dot.type = 'button';
            dot.setAttribute('aria-label', 'Vai all\'immagine ' + (i + 1));
            dot.addEventListener('click', () => goTo(i));
            dotsWrap.appendChild(dot);
        });
        const dots = Array.from(dotsWrap.children);

        function update() {
            track.style.transform = 'translateX(-' + (index * 100) + '%)';
            dots.forEach((d, i) => d.classList.toggle('active', i === index));
        }

        function goTo(i) {
            index = (i + slides.length) % slides.length;
            update();
        }

        function next() { goTo(index + 1); }

        prevBtn?.addEventListener('click', () => { goTo(index - 1); resetAutoplay(); });
        nextBtn?.addEventListener('click', () => { next(); resetAutoplay(); });
        dots.forEach(dot => dot.addEventListener('click', resetAutoplay));

        let autoplayId = null;
        function startAutoplay() {
            if (slides.length <= 1) return;
            autoplayId = setInterval(next, 4000);
        }
        function resetAutoplay() {
            if (autoplayId) clearInterval(autoplayId);
            startAutoplay();
        }

        if (slides.length <= 1) {
            if (prevBtn) prevBtn.style.display = 'none';
            if (nextBtn) nextBtn.style.display = 'none';
        }

        update();
        startAutoplay();
        carousel.addEventListener('mouseenter', () => { if (autoplayId) clearInterval(autoplayId); });
        carousel.addEventListener('mouseleave', startAutoplay);
    });
})();

/* ==========================================================================
   EFFETTO SCROLL-ZOOM
   La galleria si ingrandisce a tutto schermo scorrendo (sotto la navbar),
   resta agganciata mentre si scorre e si rilascia rivelando il resto della
   pagina.
   ========================================================================== */

(function () {
    const navbar = document.querySelector('.site-header');

    function setNavbarHeight() {
        const height = navbar ? navbar.getBoundingClientRect().height : 0;
        document.documentElement.style.setProperty('--navbar-height', height + 'px');
        return height;
    }

    let navbarHeight = setNavbarHeight();

    document.querySelectorAll('.gallery-scroll-zoom').forEach(zoomWrap => {
        const carousel = zoomWrap.querySelector('.gallery-carousel');
        const viewport = zoomWrap.querySelector('.gallery-carousel-viewport');
        if (!carousel || !viewport) return;

        const baseWidthRatio = 0.92;
        const baseMaxWidth = 1100;
        const baseHeight = 520;
        const baseRadius = 22;
        const growRangeRatio = 0.35;

        let ticking = false;

        function update() {
            ticking = false;

            const rect = zoomWrap.getBoundingClientRect();
            const growRange = window.innerHeight * growRangeRatio;
            const progress = Math.min(1, Math.max(0, -rect.top / growRange));

            const startWidth = Math.min(baseMaxWidth, window.innerWidth * baseWidthRatio);
            const startHeight = window.innerWidth <= 600 ? 380 : baseHeight;
            const fullHeight = window.innerHeight - navbarHeight;

            const width = startWidth + (window.innerWidth - startWidth) * progress;
            const height = startHeight + (fullHeight - startHeight) * progress;
            const radius = baseRadius * (1 - progress);

            viewport.style.width = width + 'px';
            viewport.style.height = height + 'px';
            viewport.style.borderRadius = radius + 'px';
        }

        function onScroll() {
            if (!ticking) {
                window.requestAnimationFrame(update);
                ticking = true;
            }
        }

        function onResize() {
            navbarHeight = setNavbarHeight();
            onScroll();
        }

        window.addEventListener('scroll', onScroll, { passive: true });
        window.addEventListener('resize', onResize);
        update();
    });
})();

/* ==========================================================================
   MENU "CAMBIA IMMAGINE"
   Spostato come figlio diretto di <body> via JS perché .event-detail-hero ha
   overflow:hidden (per gli angoli arrotondati) e taglierebbe altrimenti un
   menu a comparsa più grande del box.
   ========================================================================== */

(function () {
    document.querySelectorAll('.hero-cover-controls').forEach(controls => {
        const toggle = controls.querySelector('.hero-cover-toggle');
        const menu = controls.querySelector('.hero-cover-menu');
        if (!toggle || !menu) return;

        document.body.appendChild(menu);
        menu.classList.add('hero-cover-menu--floating');

        function position() {
            const rect = toggle.getBoundingClientRect();
            menu.style.top = (rect.bottom + 8) + 'px';
            menu.style.left = rect.left + 'px';
        }

        function open() {
            position();
            menu.hidden = false;
        }

        function close() {
            menu.hidden = true;
        }

        toggle.addEventListener('click', (event) => {
            event.stopPropagation();
            menu.hidden ? open() : close();
        });

        document.addEventListener('click', (event) => {
            if (!menu.hidden && !menu.contains(event.target) && event.target !== toggle) {
                close();
            }
        });

        window.addEventListener('scroll', () => { if (!menu.hidden) position(); }, { passive: true });
        window.addEventListener('resize', () => { if (!menu.hidden) position(); });
    });
})();

/* ==========================================================================
   PREFERITI
   Bollino "preferiti" nell'hero: aggiunge/rimuove l'evento dai preferiti
   dell'utente autenticato tramite l'API REST già esistente.
   ========================================================================== */

(function () {
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

    document.querySelectorAll('button.favorite-badge[data-evento-id]').forEach(btn => {
        const eventoId = btn.dataset.eventoId;
        const utenteId = btn.dataset.utenteId;
        const icon = btn.querySelector('i');

        btn.addEventListener('click', async () => {
            const isFavorite = btn.classList.contains('is-favorite');
            const method = isFavorite ? 'DELETE' : 'POST';

            btn.disabled = true;
            try {
                const headers = {};
                if (csrfToken && csrfHeader) {
                    headers[csrfHeader] = csrfToken;
                }

                const response = await fetch(
                    `/api/utenti/${utenteId}/preferiti/eventi/${eventoId}`,
                    { method, headers }
                );

                if (response.ok) {
                    btn.classList.toggle('is-favorite');
                    icon.classList.toggle('fa-regular');
                    icon.classList.toggle('fa-solid');
                }
            } finally {
                btn.disabled = false;
            }
        });
    });
})();