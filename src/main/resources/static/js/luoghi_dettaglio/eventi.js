document.addEventListener('DOMContentLoaded', function(){
  document.querySelectorAll('a.event-card[data-ajax="true"]').forEach(a=>{
    a.addEventListener('click', function(ev){
      ev.preventDefault();
      const id = a.dataset.id;
      fetch(`/luoghi/eventi/${id}/fragment`)
        .then(r => { if(!r.ok) throw r; return r.text(); })
        .then(html => {
          const modal = document.getElementById('eventoModal');
          modal.innerHTML = html;
          modal.style.display = 'block';
          // simple close on click outside
          modal.addEventListener('click', function(e){
            if(e.target === modal) modal.style.display='none';
          });
        })
        .catch(e => {
          console.error(e);
          window.location.href = a.getAttribute('href');
        });
    });
  });
});