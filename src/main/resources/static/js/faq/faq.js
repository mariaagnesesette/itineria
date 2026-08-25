"use strict";

document.addEventListener('DOMContentLoaded', () => {
  const items = [...document.querySelectorAll('.faq')];
  items.forEach((item, index) => {
    const button = item.querySelector('.question'); const answer = item.querySelector('.answer');
    const id = `faq-answer-${index}`; answer.id = id; button.setAttribute('aria-controls', id);
    button.addEventListener('click', () => {
      const opening = button.getAttribute('aria-expanded') !== 'true';
      items.forEach(other => { other.querySelector('.question').setAttribute('aria-expanded','false'); other.querySelector('.answer').hidden = true; });
      button.setAttribute('aria-expanded', String(opening)); answer.hidden = !opening;
    });
  });
  if (location.hash) items[0]?.querySelector('.question').click();
});
