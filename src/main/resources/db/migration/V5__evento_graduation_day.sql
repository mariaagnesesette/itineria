-- =================================================================
-- NUOVO EVENTO AGGIUNTO DOPO IL POPOLAMENTO INIZIALE
-- GraduationDay (id 15) - luogo di interesse 4 (Musei Reali di Torino)
-- =================================================================

INSERT INTO eventi
(id, nome, descrizione, tipologia, prezzo, prenotazione, pubblico, id_luogo_interesse, created_at, updated_at, version) VALUES
(15,
 'GraduationDay',
 'L''evento sarà dedicato alla presentazione dei progetti sviluppati dagli studenti della classe Java154 di Generation Italy al termine del loro percorso formativo.

Durante l''incontro, i partecipanti avranno l''opportunità di scoprire le applicazioni realizzate dai ragazzi e conoscere il lavoro svolto nel corso degli ultimi mesi: dalla progettazione allo sviluppo, fino alla presentazione finale delle soluzioni create. Sarà un''occasione per valorizzare l''impegno, la crescita personale e professionale e le competenze tecniche acquisite in ambito Java e sviluppo software.

L''evento rappresenta anche un momento conviviale e di confronto, in cui celebrare il percorso intrapreso dagli studenti, che hanno scelto di mettersi in gioco per costruire nuove opportunità nel settore IT. Sarà possibile condividere esperienze, fare networking e osservare da vicino il risultato di un intenso percorso di formazione orientato all''ingresso nel mondo del lavoro.L''evento sarà dedicato alla presentazione dei progetti sviluppati dagli studenti della classe Java154 di Generation Italy al termine del loro percorso formativo.

Durante l''incontro, i partecipanti avranno l''opportunità di scoprire le applicazioni realizzate dai ragazzi e conoscere il lavoro svolto nel corso degli ultimi mesi: dalla progettazione allo sviluppo, fino alla presentazione finale delle soluzioni create. Sarà un''occasione per valorizzare l''impegno, la crescita personale e professionale e le competenze tecniche acquisite in ambito Java e sviluppo software.

L''evento rappresenta anche un momento conviviale e di confronto, in cui celebrare il percorso intrapreso dagli studenti, che hanno scelto di mettersi in gioco per costruire nuove opportunità nel settore IT. Sarà possibile condividere esperienze, fare networking e osservare da vicino il risultato di un intenso percorso di formazione orientato all''ingresso nel mondo del lavoro.',
 'PRESENTAZIONE_LIBRO', 0.00, FALSE, 'TUTTI', 4, '2026-08-26 18:07:30', '2026-08-26 18:07:30', 0);

INSERT INTO data_evento (evento_id, data_inizio, data_fine, ora_inizio, ora_fine) VALUES
(15, '2026-08-27', '2026-08-27', '15:00:00', '17:00:00');
