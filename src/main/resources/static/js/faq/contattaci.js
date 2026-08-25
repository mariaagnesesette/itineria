"use strict";

document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('#contactForm');
  const message = document.querySelector('#formMessage');
  const subject = new URLSearchParams(location.search).get('oggetto');
  if (subject) form.elements.oggetto.value = subject;
  form.addEventListener('submit', event => {
    event.preventDefault();
    if (!form.checkValidity()) { form.reportValidity(); return; }
    const data = Object.fromEntries(new FormData(form));
    const messages = JSON.parse(localStorage.getItem('itinerariaContactMessages') || '[]');
    messages.push({ ...data, sentAt: new Date().toISOString() });
    localStorage.setItem('itinerariaContactMessages', JSON.stringify(messages));
    form.reset(); message.textContent = 'Grazie, il tuo messaggio è stato inviato.'; message.classList.add('success');
  });
});
