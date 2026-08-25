"use strict";

document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('#loginForm');
  const message = document.querySelector('#loginMessage');
  if (new URLSearchParams(location.search).has('errore')) message.textContent = 'Email o password non corretti.';
  form.addEventListener('submit', event => {
    if (!form.checkValidity()) { event.preventDefault(); form.reportValidity(); }
  });
});
console.log("✅ accedi.js est bien chargé !");
