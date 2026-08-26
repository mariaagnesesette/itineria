"use strict";

document.addEventListener('DOMContentLoaded', () => {
  const viewCard = document.querySelector('#viewProfileCard');
  const editCard = document.querySelector('#editProfileCard');
  const btnModifica = document.querySelector('#btnModificaProfilo');
  const btnAnnulla = document.querySelector('#btnAnnullaModifica');

  btnModifica?.addEventListener('click', () => { viewCard.hidden = true; editCard.hidden = false; });
  btnAnnulla?.addEventListener('click', () => { editCard.hidden = true; viewCard.hidden = false; });

  const passwordForm = document.querySelector('#passwordForm');
  const btnCambiaPassword = document.querySelector('#btnCambiaPassword');
  const btnAnnullaPassword = document.querySelector('#btnAnnullaPassword');

  btnCambiaPassword?.addEventListener('click', () => { passwordForm.hidden = false; btnCambiaPassword.hidden = true; });
  btnAnnullaPassword?.addEventListener('click', () => { passwordForm.hidden = true; passwordForm.reset(); btnCambiaPassword.hidden = false; });

  document.querySelectorAll('.btn-toggle-password').forEach(button => button.addEventListener('click', () => {
    const input = document.getElementById(button.dataset.target);
    const isHidden = input.type === 'password';
    input.type = isHidden ? 'text' : 'password';
    button.textContent = isHidden ? '🙈' : '👁';
  }));

  const activateTab = (tab) => {
    const button = document.querySelector(`.tab-btn[data-tab="${tab}"]`);
    if (!button) return;
    document.querySelectorAll('.tab-btn').forEach(item => item.classList.toggle('active', item === button));
    document.querySelectorAll('.tab-panel').forEach(panel => panel.classList.toggle('active', panel.id === `panel-${tab}`));
  };

  document.querySelectorAll('.tab-btn').forEach(button => button.addEventListener('click', () => activateTab(button.dataset.tab)));

  if (window.location.hash) activateTab(window.location.hash.slice(1));

  const documentsKey = 'itinerariaDocuments'; const documentsBody = document.querySelector('#documentsBody');
  const renderDocuments = () => { const docs = JSON.parse(localStorage.getItem(documentsKey) || '[]'); documentsBody.innerHTML = docs.length ? docs.map(doc => `<tr><td>${doc.tipo}</td><td>${doc.codice}</td><td>${doc.date}</td><td><span class="badge-status">In approvazione</span></td></tr>`).join('') : '<tr><td colspan="4">Nessun documento caricato.</td></tr>'; };
  renderDocuments();
  document.querySelector('#documentForm').addEventListener('submit', event => { event.preventDefault(); const form = event.currentTarget; if (!form.checkValidity()) return form.reportValidity(); const docs = JSON.parse(localStorage.getItem(documentsKey) || '[]'); docs.push({ tipo: form.elements.tipo.value, codice: form.elements.codice.value, date: new Intl.DateTimeFormat('it-IT',{dateStyle:'long'}).format(new Date()) }); localStorage.setItem(documentsKey, JSON.stringify(docs)); form.reset(); renderDocuments(); });

  let favoriteType = 'luoghi'; const favoritesList = document.querySelector('#favoritesList'); const search = document.querySelector('#favoriteSearch');
  const utenteId = favoritesList?.dataset.utenteId;
  const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

  document.querySelectorAll('.pill-btn').forEach(button => button.addEventListener('click', () => { favoriteType = button.dataset.favoriteType; document.querySelectorAll('.pill-btn').forEach(item => item.classList.toggle('active', item === button)); renderFavorites(); }));
  const renderFavorites = () => { [...favoritesList.querySelectorAll('[data-favorite-item]')].forEach(card => { const text = card.textContent.toLowerCase(); card.hidden = card.dataset.favoriteItem !== favoriteType || !text.includes(search.value.toLowerCase()); }); };
  search?.addEventListener('input', renderFavorites);
  renderFavorites();

  favoritesList?.addEventListener('click', async event => {
    const button = event.target.closest('.btn-icon-trash');
    if (!button) return;
    if (!confirm('Rimuovere questo preferito?')) return;

    const luogoId = button.dataset.removeLuogo;
    const eventoId = button.dataset.removeEvento;
    const url = luogoId
      ? `/api/utenti/${utenteId}/preferiti/luoghi/${luogoId}`
      : `/api/utenti/${utenteId}/preferiti/eventi/${eventoId}`;

    const headers = {};
    if (csrfToken && csrfHeader) headers[csrfHeader] = csrfToken;

    const response = await fetch(url, { method: 'DELETE', headers });
    if (response.ok) {
      button.closest('.item-card').remove();
    }
  });

  document.querySelector('#panel-recensioni').addEventListener('click', event => { const card = event.target.closest('.review-card'); if (!card) return; if (event.target.closest('[data-action="delete-review"]') && confirm('Eliminare questa recensione?')) card.remove(); if (event.target.closest('[data-action="edit-review"]')) { const text = card.querySelector('.review-text'); const edit = prompt('Modifica la recensione:', text.textContent); if (edit?.trim()) text.textContent = edit.trim(); } });
});
