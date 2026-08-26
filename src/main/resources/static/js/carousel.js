/*
 * Carosello riutilizzabile per le gallerie fotografiche dei luoghi di interesse.
 * Si auto-inizializza su ogni elemento [data-carousel] presente nella pagina
 * (una pagina di dettaglio ne ha tipicamente due: hero e galleria completa).
 *
 * Markup atteso:
 * <div data-carousel data-autoplay="5000">
 *   <div class="carousel__viewport">
 *     <div class="carousel__track">
 *       <div class="carousel__slide">...</div>
 *       ...
 *     </div>
 *   </div>
 *   <button data-carousel-prev></button>
 *   <button data-carousel-next></button>
 *   <div class="carousel-dots"></div> (opzionale)
 * </div>
 */
(function () {
  "use strict";

  function initCarousel(root) {
    var track = root.querySelector('.carousel__track');
    var slides = Array.prototype.slice.call(root.querySelectorAll('.carousel__slide'));

    if (!track || slides.length === 0) {
      return;
    }

    var current = 0;
    var autoplayMs = parseInt(root.getAttribute('data-autoplay') || '0', 10);
    var timer = null;
    var dotsWrap = root.querySelector('.carousel-dots');
    var dots = [];

    if (dotsWrap && slides.length > 1) {
      slides.forEach(function (slide, i) {
        var dot = document.createElement('button');
        dot.type = 'button';
        dot.setAttribute('aria-label', 'Vai alla foto ' + (i + 1));
        if (i === 0) dot.classList.add('active');
        dot.addEventListener('click', function () {
          goTo(i);
          restartAutoplay();
        });
        dotsWrap.appendChild(dot);
      });
      dots = Array.prototype.slice.call(dotsWrap.children);
    }

    function render() {
      track.style.transform = 'translateX(-' + (current * 100) + '%)';
      dots.forEach(function (d, i) { d.classList.toggle('active', i === current); });
    }

    function goTo(index) {
      current = (index + slides.length) % slides.length;
      render();
    }

    function next() { goTo(current + 1); }
    function prev() { goTo(current - 1); }

    function startAutoplay() {
      if (autoplayMs > 0 && slides.length > 1) {
        timer = setInterval(next, autoplayMs);
      }
    }

    function stopAutoplay() {
      if (timer) {
        clearInterval(timer);
        timer = null;
      }
    }

    function restartAutoplay() {
      stopAutoplay();
      startAutoplay();
    }

    if (slides.length > 1) {
      root.querySelectorAll('[data-carousel-prev]').forEach(function (btn) {
        btn.addEventListener('click', function () { prev(); restartAutoplay(); });
      });
      root.querySelectorAll('[data-carousel-next]').forEach(function (btn) {
        btn.addEventListener('click', function () { next(); restartAutoplay(); });
      });

      root.addEventListener('mouseenter', stopAutoplay);
      root.addEventListener('mouseleave', startAutoplay);

      var touchStartX = 0;
      track.addEventListener('touchstart', function (e) {
        touchStartX = e.touches[0].clientX;
      }, { passive: true });

      track.addEventListener('touchend', function (e) {
        var diff = e.changedTouches[0].clientX - touchStartX;
        if (Math.abs(diff) > 40) {
          if (diff < 0) { next(); } else { prev(); }
          restartAutoplay();
        }
      }, { passive: true });
    } else {
      root.querySelectorAll('[data-carousel-prev], [data-carousel-next]').forEach(function (btn) {
        btn.style.display = 'none';
      });
    }

    render();
    startAutoplay();
  }

  document.querySelectorAll('[data-carousel]').forEach(initCarousel);
})();
