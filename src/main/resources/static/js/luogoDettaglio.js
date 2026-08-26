// Comportamenti comuni a tutte le pagine di dettaglio di un luogo di interesse
// (biblioteche, musei, parchi, ristoranti, locali): toggle modalità modifica,
// mantenimento posizione di scroll, carosello galleria e relativo effetto
// scroll-zoom a tutto schermo, apertura del form di recensione.

(function () {
    document.querySelectorAll('.edit-box').forEach(box => {
        const viewMode = box.querySelector('.view-mode');
        const editMode = box.querySelector('.edit-mode');
        const btnEdit = box.querySelector('.btn-edit-box');
        const btnCancel = box.querySelector('.btn-cancel-box');
        if (!editMode) return;

        btnEdit?.addEventListener('click', () => { viewMode.hidden = true; editMode.hidden = false; });
        btnCancel?.addEventListener('click', () => { editMode.hidden = true; viewMode.hidden = false; });
    });

    if (document.body.dataset.erroreModifica === 'true') {
        document.querySelectorAll('.edit-box').forEach(box => {
            const viewMode = box.querySelector('.view-mode');
            const editMode = box.querySelector('.edit-mode');
            if (editMode) { viewMode.hidden = true; editMode.hidden = false; }
        });
    }
})();

// Menu "Cambia immagine" nell'hero: carica nuova immagine o scegli una tra
// quelle già presenti in galleria come copertina.
// Il menu viene spostato come figlio diretto di <body> e posizionato con
// position:fixed, perché .luogo-hero ha overflow:hidden (per gli angoli
// arrotondati) e taglierebbe altrimenti un menu a comparsa più grande del box.
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

// Bollino "preferiti" nell'hero: aggiunge/rimuove il luogo dai preferiti
// dell'utente autenticato tramite l'API REST già esistente.
(function () {
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

    document.querySelectorAll('button.favorite-badge[data-luogo-id]').forEach(btn => {
        const luogoId = btn.dataset.luogoId;
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
                    `/api/utenti/${utenteId}/preferiti/luoghi/${luogoId}`,
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

// Mantiene la posizione di scroll dopo il salvataggio delle modifiche
(function () {
    const scrollKey = 'itineriaLuogoScrollY';

    if ('scrollRestoration' in history) {
        history.scrollRestoration = 'manual';
    }

    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', () => {
            sessionStorage.setItem(scrollKey, String(window.scrollY));
        });
    });

    const savedScrollY = sessionStorage.getItem(scrollKey);
    if (savedScrollY !== null) {
        sessionStorage.removeItem(scrollKey);
        const previousScrollBehavior = document.documentElement.style.scrollBehavior;
        document.documentElement.style.scrollBehavior = 'auto';
        window.scrollTo({ top: parseInt(savedScrollY, 10), left: 0, behavior: 'instant' });
        document.documentElement.style.scrollBehavior = previousScrollBehavior;
    }
})();

// Carosello galleria
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

// Effetto scroll-zoom: la galleria si ingrandisce a tutto schermo scorrendo
// (sotto la navbar), resta agganciata mentre si scorre e si rilascia
// rivelando il resto della pagina.
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
        const growRangeRatio = 0.35; // quanta parte dell'altezza finestra serve a raggiungere il pieno schermo

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

// Eliminazione di una recensione da parte dell'autore.
(function () {
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

    document.querySelectorAll('.btn-delete-review[data-recensione-id]').forEach(btn => {
        btn.addEventListener('click', async () => {
            if (!confirm('Vuoi eliminare questa recensione?')) {
                return;
            }

            const recensioneId = btn.dataset.recensioneId;

            btn.disabled = true;
            try {
                const headers = {};
                if (csrfToken && csrfHeader) {
                    headers[csrfHeader] = csrfToken;
                }

                const response = await fetch(
                    `/api/recensioni/${recensioneId}`,
                    { method: 'DELETE', headers }
                );

                if (response.ok) {
                    btn.closest('.review-card')?.remove();
                } else {
                    btn.disabled = false;
                }
            } catch {
                btn.disabled = false;
            }
        });
    });
})();

function apriFormRecensione() {

    const form = document.getElementById("reviewForm");

    if (!form) {
        return;
    }

    form.style.display = "block";

    form.scrollIntoView({
        behavior: "smooth",
        block: "start"
    });

}
