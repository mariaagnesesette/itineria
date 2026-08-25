// biblioteche.js
document.querySelectorAll('.filter-chip').forEach(chip => {
  chip.addEventListener('click', function() {
    const filter = this.dataset.filter;
    const form = document.getElementById('bibliotecheFilterForm');
    
    // Rimuovi la classe active da tutti i chip
    document.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
    
    // Aggiungi la classe active al chip cliccato
    this.classList.add('active');

    // Resetta tutti i filtri
    document.getElementById('pubblicoInput').value = '';
    document.getElementById('wifiInput').value = '';
    document.getElementById('areaComputerInput').value = '';
    document.getElementById('areaBambiniInput').value = '';
    document.getElementById('sempreApertoInput').value = '';
    document.getElementById('accessibilitaInput').value = '';

    // Imposta il filtro selezionato
    if (filter !== 'all') {
      switch(filter) {
        case 'pubblico':
          document.getElementById('pubblicoInput').value = 'true';
          break;
        case 'wifi':
          document.getElementById('wifiInput').value = 'true';
          break;
        case 'area-computer':
          document.getElementById('areaComputerInput').value = 'true';
          break;
        case 'area-bambini':
          document.getElementById('areaBambiniInput').value = 'true';
          break;
        case 'sempre-aperto':
          document.getElementById('sempreApertoInput').value = 'true';
          break;
        case 'accessibile':
          document.getElementById('accessibilitaInput').value = 'COMPLETA';
          break;
      }
    }

    // Sottometti il form
    form.submit();
  });
});

// Ricerca al volo nel campo nome
document.getElementById('bibliotecheSearchInput').addEventListener('keyup', function(e) {
  if (e.key === 'Enter') {
    document.getElementById('nomeInput').value = this.value;
    document.getElementById('bibliotecheFilterForm').submit();
  }
});