-- =================================================================
-- POPOLAMENTO DATABASE TORINO
-- =================================================================

-- 1. CITTA
INSERT INTO citta (nome, regione, descrizione) VALUES 
('Torino', 'PIEMONTE', 'Capoluogo del Piemonte, nota per la sua architettura barocca, i musei di fama mondiale e gli ampi parchi urbani.');

-- 2. UTENTI
INSERT INTO utenti (nome, cognome, data_nascita, username, email, password, ruolo) VALUES
('Mario', 'Rossi', '1985-04-12', 'mrossi', 'mario.rossi@email.it', '$2a$10$e8R1...hash1', 'ADMIN'),
('Giulia', 'Bianchi', '1990-09-25', 'gbianchi', 'giulia.bianchi@email.it', '$2a$10$e8R1...hash2', 'MANAGER'),
('Luca', 'Verdi', '1998-01-15', 'lverdi', 'luca.verdi@email.it', '$2a$10$e8R1...hash3', 'USER'),
('Elena', 'Ferrari', '2001-06-30', 'eferrari', 'elena.ferrari@email.it', '$2a$10$e8R1...hash4', 'USER');

-- 3. FASCE PREZZO
INSERT INTO fasce_prezzo (nome) VALUES
('Intero'),
('Ridotto (Under 26 / Over 65)'),
('Gratuito (Under 6)');

-- 4. LUOGHI DI INTERESSE (Assumendo id_citta = 1 e id_manager = 2)
INSERT INTO luoghi_interesse 
(nome, descrizione, tipo, accessibilita, indirizzo, sempre_aperto, link_sito, numero_telefono, email, id_citta, id_manager) VALUES
('Museo Egizio', 'Il più antico museo a livello mondiale dedicato interamente alla cultura egizia.', 'MUSEO', 'COMPLETA', 'Via Accademia delle Scienze 6', FALSE, 'https://museoegizio.it', '0115617776', 'info@museoegizio.it', 1, 2),
('Biblioteca Civica Centrale', 'Principale biblioteca pubblica della città con un ricco patrimonio librario e sale studio.', 'BIBLIOTECA', 'COMPLETA', 'Via della Cittadella 5', FALSE, 'https://bct.comune.torino.it', '01101129800', 'biblioteche.civiche@comune.torino.it', 1, 2),
('Parco del Valentino', 'Famoso parco pubblico situato lungo le sponde del Po, sede del Borgo Medievale.', 'ZONA_VERDE', 'COMPLETA', 'Corso Massimo d Azeglio', TRUE, NULL, NULL, NULL, 1, 2),
('Trattoria Valenza', 'Storica trattoria torinese specializzata in cucina tipica piemontese.', 'RISTORANTE', 'PARZIALE', 'Via Borgo Dora 39', FALSE, 'https://trattoriavalenza.it', '0115213914', 'info@trattoriavalenza.it', 1, 2),
('Caffè Mulassano', 'Storico caffè torinese nel cuore della città, famoso per aver inventato il tramezzino.', 'LOCALE', 'LIMITATA', 'Piazza Castello 15', FALSE, 'https://caffemulassano.it', '0115618200', 'info@caffemulassano.it', 1, 2),
('Mole Antonelliana e Museo del Cinema', 'Simbolo architettonico di Torino che ospita l eccezionale Museo Nazionale del Cinema.', 'MUSEO', 'COMPLETA', 'Via Montebello 20', FALSE, 'https://museocinema.it', '0118138560', 'info@museocinema.it', 1, 2),
('Galleria Civica d Arte Moderna e Contemporanea (GAM)', 'Museo con oltre 50.000 opere tra dipinti, sculture e installazioni dal XIX secolo a oggi.', 'MUSEO', 'COMPLETA', 'Via Corso Galileo Ferraris 30', FALSE, 'https://gamtorino.it', '0114429518', 'gam@fondazionetorinomusei.it', 1, 2),
('Giardini Reali', 'Area verde di grande valore storico situata alle spalle del Palazzo Reale.', 'ZONA_VERDE', 'COMPLETA', 'Piazza Castello 191', FALSE, 'https://museireali.beniculturali.it', '0115211106', 'mr-to@cultura.gov.it', 1, 2),
('Parco della Pellerina', 'Il piu grande parco della citta di Torino, attraversato dalla Dora Riparia.', 'ZONA_VERDE', 'PARZIALE', 'Corso Appio Claudio', TRUE, NULL, NULL, NULL, 1, 2),
('Biblioteca Reale', 'Custodisce collezioni di manoscritti e disegni di inestimabile valore, tra cui l Autoritratto di Leonardo da Vinci.', 'BIBLIOTECA', 'LIMITATA', 'Piazza Castello 191', FALSE, 'https://museireali.beniculturali.it/biblioteca-reale/', '011543855', 'mr-to.biblioteca@cultura.gov.it', 1, 2),
('Ristorante Del Cambio', 'Storico ristorante stellato dove cenava Camillo Benso Conte di Cavour.', 'RISTORANTE', 'COMPLETA', 'Piazza Carignano 2', FALSE, 'https://delcambio.it', '011546690', 'welcome@delcambio.it', 1, 2),
('Pizzeria Porto di Savona', 'Storico locale in Piazza Vittorio che propone una rinomata pizza e piatti classici piemontesi.', 'RISTORANTE', 'PARZIALE', 'Piazza Vittorio Veneto 35', FALSE, 'https://portodisavona.it', '0118173500', 'info@portodisavona.it', 1, 2),
('Caffè Al Bicerin', 'Locale storico in cui e stata inventata la celebre bevanda torinese al caffe, cioccolato e crema di latte.', 'LOCALE', 'LIMITATA', 'Piazza della Consolata 5', FALSE, 'https://bicerin.it', '0114369325', 'bicerin@bicerin.it', 1, 2),
('Caffè Torino', 'Elegante locale in stile Liberty sotto i portici di Piazza San Carlo.', 'LOCALE', 'COMPLETA', 'Piazza San Carlo 204', FALSE, 'https://caffetorino1903.it', '011545101', 'info@caffetorino1903.it', 1, 2),
('EDITORIALE - San Salvario Cocktail Bar', 'Locale moderno nel cuore della movida torinese specializzato in mixology d avanguardia.', 'LOCALE', 'COMPLETA', 'Via Saluzzo 34', FALSE, 'https://editoriale-bar.it', '0116504432', 'info@editoriale-bar.it', 1, 2);

-- 5. TABELLE SPECIALIZZATE (Qui L'ID serve in quanto Foreign Key non AUTO_INCREMENT)

-- 5.1 MUSEI E PREZZI
INSERT INTO musei (id, guide_prenotabili, tipologia, bar_interno) VALUES
(1, TRUE, 'ARCHEOLOGIA', TRUE),
(6, TRUE, 'MULTIDISCIPLINARE', TRUE),
(7, TRUE, 'ARTE', TRUE);

INSERT INTO prezzi_musei (id_museo, id_fascia_prezzo, prezzo) VALUES
(1, 1, 18.00), -- Egizio Intero
(1, 2, 15.00), -- Egizio Ridotto
(1, 3, 0.00),  -- Egizio Gratuito
(6, 1, 15.00), -- Mole Intero
(6, 2, 12.00), -- Mole Ridotto
(7, 1, 10.00), -- GAM Intero
(7, 2, 8.00);  -- GAM Ridotto

-- 5.2 BIBLIOTECHE
INSERT INTO biblioteche (id, pubblico, wifi, area_computer, area_bambini) VALUES
(2, TRUE, TRUE, TRUE, TRUE),
(10, TRUE, FALSE, FALSE, FALSE);

-- 5.3 ZONE VERDI
INSERT INTO zone_verdi (id, area_mq, tipologia, dog_friendly, ristoro, ciclabile) VALUES
(3, 421000.00, 'PARCO_URBANO', TRUE, TRUE, TRUE),
(8, 70000.00, 'PARCO_STORICO', FALSE, FALSE, FALSE),
(9, 837000.00, 'PARCO_URBANO', TRUE, TRUE, TRUE);

-- 5.4 RISTORANTI
INSERT INTO ristoranti (id, tipo_cucina, fascia_prezzo, dog_friendly, per_celiaci, posti_esterni) VALUES
(4, 'ITALIANA', 'MEDIO', TRUE, TRUE, TRUE),
(11, 'ITALIANA', 'LUSSO', FALSE, TRUE, TRUE),
(12, 'PIZZERIA', 'MEDIO', TRUE, TRUE, TRUE);

-- 5.5 LOCALI
INSERT INTO locali (id, tipo_locale, atmosfera, fascia_prezzo, apertura_serale, per_celiaci, posti_esterni) VALUES
(5, 'CAFFE', 'ELEGANTE', 'ALTO', FALSE, FALSE, TRUE),
(13, 'CAFFE', 'INFORMALE', 'MEDIO', FALSE, FALSE, TRUE),
(14, 'BAR', 'ELEGANTE', 'ALTO', TRUE, TRUE, TRUE),
(15, 'COCKTAIL_BAR', 'FESTOSA', 'MEDIO', TRUE, TRUE, TRUE);

-- 6. ORARI DI APERTURA
INSERT INTO orario_apertura (id_luogo_interesse, giorno, apertura, chiusura) VALUES
(1, 'LUNEDI', '09:00:00', '14:00:00'),
(1, 'MARTEDI', '09:00:00', '18:30:00'),
(1, 'MERCOLEDI', '09:00:00', '18:30:00'),
(1, 'GIOVEDI', '09:00:00', '18:30:00'),
(1, 'VENERDI', '09:00:00', '18:30:00'),
(1, 'SABATO', '09:00:00', '18:30:00'),
(1, 'DOMENICA', '09:00:00', '18:30:00'),
(2, 'LUNEDI', '14:00:00', '19:50:00'),
(2, 'MARTEDI', '08:15:00', '19:50:00'),
(2, 'MERCOLEDI', '08:15:00', '19:50:00'),
(2, 'GIOVEDI', '08:15:00', '19:50:00'),
(2, 'VENERDI', '08:15:00', '19:50:00'),
(2, 'SABATO', '10:30:00', '18:00:00'),
(4, 'MARTEDI', '12:30:00', '14:30:00'),
(4, 'MARTEDI', '19:30:00', '22:30:00'),
(4, 'MERCOLEDI', '12:30:00', '14:30:00'),
(4, 'MERCOLEDI', '19:30:00', '22:30:00'),
(4, 'GIOVEDI', '12:30:00', '14:30:00'),
(4, 'GIOVEDI', '19:30:00', '22:30:00'),
(4, 'VENERDI', '12:30:00', '14:30:00'),
(4, 'VENERDI', '19:30:00', '22:30:00'),
(4, 'SABATO', '12:30:00', '14:30:00'),
(4, 'SABATO', '19:30:00', '22:30:00'),
(5, 'LUNEDI', '08:00:00', '20:00:00'),
(5, 'MARTEDI', '08:00:00', '20:00:00'),
(5, 'GIOVEDI', '08:00:00', '20:00:00'),
(5, 'VENERDI', '08:00:00', '20:00:00'),
(5, 'SABATO', '08:00:00', '20:00:00'),
(5, 'DOMENICA', '08:00:00', '20:00:00'),
(6, 'LUNEDI', '09:00:00', '19:00:00'),
(6, 'MERCOLEDI', '09:00:00', '19:00:00'),
(6, 'GIOVEDI', '09:00:00', '19:00:00'),
(6, 'VENERDI', '09:00:00', '19:00:00'),
(6, 'SABATO', '09:00:00', '19:00:00'),
(6, 'DOMENICA', '09:00:00', '19:00:00'),
(11, 'MARTEDI', '12:30:00', '14:30:00'),
(11, 'MARTEDI', '19:30:00', '22:30:00'),
(11, 'MERCOLEDI', '12:30:00', '14:30:00'),
(11, 'MERCOLEDI', '19:30:00', '22:30:00'),
(11, 'GIOVEDI', '12:30:00', '14:30:00'),
(11, 'GIOVEDI', '19:30:00', '22:30:00'),
(11, 'VENERDI', '12:30:00', '14:30:00'),
(11, 'VENERDI', '19:30:00', '22:30:00'),
(11, 'SABATO', '12:30:00', '14:30:00'),
(11, 'SABATO', '19:30:00', '22:30:00'),
(13, 'LUNEDI', '08:30:00', '19:30:00'),
(13, 'MARTEDI', '08:30:00', '19:30:00'),
(13, 'GIOVEDI', '08:30:00', '19:30:00'),
(13, 'VENERDI', '08:30:00', '19:30:00'),
(13, 'SABATO', '08:30:00', '19:30:00'),
(13, 'DOMENICA', '08:30:00', '19:30:00'),
(15, 'MERCOLEDI', '18:00:00', '02:00:00'),
(15, 'GIOVEDI', '18:00:00', '02:00:00'),
(15, 'VENERDI', '18:00:00', '03:00:00'),
(15, 'SABATO', '18:00:00', '03:00:00'),
(15, 'DOMENICA', '18:00:00', '01:00:00');

-- 7. EVENTI E DATE EVENTO
INSERT INTO eventi (nome, descrizione, tipologia, prezzo, prenotazione, pubblico, id_luogo_interesse) VALUES
('Notte Reale al Museo Egizio', 'Visita guidata in notturna tra i reperti dei Faraoni.', 'VISITA_GUIDATA', 22.00, TRUE, 'TUTTI', 1),
('Torino Jazz Festival - Concerti al Parco', 'Performance live di musica jazz immersi nel verde.', 'CONCERTO', 0.00, FALSE, 'TUTTI', 3),
('CioccolaTO - Festival del Cioccolato', 'Degustazioni, workshop e masterclass dedicati al celebre gianduiotto e al cioccolato torinese.', 'FESTIVAL', 0.00, FALSE, 'TUTTI', 3),
('Mostra Speciale: Scienza ed Enigmi nell Antico Egitto', 'Esposizione temporanea con reperti inediti e laboratori didattici per famiglie.', 'MOSTRA', 12.00, TRUE, 'TUTTI', 1),
('Proiezione Sotto le Stelle: Classici del Cinema', 'Rassegna cinematografica all aperto nel cortile della Mole Antonelliana.', 'PROIEZIONE', 6.50, TRUE, 'GIOVANI', 6),
('Workshop di Arte Contemporanea e Pittura', 'Laboratorio pratico di pittura e scultura guidato da artisti locali.', 'WORKSHOP', 15.00, TRUE, 'ADULTI', 7),
('Incontro con l Autore e Presentazione Libro', 'Presentazione del nuovo romanzo storico ambientato nella Torino del Risorgimento.', 'PRESENTAZIONE_LIBRO', 0.00, FALSE, 'ADULTI', 2),
('Letture Animate e Caccia al Tesoro', 'Pomeriggio di favole e giochi interattivi dedicati ai più piccoli.', 'EVENTO_BAMBINI', 5.00, TRUE, 'BAMBINI', 10),
('Aperitivo in Musica & Dj Set', 'Serata con musica lounge, cocktail d avanguardia e finger food piemontese.', 'EVENTO_SPECIALE', 18.00, FALSE, 'GIOVANI', 15),
('Gran Galà della Cucina Sabauda', 'Cena degustazione a tema con abbinamento vini del Piemonte e guida storica ai piatti.', 'EVENTO_SPECIALE', 85.00, TRUE, 'ADULTI', 11);

INSERT INTO data_evento (evento_id, data_inizio, data_fine, ora_inizio, ora_fine) VALUES
(1, '2026-10-15', '2026-10-15', '20:30:00', '23:30:00'),
(2, '2026-06-20', '2026-06-22', '18:00:00', '23:00:00'),
(3, '2026-11-20', '2026-11-29', '10:00:00', '22:00:00'),
(4, '2026-09-01', '2026-12-15', '09:00:00', '18:00:00'),
(5, '2026-07-10', '2026-07-10', '21:00:00', '23:30:00'),
(6, '2026-10-05', '2026-10-05', '15:00:00', '18:00:00'),
(7, '2026-11-12', '2026-11-12', '18:00:00', '20:00:00'),
(8, '2026-09-19', '2026-09-19', '15:30:00', '17:30:00'),
(9, '2026-10-31', '2026-10-31', '19:00:00', '02:00:00'),
(10, '2026-12-05', '2026-12-05', '20:00:00', '23:30:00');

-- 8. RECENSIONI
INSERT INTO recensioni (voto, commento, id_utente, id_luogo_interesse) VALUES
(5, 'Esperienza fantastica, reperti tenuti benissimo e spiegazioni chiari.', 3, 1),
(4, 'Bellissimo parco per passeggiare o correre la mattina, sempre ben tenuto.', 4, 3),
(5, 'Agnolotti al plin eccezionali! Un vero tuffo nella tradizione piemontese.', 3, 4),
(3, 'Caffè storico affascinante e tramezzini ottimi, ma i prezzi sono un po elevati.', 4, 5),
(5, 'Ascensore panoramico mozzafiato e museo davvero unico!', 3, 6),
(5, 'Il bicerin originale e imbattibile, una tappa obbligatoria a Torino.', 4, 13),
(4, 'Ottima pizza e servizio rapido nonostante la fila.', 3, 12);

-- 9. PREFERITI (Luoghi ed Eventi)
INSERT INTO utente_luoghi_preferiti (id_utente, id_luogo_interesse) VALUES
(3, 1), (3, 4), (3, 6),
(4, 1), (4, 3), (4, 13);

INSERT INTO utente_eventi_preferiti (id_utente, id_evento) VALUES
(3, 1), (3, 3), (3, 9),
(4, 2), (4, 4), (4, 5);