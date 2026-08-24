"use strict";

document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('#registrationForm');
  const message = document.querySelector('#registrationMessage');
  form.addEventListener('submit', event => {
    const birthDate = new Date(form.elements.dataNascita.value);
    const adultDate = new Date(); adultDate.setFullYear(adultDate.getFullYear() - 13);
    if (!form.checkValidity() || birthDate > adultDate) {
      event.preventDefault();
      message.textContent = birthDate > adultDate ? 'Devi avere almeno 13 anni per registrarti.' : 'Completa correttamente tutti i campi.';
      form.reportValidity();
    }
  });
});
