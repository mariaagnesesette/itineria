"use strict";

document.addEventListener('DOMContentLoaded', () => {
  const profileKey = 'itinerariaProfile';
  const defaultProfile = { nome: '', cognome: '', email: '', dataNascita: '', username: '' };
  let profile = { ...defaultProfile, ...JSON.parse(localStorage.getItem(profileKey) || '{}') };
  const profileForm = document.querySelector('#profileForm');
  const profileName = document.querySelector('#profileName');
  const fillProfile = () => { Object.entries(profile).forEach(([key, value]) => { if (profileForm.elements[key]) profileForm.elements[key].value = value; }); profileName.textContent = `${profile.nome} ${profile.cognome}`.trim() || 'Il tuo profilo'; };
  fillProfile();

  document.querySelectorAll('.tab-btn').forEach(button => button.addEventListener('click', () => {
    document.querySelectorAll('.tab-btn').forEach(item => item.classList.toggle('active', item === button));
    document.querySelectorAll('.tab-panel').forEach(panel => panel.classList.toggle('active', panel.id === `panel-${button.dataset.tab}`));
  }));
  profileForm.addEventListener('submit', event => {
    event.preventDefault(); if (!profileForm.checkValidity()) return profileForm.reportValidity();
    profile = { ...profile, ...Object.fromEntries(new FormData(profileForm)) }; delete profile.password;
    localStorage.setItem(profileKey, JSON.stringify(profile)); fillProfile();
    const message = document.querySelector('#profileMessage'); message.textContent = 'Modifiche salvate sul dispositivo.'; message.className = 'form-message success';
  });

  const documentsKey = 'itinerariaDocuments'; const documentsBody = document.querySelector('#documentsBody');
  const renderDocuments = () => { const docs = JSON.parse(localStorage.getItem(documentsKey) || '[]'); documentsBody.innerHTML = docs.length ? docs.map(doc => `<tr><td>${doc.tipo}</td><td>${doc.codice}</td><td>${doc.date}</td><td><span class="badge-status">In approvazione</span></td></tr>`).join('') : '<tr><td colspan="4">Nessun documento caricato.</td></tr>'; };
  renderDocuments();
  document.querySelector('#documentForm').addEventListener('submit', event => { event.preventDefault(); const form = event.currentTarget; if (!form.checkValidity()) return form.reportValidity(); const docs = JSON.parse(localStorage.getItem(documentsKey) || '[]'); docs.push({ tipo: form.elements.tipo.value, codice: form.elements.codice.value, date: new Intl.DateTimeFormat('it-IT',{dateStyle:'long'}).format(new Date()) }); localStorage.setItem(documentsKey, JSON.stringify(docs)); form.reset(); renderDocuments(); });

  let favoriteType = 'luoghi'; const favoritesList = document.querySelector('#favoritesList'); const search = document.querySelector('#favoriteSearch');
  document.querySelectorAll('.pill-btn').forEach(button => button.addEventListener('click', () => { favoriteType = button.dataset.favoriteType; document.querySelectorAll('.pill-btn').forEach(item => item.classList.toggle('active', item === button)); renderFavorites(); }));
  const renderFavorites = () => { [...favoritesList.children].forEach(card => { const text = card.textContent.toLowerCase(); card.hidden = favoriteType !== 'luoghi' || !text.includes(search.value.toLowerCase()); }); };
  search.addEventListener('input', renderFavorites);
  favoritesList.addEventListener('click', event => { const button = event.target.closest('.btn-icon-trash'); if (button && confirm('Rimuovere questo preferito?')) button.closest('.item-card').remove(); });

  document.querySelector('#panel-recensioni').addEventListener('click', event => { const card = event.target.closest('.review-card'); if (!card) return; if (event.target.closest('[data-action="delete-review"]') && confirm('Eliminare questa recensione?')) card.remove(); if (event.target.closest('[data-action="edit-review"]')) { const text = card.querySelector('.review-text'); const edit = prompt('Modifica la recensione:', text.textContent); if (edit?.trim()) text.textContent = edit.trim(); } });
});
