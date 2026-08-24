"use strict";

document.addEventListener('DOMContentLoaded', () => {
  const subject = {accesso:'Problema di accesso',ricerca:'Problema nella ricerca',segnalazione:'Segnalazione di un luogo',tecnico:'Problema tecnico'};
  document.querySelectorAll('.support-topic').forEach(button => button.addEventListener('click', () => {
    location.href = `contattaci.html?oggetto=${encodeURIComponent(subject[button.dataset.topic])}`;
  }));
});
