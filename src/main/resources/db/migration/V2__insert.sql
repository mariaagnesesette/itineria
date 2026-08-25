-- =================================================================
-- POPOLAMENTO DATABASE - CITTA DI TORINO
-- Dati reali relativi ai principali luoghi di interesse della città
-- =================================================================

-- 1. CITTA
INSERT INTO citta (nome, regione, descrizione) VALUES
('Torino', 'PIEMONTE', 'Torino, capoluogo del Piemonte e prima capitale d''Italia, è una città elegante che unisce raffinatezza sabauda, industria e innovazione. Celebre per i suoi lunghi portici, le piazze barocche, i palazzi reali patrimonio UNESCO e musei di fama mondiale come il Museo Egizio, offre anche un ricco patrimonio enogastronomico, dai celebri caffè storici alla tradizione del cioccolato e del vermouth. Attraversata dal fiume Po e circondata dalle Alpi, Torino è oggi un vivace polo universitario, culturale e tecnologico, meta crescente del turismo italiano e internazionale.');

-- 2. UTENTI (password comune per tutti: password123)
INSERT INTO utenti (nome, cognome, data_nascita, username, email, password, ruolo) VALUES
('Mario', 'Rossi', '1980-03-14', 'mrossi', 'mario.rossi@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'ADMIN'),
('Giulia', 'Bianchi', '1988-07-22', 'gbianchi', 'giulia.bianchi@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'MANAGER'),
('Davide', 'Ferrero', '1985-11-05', 'dferrero', 'davide.ferrero@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'MANAGER'),
('Chiara', 'Bruno', '1992-02-18', 'cbruno', 'chiara.bruno@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'MANAGER'),
('Luca', 'Verdi', '1998-01-15', 'lverdi', 'luca.verdi@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'USER'),
('Elena', 'Ferrari', '2001-06-30', 'eferrari', 'elena.ferrari@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'USER'),
('Federico', 'Colombo', '1995-09-09', 'fcolombo', 'federico.colombo@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'USER'),
('Sara', 'Gallo', '1999-12-03', 'sgallo', 'sara.gallo@email.it', '$2a$10$nX0NFu1mfurdQbYI569VeeNlRAPACv0tz1rkZ8IiOOO8lKCLSePeO', 'USER');

-- 3. FASCE PREZZO
INSERT INTO fasce_prezzo (nome) VALUES
('Intero'),
('Ridotto (Under 26 / Over 65)'),
('Gratuito (Under 6)');

-- =================================================================
-- 4. LUOGHI DI INTERESSE (id_citta = 1)
-- Ordine di inserimento (determina gli id 1-31):
--  1-9   MUSEI            (manager id 2 - Giulia Bianchi)
--  10-13 BIBLIOTECHE       (manager id 2 - Giulia Bianchi)
--  14-19 ZONE VERDI        (manager id 3 - Davide Ferrero)
--  20-25 RISTORANTI        (manager id 3 - Davide Ferrero)
--  26-31 LOCALI            (manager id 4 - Chiara Bruno)
-- =================================================================

INSERT INTO luoghi_interesse
(nome, descrizione, tipo, accessibilita, indirizzo, sempre_aperto, link_sito, numero_telefono, email, id_citta, id_manager) VALUES

-- MUSEI (1-9)
('Museo Egizio', 'Fondato nel 1824 grazie alla collezione di Bernardino Drovetti, il Museo Egizio di Torino è il più antico museo al mondo interamente dedicato alla civiltà dell''antico Egitto e, dopo quello del Cairo, il più importante per ricchezza della collezione. Ospita oltre 40.000 reperti tra cui la Tomba di Kha, il Papiro delle Miniere d''Oro e straordinarie collezioni di sarcofagi, statue e oggetti di vita quotidiana. Il percorso museale, rinnovato nel 2015, si sviluppa su quattro piani e racconta oltre 5000 anni di storia egizia attraverso allestimenti moderni e coinvolgenti.', 'MUSEO', 'COMPLETA', 'Via Accademia delle Scienze, 6', FALSE, 'https://museoegizio.it', '0115617776', 'info@museoegizio.it', 1, 2),
('Mole Antonelliana e Museo Nazionale del Cinema', 'Simbolo indiscusso di Torino, la Mole Antonelliana fu progettata dall''architetto Alessandro Antonelli e completata nel 1889. Con i suoi 167,5 metri è stata per decenni l''edificio in muratura più alto d''Europa. Dal 2000 ospita il Museo Nazionale del Cinema, che ripercorre la storia della settima arte attraverso installazioni, cimeli, costumi di scena e la spettacolare Aula del Tempio. L''ascensore panoramico conduce fino a 85 metri d''altezza, regalando una vista a 360 gradi sulla città e sulle Alpi circostanti.', 'MUSEO', 'COMPLETA', 'Via Montebello, 20', FALSE, 'https://museocinema.it', '0118138560', 'info@museocinema.it', 1, 2),
('Palazzo Madama - Museo Civico d''Arte Antica', 'Palazzo Madama racconta duemila anni di storia torinese: sorto sui resti delle antiche porte romane della città, fu residenza sabauda e sede del primo Senato del Regno d''Italia. La facciata barocca, capolavoro di Filippo Juvarra, introduce oggi al Museo Civico d''Arte Antica, che custodisce collezioni di pittura, scultura, ceramiche, arti decorative e preziosi codici miniati. Nel 1997 è stato inserito dall''UNESCO tra i siti patrimonio dell''umanità delle Residenze Sabaude.', 'MUSEO', 'PARZIALE', 'Piazza Castello', FALSE, 'https://palazzomadamatorino.it', '0114433501', 'info@palazzomadamatorino.it', 1, 2),
('Musei Reali di Torino', 'Il complesso dei Musei Reali riunisce in un unico percorso il Palazzo Reale, l''Armeria Reale, la Galleria Sabauda, il Museo di Antichità e i Giardini Reali. Per secoli residenza dei Savoia, il palazzo conserva appartamenti sfarzosi, arazzi, mobili d''epoca e una delle raccolte di armi antiche più importanti al mondo. La Galleria Sabauda espone capolavori di Rembrandt, Van Dyck e Beato Angelico. Il complesso fa parte del sito UNESCO delle Residenze Sabaude.', 'MUSEO', 'PARZIALE', 'Piazzetta Reale, 1', FALSE, 'https://museireali.beniculturali.it', '0115211106', 'mr-to@cultura.gov.it', 1, 2),
('GAM - Galleria Civica d''Arte Moderna e Contemporanea', 'La Galleria Civica d''Arte Moderna e Contemporanea di Torino, tra i primi musei italiani dedicati all''arte moderna, conserva oltre 45.000 opere tra dipinti, sculture, disegni, fotografie e installazioni video dal XIX secolo a oggi. Il percorso espositivo alterna capolavori di Fontana, Boetti, Pistoletto e Modigliani a mostre temporanee di respiro internazionale, in un edificio razionalista circondato dal verde di Via Magenta.', 'MUSEO', 'COMPLETA', 'Via Magenta, 31', FALSE, 'https://gamtorino.it', '0114429518', 'gam@fondazionetorinomusei.it', 1, 2),
('MAUTO - Museo Nazionale dell''Automobile', 'Il Museo Nazionale dell''Automobile di Torino è uno dei più importanti musei automobilistici al mondo, con oltre 200 vetture originali esposte lungo un percorso che ripercorre l''evoluzione dell''automobile dalle origini a oggi. Tra i pezzi più celebri figurano la Bertone Carabo, la Itala che vinse il Pechino-Parigi del 1907 e straordinari esemplari Fiat, Lancia e Ferrari, in un allestimento pensato dall''architetto Cino Zucchi.', 'MUSEO', 'COMPLETA', 'Corso Unità d''Italia, 40', FALSE, 'https://www.museoauto.it', '0116776008', 'info@museoauto.it', 1, 2),
('Museo Nazionale della Montagna', 'Fondato nel 1874, il Museo Nazionale della Montagna è il più antico museo alpino d''Europa. Situato sul colle di Sassi accanto al Monte dei Cappuccini, racconta la storia dell''alpinismo, dell''esplorazione e della cultura di montagna attraverso oggetti, fotografie storiche, opere d''arte e cimeli delle grandi spedizioni himalayane italiane, tra cui quella al K2 del 1954.', 'MUSEO', 'PARZIALE', 'Piazzale Monte dei Cappuccini, 7', FALSE, 'https://www.museomontagna.org', '0116604104', 'info@museomontagna.org', 1, 2),
('Pinacoteca Giovanni e Marella Agnelli', 'Situata sul tetto dello storico stabilimento Lingotto, in una struttura in vetro e acciaio progettata da Renzo Piano, la Pinacoteca Giovanni e Marella Agnelli ospita una raccolta di 25 capolavori scelti personalmente dagli Agnelli, tra cui opere di Canaletto, Matisse, Renoir e Picasso. La visita si conclude sulla pista di collaudo dell''ex fabbrica Fiat, oggi giardino pensile con vista panoramica sulla città.', 'MUSEO', 'COMPLETA', 'Via Nizza, 230', FALSE, 'https://www.pinacoteca-agnelli.it', '0110062713', 'info@pinacoteca-agnelli.it', 1, 2),
('Museo Nazionale del Risorgimento Italiano', 'Ospitato all''interno del monumentale Palazzo Carignano, luogo di nascita di Vittorio Emanuele II e sede del primo Parlamento del Regno d''Italia, il Museo Nazionale del Risorgimento Italiano racconta attraverso oltre 30 sale il percorso che portò all''Unità d''Italia, dalla Rivoluzione francese alla Prima Guerra Mondiale, con documenti, dipinti, uniformi e cimeli originali.', 'MUSEO', 'LIMITATA', 'Piazza Carignano, 5', FALSE, 'https://museorisorgimentotorino.it', '0115621147', 'info@museorisorgimentotorino.it', 1, 2),

-- BIBLIOTECHE (10-13)
('Biblioteca Civica Centrale', 'Principale biblioteca pubblica della Città di Torino, offre un ricco patrimonio librario multimediale, sale studio, postazioni internet e un ampio calendario di iniziative culturali, presentazioni di libri e incontri con gli autori. Fa parte del sistema delle Biblioteche Civiche Torinesi, una rete diffusa capillarmente su tutto il territorio cittadino.', 'BIBLIOTECA', 'COMPLETA', 'Via della Cittadella, 5', FALSE, 'https://bct.comune.torino.it', '01101129800', 'biblioteche.civiche@comune.torino.it', 1, 2),
('Biblioteca Nazionale Universitaria di Torino', 'Tra le biblioteche pubbliche statali più importanti d''Italia, conserva oltre due milioni di volumi, manoscritti, incunaboli e collezioni di pregio, tra cui codici miniati e fondi appartenuti a illustri famiglie piemontesi. Danneggiata da un grave incendio nel 1904 che distrusse parte del patrimonio, oggi è un punto di riferimento per la ricerca accademica e umanistica torinese.', 'BIBLIOTECA', 'PARZIALE', 'Piazza Carlo Alberto, 3', FALSE, 'https://www.bnto.beniculturali.it', '0118120340', 'bnto@cultura.gov.it', 1, 2),
('Biblioteca Reale', 'Voluta da Carlo Alberto di Savoia e aperta nel 1839, la Biblioteca Reale custodisce oltre 200.000 tra libri, manoscritti, disegni e stampe di inestimabile valore. Il suo tesoro più celebre è l''Autoritratto di Leonardo da Vinci a sanguigna, esposto a rotazione per motivi conservativi insieme ad altri disegni del maestro fiorentino e di artisti come Rembrandt.', 'BIBLIOTECA', 'LIMITATA', 'Piazza Castello, 191', FALSE, 'https://museireali.beniculturali.it/biblioteca-reale', '011543855', 'mr-to.biblioteca@cultura.gov.it', 1, 2),
('Biblioteca Civica Musicale Andrea della Corte', 'Dedicata al musicologo Andrea Della Corte, questa biblioteca civica specializzata conserva un vasto patrimonio di spartiti, libri di musica, dischi e registrazioni storiche. È un punto di riferimento per musicisti, studenti dei conservatori e appassionati, con sale di ascolto e un fondo dedicato alla tradizione musicale piemontese.', 'BIBLIOTECA', 'PARZIALE', 'Piazza Bodoni, 5', FALSE, 'https://www.comune.torino.it/biblioteche', '0114431900', 'biblioteca.dellacorte@comune.torino.it', 1, 2),

-- ZONE VERDI (14-19)
('Parco del Valentino', 'Il più celebre parco pubblico di Torino si estende per oltre 40 ettari lungo le sponde del fiume Po. Al suo interno sorgono il Castello del Valentino, residenza sabauda patrimonio UNESCO e sede della Facoltà di Architettura, il pittoresco Borgo Medievale costruito per l''Esposizione del 1884 e l''Orto Botanico universitario. È il polmone verde preferito dai torinesi per passeggiate, jogging e picnic lungo il fiume.', 'ZONA_VERDE', 'COMPLETA', 'Corso Massimo d''Azeglio', TRUE, NULL, NULL, NULL, 1, 3),
('Giardini Reali', 'Progettati nel XVII secolo da André Le Nôtre, celebre giardiniere di Versailles, i Giardini Reali si estendono alle spalle di Palazzo Reale offrendo un''oasi di quiete nel cuore della città. Tra vialetti alberati, fontane e aiuole geometriche, i giardini fanno parte del percorso dei Musei Reali e rappresentano un raro esempio di giardino formale barocco conservato in ambito urbano.', 'ZONA_VERDE', 'PARZIALE', 'Piazza Castello, 191', FALSE, 'https://museireali.beniculturali.it', '0115211106', NULL, 1, 3),
('Parco della Pellerina', 'Con circa 83 ettari di estensione, la Pellerina è il parco pubblico più grande di Torino. Attraversato dalla Dora Riparia, offre ampi prati, piste ciclabili, un laghetto, campi sportivi e aree gioco per bambini. È la sede storica del Luna Park di Natale ed è molto amato dai torinesi per attività sportive all''aperto e grandi eventi cittadini.', 'ZONA_VERDE', 'COMPLETA', 'Corso Appio Claudio, 2', TRUE, NULL, NULL, NULL, 1, 3),
('Parco Michelotti', 'Affacciato sulle rive del Po, il Parco Michelotti prende il nome dall''omonimo giardino zoologico che vi sorgeva fino agli anni ottanta. Oggi è un''area naturalistica protetta che ospita la Cascina Bert e alcuni edifici storici, meta ideale per passeggiate lungo il fiume, birdwatching e attività di educazione ambientale rivolte alle scuole.', 'ZONA_VERDE', 'PARZIALE', 'Corso Casale, 200', TRUE, NULL, NULL, NULL, 1, 3),
('Parco della Tesoriera', 'Il parco circonda l''omonima villa settecentesca, un tempo residenza del tesoriere generale del Regno di Sardegna. Con i suoi vialetti ombreggiati, il laghetto con i pesci rossi e le aiuole fiorite, è uno dei giardini storici meglio conservati della città e ospita rassegne teatrali estive nella cornice della villa.', 'ZONA_VERDE', 'COMPLETA', 'Corso Francia, 186', TRUE, NULL, NULL, NULL, 1, 3),
('Orto Botanico dell''Università di Torino', 'Fondato nel 1729 dall''Università di Torino, l''Orto Botanico si trova all''interno del Parco del Valentino e custodisce oltre 2.000 specie vegetali, tra cui piante secolari, un giardino roccioso alpino e serre storiche. Rappresenta un importante centro di ricerca botanica e un percorso didattico affascinante per famiglie e appassionati di natura.', 'ZONA_VERDE', 'PARZIALE', 'Viale Pier Andrea Mattioli, 25', FALSE, 'https://www.ortobotanicotorino.it', '0116705987', 'ortobotanico@unito.it', 1, 3),

-- RISTORANTI (20-25)
('Ristorante Del Cambio', 'Aperto nel 1757, Del Cambio è uno dei ristoranti più antichi d''Italia ancora in attività. Punto di riferimento della Torino risorgimentale, era il ristorante preferito di Camillo Benso Conte di Cavour, che vi teneva il proprio tavolo riservato. Oggi, sotto la guida di uno chef stellato, propone una cucina che reinterpreta i grandi classici piemontesi in chiave contemporanea, in sale sontuose decorate a specchi e stucchi dorati.', 'RISTORANTE', 'PARZIALE', 'Piazza Carignano, 2', FALSE, 'https://www.delcambio.it', '011546690', 'welcome@delcambio.it', 1, 3),
('Trattoria Porto di Savona', 'Storica trattoria affacciata su Piazza Vittorio Veneto, aperta dal 1863, propone una cucina che unisce la tradizione piemontese a una rinomata pizza napoletana. Le sale in stile antico e i tavoli all''aperto sulla piazza ne fanno una tappa amata sia dai torinesi che dai turisti in cerca di un''atmosfera genuina.', 'RISTORANTE', 'COMPLETA', 'Piazza Vittorio Veneto, 35', FALSE, 'https://www.portodisavona.it', '0118173500', 'info@portodisavona.it', 1, 3),
('Ristorante Consorzio', 'Piccolo ristorante nel cuore del Quadrilatero Romano, il Consorzio propone una cucina di territorio basata su una selezione rigorosa di piccoli produttori piemontesi. La carta dei vini, tra le più curate della città, valorizza etichette artigianali e vitigni autoctoni, in un ambiente semplice ed elegante molto apprezzato dagli intenditori.', 'RISTORANTE', 'LIMITATA', 'Via Monte di Pietà, 23', FALSE, 'https://www.ristoranteconsorzio.it', '0112767661', 'info@ristoranteconsorzio.it', 1, 3),
('Le Tre Galline', 'Uno dei ristoranti più antichi di Torino, le cui origini risalgono al 1300, quando era luogo di sosta per i mercanti. Oggi propone una cucina piemontese di alto livello, dai celebri agnolotti al plin al bollito misto, servita in un ambiente elegante che conserva il fascino della tradizione.', 'RISTORANTE', 'LIMITATA', 'Via Bellezia, 37', FALSE, 'https://www.tregalline.it', '0114366553', 'info@tregalline.it', 1, 3),
('La Pista - Ristorante Lingotto', 'Situato sulla pista di collaudo panoramica dell''ex fabbrica Fiat Lingotto, questo ristorante offre una vista mozzafiato sulla città e sulle Alpi. La cucina propone piatti piemontesi rivisitati e specialità di pesce, in un contesto unico che unisce storia industriale e design contemporaneo.', 'RISTORANTE', 'COMPLETA', 'Via Nizza, 262', FALSE, 'https://www.lapistalingotto.it', '0116645180', 'info@lapistalingotto.it', 1, 3),
('Antiche Sere', 'Trattoria familiare nel quartiere di San Donato, propone una cucina piemontese casalinga e genuina in un ambiente informale e conviviale. Il menu, che cambia con le stagioni, è un omaggio ai piatti della tradizione contadina piemontese, accompagnati da una selezione di vini regionali.', 'RISTORANTE', 'NON_SPECIFICATA', 'Via Cenischia, 9', FALSE, NULL, '0113854347', NULL, 1, 3),

-- LOCALI (26-31)
('Caffè Mulassano', 'Aperto nel 1907, il Caffè Mulassano è tra i locali storici più celebri di Torino: qui, nel 1926, fu inventato il tramezzino. I suoi arredi originali in noce, ottone e marmo, dichiarati bene di interesse storico, ne fanno un piccolo gioiello Art Nouveau nel cuore di Piazza Castello.', 'LOCALE', 'LIMITATA', 'Piazza Castello, 15', FALSE, 'https://www.caffemulassano.it', '0115618200', 'info@caffemulassano.it', 1, 4),
('Caffè Al Bicerin', 'Aperto nel 1763, è il locale dove nacque il celebre bicerin, la storica bevanda torinese a base di caffè, cioccolato e crema di latte. Frequentato in passato da Alexandre Dumas e Nietzsche, conserva ancora oggi l''arredamento originale in stile ottocentesco.', 'LOCALE', 'LIMITATA', 'Piazza della Consolata, 5', FALSE, 'https://www.bicerin.it', '0114369325', 'bicerin@bicerin.it', 1, 4),
('Caffè Torino', 'Elegante caffè storico del 1903, situato sotto i portici di Piazza San Carlo. Celebre per il toro in bronzo incastonato nel marciapiede antistante, simbolo di buona fortuna per i torinesi, è un salotto cittadino ideale per un aperitivo o una colazione in stile Belle Époque.', 'LOCALE', 'COMPLETA', 'Piazza San Carlo, 204', FALSE, 'https://www.caffetorino1903.it', '011545101', 'info@caffetorino1903.it', 1, 4),
('Caffè San Carlo', 'Uno dei primi locali d''Italia a essere illuminato a gas, il Caffè San Carlo è un salotto ottocentesco affacciato sulla piazza omonima. Con i suoi specchi, stucchi dorati e lampadari di cristallo, ha ospitato nei secoli intellettuali, politici e artisti, diventando un simbolo dell''eleganza torinese.', 'LOCALE', 'COMPLETA', 'Piazza San Carlo, 156', FALSE, 'https://www.caffesancarlo.it', '0115326875', 'info@caffesancarlo.it', 1, 4),
('Baratti & Milano', 'Aperta nel 1875 all''interno della elegante Galleria Subalpina, Baratti & Milano è una storica pasticceria e caffetteria nota per i suoi cioccolatini gianduia, i marron glacés e gli arredi liberty originali in legno e vetro, che ne fanno una delle sale da tè più affascinanti d''Europa.', 'LOCALE', 'PARZIALE', 'Piazza Castello, 27', FALSE, 'https://www.barattiemilano.it', '0114407138', 'info@barattiemilano.it', 1, 4),
('Editoriale', 'Locale moderno affacciato su Piazza Emanuele Filiberto, nel cuore della movida di San Salvario. Propone una cocktail list creativa e in continua evoluzione, accompagnata da proposte gastronomiche ricercate, in un ambiente dal design curato che lo rende uno dei punti di riferimento della vita notturna torinese.', 'LOCALE', 'COMPLETA', 'Piazza Emanuele Filiberto, 12/E', FALSE, 'https://www.editorialetorino.it', '0119127070', 'info@editorialetorino.it', 1, 4);

-- =================================================================
-- 5. TABELLE SPECIALIZZATE (id coincide con luoghi_interesse.id)
-- =================================================================

-- 5.1 MUSEI E PREZZI
INSERT INTO musei (id, guide_prenotabili, tipologia, bar_interno) VALUES
(1, TRUE, 'ARCHEOLOGIA', TRUE),
(2, TRUE, 'MULTIDISCIPLINARE', TRUE),
(3, TRUE, 'ARTE', FALSE),
(4, TRUE, 'STORIA', TRUE),
(5, TRUE, 'ARTE', TRUE),
(6, TRUE, 'TRASPORTI', TRUE),
(7, FALSE, 'STORIA', TRUE),
(8, TRUE, 'ARTE', TRUE),
(9, TRUE, 'STORIA', FALSE);

INSERT INTO prezzi_musei (id_museo, id_fascia_prezzo, prezzo) VALUES
(1, 1, 18.00), (1, 2, 15.00), (1, 3, 0.00),
(2, 1, 15.00), (2, 2, 12.00), (2, 3, 0.00),
(3, 1, 10.00), (3, 2, 8.00), (3, 3, 0.00),
(4, 1, 15.00), (4, 2, 12.00), (4, 3, 0.00),
(5, 1, 10.00), (5, 2, 8.00), (5, 3, 0.00),
(6, 1, 12.00), (6, 2, 9.00), (6, 3, 0.00),
(7, 1, 10.00), (7, 2, 7.00), (7, 3, 0.00),
(8, 1, 10.00), (8, 2, 8.00), (8, 3, 0.00),
(9, 1, 10.00), (9, 2, 8.00), (9, 3, 0.00);

-- 5.2 BIBLIOTECHE
INSERT INTO biblioteche (id, pubblico, wifi, area_computer, area_bambini) VALUES
(10, TRUE, TRUE, TRUE, TRUE),
(11, TRUE, TRUE, FALSE, FALSE),
(12, FALSE, FALSE, FALSE, FALSE),
(13, TRUE, TRUE, FALSE, FALSE);

-- 5.3 ZONE VERDI
INSERT INTO zone_verdi (id, area_mq, tipologia, dog_friendly, ristoro, ciclabile) VALUES
(14, 421000.00, 'PARCO_URBANO', TRUE, TRUE, TRUE),
(15, 68000.00, 'PARCO_STORICO', FALSE, FALSE, FALSE),
(16, 837000.00, 'PARCO_URBANO', TRUE, TRUE, TRUE),
(17, 59000.00, 'PARCO_NATURALE', TRUE, FALSE, TRUE),
(18, 75000.00, 'GIARDINO_PUBBLICO', TRUE, TRUE, FALSE),
(19, 30000.00, 'ORTO_BOTANICO', FALSE, FALSE, FALSE);

-- 5.4 RISTORANTI
INSERT INTO ristoranti (id, tipo_cucina, fascia_prezzo, dog_friendly, per_celiaci, posti_esterni) VALUES
(20, 'ITALIANA', 'LUSSO', FALSE, TRUE, FALSE),
(21, 'PIZZERIA', 'MEDIO', TRUE, TRUE, TRUE),
(22, 'ITALIANA', 'COSTOSO', FALSE, TRUE, FALSE),
(23, 'ITALIANA', 'COSTOSO', FALSE, TRUE, FALSE),
(24, 'ITALIANA', 'COSTOSO', TRUE, TRUE, TRUE),
(25, 'ITALIANA', 'MEDIO', TRUE, FALSE, TRUE);

-- 5.5 LOCALI
INSERT INTO locali (id, tipo_locale, atmosfera, fascia_prezzo, apertura_serale, per_celiaci, posti_esterni) VALUES
(26, 'CAFFE', 'ELEGANTE', 'ALTO', FALSE, FALSE, FALSE),
(27, 'CAFFE', 'ELEGANTE', 'ALTO', FALSE, FALSE, TRUE),
(28, 'CAFFE', 'ELEGANTE', 'ALTO', TRUE, FALSE, TRUE),
(29, 'CAFFE', 'ELEGANTE', 'MEDIO', TRUE, FALSE, TRUE),
(30, 'CAFFE', 'ELEGANTE', 'ALTO', FALSE, TRUE, FALSE),
(31, 'COCKTAIL_BAR', 'FESTOSA', 'MEDIO', TRUE, TRUE, TRUE);

-- =================================================================
-- 6. ORARI DI APERTURA
-- =================================================================

-- Museo Egizio (1)
INSERT INTO orario_apertura (id_luogo_interesse, giorno, apertura, chiusura) VALUES
(1, 'LUNEDI', '09:00:00', '14:00:00'),
(1, 'MARTEDI', '09:00:00', '18:30:00'),
(1, 'MERCOLEDI', '09:00:00', '18:30:00'),
(1, 'GIOVEDI', '09:00:00', '18:30:00'),
(1, 'VENERDI', '09:00:00', '18:30:00'),
(1, 'SABATO', '09:00:00', '18:30:00'),
(1, 'DOMENICA', '09:00:00', '18:30:00'),
-- Mole Antonelliana / Museo del Cinema (2) - chiuso il martedì
(2, 'LUNEDI', '09:00:00', '19:00:00'),
(2, 'MERCOLEDI', '09:00:00', '19:00:00'),
(2, 'GIOVEDI', '09:00:00', '19:00:00'),
(2, 'VENERDI', '09:00:00', '19:00:00'),
(2, 'SABATO', '09:00:00', '19:00:00'),
(2, 'DOMENICA', '09:00:00', '19:00:00'),
-- Palazzo Madama (3) - chiuso il martedì
(3, 'LUNEDI', '10:00:00', '18:00:00'),
(3, 'MERCOLEDI', '10:00:00', '18:00:00'),
(3, 'GIOVEDI', '10:00:00', '18:00:00'),
(3, 'VENERDI', '10:00:00', '18:00:00'),
(3, 'SABATO', '10:00:00', '18:00:00'),
(3, 'DOMENICA', '10:00:00', '18:00:00'),
-- Musei Reali (4) - chiuso il lunedì
(4, 'MARTEDI', '09:00:00', '19:00:00'),
(4, 'MERCOLEDI', '09:00:00', '19:00:00'),
(4, 'GIOVEDI', '09:00:00', '19:00:00'),
(4, 'VENERDI', '09:00:00', '19:00:00'),
(4, 'SABATO', '09:00:00', '19:00:00'),
(4, 'DOMENICA', '09:00:00', '19:00:00'),
-- GAM (5) - chiuso il lunedì
(5, 'MARTEDI', '10:00:00', '18:00:00'),
(5, 'MERCOLEDI', '10:00:00', '18:00:00'),
(5, 'GIOVEDI', '10:00:00', '18:00:00'),
(5, 'VENERDI', '10:00:00', '18:00:00'),
(5, 'SABATO', '10:00:00', '18:00:00'),
(5, 'DOMENICA', '10:00:00', '18:00:00'),
-- MAUTO (6)
(6, 'LUNEDI', '10:00:00', '14:00:00'),
(6, 'MARTEDI', '10:00:00', '19:00:00'),
(6, 'MERCOLEDI', '10:00:00', '19:00:00'),
(6, 'GIOVEDI', '10:00:00', '19:00:00'),
(6, 'VENERDI', '10:00:00', '19:00:00'),
(6, 'SABATO', '10:00:00', '19:00:00'),
(6, 'DOMENICA', '10:00:00', '19:00:00'),
-- Museo Nazionale della Montagna (7)
(7, 'LUNEDI', '10:00:00', '18:00:00'),
(7, 'MARTEDI', '10:00:00', '18:00:00'),
(7, 'MERCOLEDI', '10:00:00', '18:00:00'),
(7, 'GIOVEDI', '10:00:00', '18:00:00'),
(7, 'VENERDI', '10:00:00', '18:00:00'),
(7, 'SABATO', '10:00:00', '18:00:00'),
(7, 'DOMENICA', '10:00:00', '18:00:00'),
-- Pinacoteca Agnelli (8) - chiuso il lunedì
(8, 'MARTEDI', '10:00:00', '19:00:00'),
(8, 'MERCOLEDI', '10:00:00', '19:00:00'),
(8, 'GIOVEDI', '10:00:00', '19:00:00'),
(8, 'VENERDI', '10:00:00', '19:00:00'),
(8, 'SABATO', '10:00:00', '19:00:00'),
(8, 'DOMENICA', '10:00:00', '19:00:00'),
-- Museo Nazionale del Risorgimento (9) - chiuso il lunedì
(9, 'MARTEDI', '10:00:00', '18:00:00'),
(9, 'MERCOLEDI', '10:00:00', '18:00:00'),
(9, 'GIOVEDI', '10:00:00', '18:00:00'),
(9, 'VENERDI', '10:00:00', '18:00:00'),
(9, 'SABATO', '10:00:00', '18:00:00'),
(9, 'DOMENICA', '10:00:00', '18:00:00'),
-- Biblioteca Civica Centrale (10)
(10, 'LUNEDI', '14:00:00', '19:50:00'),
(10, 'MARTEDI', '08:15:00', '19:50:00'),
(10, 'MERCOLEDI', '08:15:00', '19:50:00'),
(10, 'GIOVEDI', '08:15:00', '19:50:00'),
(10, 'VENERDI', '08:15:00', '19:50:00'),
(10, 'SABATO', '10:30:00', '18:00:00'),
-- Biblioteca Nazionale Universitaria (11)
(11, 'LUNEDI', '08:30:00', '19:00:00'),
(11, 'MARTEDI', '08:30:00', '19:00:00'),
(11, 'MERCOLEDI', '08:30:00', '19:00:00'),
(11, 'GIOVEDI', '08:30:00', '19:00:00'),
(11, 'VENERDI', '08:30:00', '19:00:00'),
(11, 'SABATO', '08:30:00', '13:30:00'),
-- Biblioteca Reale (12)
(12, 'LUNEDI', '08:30:00', '17:30:00'),
(12, 'MARTEDI', '08:30:00', '17:30:00'),
(12, 'MERCOLEDI', '08:30:00', '17:30:00'),
(12, 'GIOVEDI', '08:30:00', '17:30:00'),
(12, 'VENERDI', '08:30:00', '17:30:00'),
-- Biblioteca Della Corte (13)
(13, 'MARTEDI', '10:00:00', '18:00:00'),
(13, 'MERCOLEDI', '10:00:00', '18:00:00'),
(13, 'GIOVEDI', '10:00:00', '18:00:00'),
(13, 'VENERDI', '10:00:00', '18:00:00'),
(13, 'SABATO', '10:00:00', '18:00:00'),
-- Giardini Reali (15)
(15, 'MARTEDI', '09:00:00', '19:00:00'),
(15, 'MERCOLEDI', '09:00:00', '19:00:00'),
(15, 'GIOVEDI', '09:00:00', '19:00:00'),
(15, 'VENERDI', '09:00:00', '19:00:00'),
(15, 'SABATO', '09:00:00', '19:00:00'),
(15, 'DOMENICA', '09:00:00', '19:00:00'),
-- Orto Botanico (19)
(19, 'MARTEDI', '10:00:00', '18:00:00'),
(19, 'MERCOLEDI', '10:00:00', '18:00:00'),
(19, 'GIOVEDI', '10:00:00', '18:00:00'),
(19, 'VENERDI', '10:00:00', '18:00:00'),
(19, 'SABATO', '10:00:00', '18:00:00'),
(19, 'DOMENICA', '10:00:00', '18:00:00'),
-- Ristorante Del Cambio (20)
(20, 'MARTEDI', '12:30:00', '14:30:00'), (20, 'MARTEDI', '19:30:00', '22:30:00'),
(20, 'MERCOLEDI', '12:30:00', '14:30:00'), (20, 'MERCOLEDI', '19:30:00', '22:30:00'),
(20, 'GIOVEDI', '12:30:00', '14:30:00'), (20, 'GIOVEDI', '19:30:00', '22:30:00'),
(20, 'VENERDI', '12:30:00', '14:30:00'), (20, 'VENERDI', '19:30:00', '22:30:00'),
(20, 'SABATO', '12:30:00', '14:30:00'), (20, 'SABATO', '19:30:00', '22:30:00'),
(20, 'DOMENICA', '12:30:00', '14:30:00'), (20, 'DOMENICA', '19:30:00', '22:30:00'),
-- Trattoria Porto di Savona (21)
(21, 'MARTEDI', '12:00:00', '15:00:00'), (21, 'MARTEDI', '19:00:00', '23:00:00'),
(21, 'MERCOLEDI', '12:00:00', '15:00:00'), (21, 'MERCOLEDI', '19:00:00', '23:00:00'),
(21, 'GIOVEDI', '12:00:00', '15:00:00'), (21, 'GIOVEDI', '19:00:00', '23:00:00'),
(21, 'VENERDI', '12:00:00', '15:00:00'), (21, 'VENERDI', '19:00:00', '23:00:00'),
(21, 'SABATO', '12:00:00', '15:00:00'), (21, 'SABATO', '19:00:00', '23:00:00'),
(21, 'DOMENICA', '12:00:00', '15:00:00'), (21, 'DOMENICA', '19:00:00', '23:00:00'),
-- Ristorante Consorzio (22) - chiuso domenica e lunedì
(22, 'MARTEDI', '12:30:00', '14:30:00'), (22, 'MARTEDI', '19:30:00', '22:30:00'),
(22, 'MERCOLEDI', '12:30:00', '14:30:00'), (22, 'MERCOLEDI', '19:30:00', '22:30:00'),
(22, 'GIOVEDI', '12:30:00', '14:30:00'), (22, 'GIOVEDI', '19:30:00', '22:30:00'),
(22, 'VENERDI', '12:30:00', '14:30:00'), (22, 'VENERDI', '19:30:00', '22:30:00'),
(22, 'SABATO', '12:30:00', '14:30:00'), (22, 'SABATO', '19:30:00', '22:30:00'),
-- Le Tre Galline (23) - chiuso domenica e lunedì
(23, 'MARTEDI', '12:30:00', '14:30:00'), (23, 'MARTEDI', '19:30:00', '22:30:00'),
(23, 'MERCOLEDI', '12:30:00', '14:30:00'), (23, 'MERCOLEDI', '19:30:00', '22:30:00'),
(23, 'GIOVEDI', '12:30:00', '14:30:00'), (23, 'GIOVEDI', '19:30:00', '22:30:00'),
(23, 'VENERDI', '12:30:00', '14:30:00'), (23, 'VENERDI', '19:30:00', '22:30:00'),
(23, 'SABATO', '12:30:00', '14:30:00'), (23, 'SABATO', '19:30:00', '22:30:00'),
-- La Pista - Ristorante Lingotto (24)
(24, 'MARTEDI', '12:30:00', '14:30:00'), (24, 'MARTEDI', '19:30:00', '22:30:00'),
(24, 'MERCOLEDI', '12:30:00', '14:30:00'), (24, 'MERCOLEDI', '19:30:00', '22:30:00'),
(24, 'GIOVEDI', '12:30:00', '14:30:00'), (24, 'GIOVEDI', '19:30:00', '22:30:00'),
(24, 'VENERDI', '12:30:00', '14:30:00'), (24, 'VENERDI', '19:30:00', '22:30:00'),
(24, 'SABATO', '12:30:00', '14:30:00'), (24, 'SABATO', '19:30:00', '22:30:00'),
(24, 'DOMENICA', '12:30:00', '14:30:00'), (24, 'DOMENICA', '19:30:00', '22:30:00'),
-- Antiche Sere (25) - solo cena, chiuso domenica
(25, 'LUNEDI', '19:30:00', '23:00:00'),
(25, 'MARTEDI', '19:30:00', '23:00:00'),
(25, 'MERCOLEDI', '19:30:00', '23:00:00'),
(25, 'GIOVEDI', '19:30:00', '23:00:00'),
(25, 'VENERDI', '19:30:00', '23:00:00'),
(25, 'SABATO', '19:30:00', '23:00:00'),
-- Caffè Mulassano (26) - chiuso domenica
(26, 'LUNEDI', '08:00:00', '20:00:00'),
(26, 'MARTEDI', '08:00:00', '20:00:00'),
(26, 'MERCOLEDI', '08:00:00', '20:00:00'),
(26, 'GIOVEDI', '08:00:00', '20:00:00'),
(26, 'VENERDI', '08:00:00', '20:00:00'),
(26, 'SABATO', '08:00:00', '20:00:00'),
-- Caffè Al Bicerin (27) - chiuso martedì
(27, 'LUNEDI', '09:00:00', '19:00:00'),
(27, 'MERCOLEDI', '09:00:00', '19:00:00'),
(27, 'GIOVEDI', '09:00:00', '19:00:00'),
(27, 'VENERDI', '09:00:00', '19:00:00'),
(27, 'SABATO', '09:00:00', '19:00:00'),
(27, 'DOMENICA', '09:00:00', '19:00:00'),
-- Caffè Torino (28)
(28, 'LUNEDI', '08:00:00', '23:00:00'),
(28, 'MARTEDI', '08:00:00', '23:00:00'),
(28, 'MERCOLEDI', '08:00:00', '23:00:00'),
(28, 'GIOVEDI', '08:00:00', '23:00:00'),
(28, 'VENERDI', '08:00:00', '23:00:00'),
(28, 'SABATO', '08:00:00', '23:00:00'),
(28, 'DOMENICA', '08:00:00', '23:00:00'),
-- Caffè San Carlo (29)
(29, 'LUNEDI', '07:30:00', '21:00:00'),
(29, 'MARTEDI', '07:30:00', '21:00:00'),
(29, 'MERCOLEDI', '07:30:00', '21:00:00'),
(29, 'GIOVEDI', '07:30:00', '21:00:00'),
(29, 'VENERDI', '07:30:00', '21:00:00'),
(29, 'SABATO', '07:30:00', '21:00:00'),
(29, 'DOMENICA', '07:30:00', '21:00:00'),
-- Baratti & Milano (30) - chiuso lunedì
(30, 'MARTEDI', '08:30:00', '21:00:00'),
(30, 'MERCOLEDI', '08:30:00', '21:00:00'),
(30, 'GIOVEDI', '08:30:00', '21:00:00'),
(30, 'VENERDI', '08:30:00', '21:00:00'),
(30, 'SABATO', '08:30:00', '21:00:00'),
(30, 'DOMENICA', '08:30:00', '21:00:00'),
-- Editoriale (31) - chiuso lunedì
(31, 'MARTEDI', '18:00:00', '02:00:00'),
(31, 'MERCOLEDI', '18:00:00', '02:00:00'),
(31, 'GIOVEDI', '18:00:00', '02:00:00'),
(31, 'VENERDI', '18:00:00', '02:00:00'),
(31, 'SABATO', '18:00:00', '02:00:00'),
(31, 'DOMENICA', '18:00:00', '02:00:00');

-- =================================================================
-- 7. EVENTI E DATE EVENTO
-- =================================================================
INSERT INTO eventi (nome, descrizione, tipologia, prezzo, prenotazione, pubblico, id_luogo_interesse) VALUES
('Notte al Museo: I Segreti dei Faraoni', 'Un''esperienza esclusiva alla scoperta delle sale del museo dopo l''orario di chiusura, con visita guidata a tema e approfondimenti sui rituali funerari dell''antico Egitto.', 'VISITA_GUIDATA', 20.00, TRUE, 'TUTTI', 1),
('Archeologia per Famiglie', 'Laboratorio didattico pensato per bambini e genitori, alla scoperta delle tecniche di scavo e restauro attraverso attività pratiche e giochi a tema egizio.', 'LABORATORIO', 8.00, TRUE, 'BAMBINI', 1),
('CinemaRitrovato: Rassegna dei Classici Restaurati', 'Un ciclo di proiezioni dedicato ai grandi classici del cinema italiano e internazionale, restaurati in alta definizione e presentati da critici ed esperti del settore.', 'PROIEZIONE', 6.50, FALSE, 'TUTTI', 2),
('Tesori Nascosti: Visita alle Collezioni di Ceramica', 'Un percorso guidato tra le sale meno conosciute del museo, alla scoperta delle raffinate collezioni di ceramiche e maioliche europee ed orientali.', 'VISITA_GUIDATA', 12.00, TRUE, 'ADULTI', 3),
('Contemporanea: Nuovi Linguaggi dell''Arte Italiana', 'Mostra temporanea che raccoglie opere di giovani artisti italiani emergenti, in dialogo con i grandi maestri della collezione permanente del museo.', 'MOSTRA', 10.00, FALSE, 'TUTTI', 5),
('Concept Cars: Il Futuro del Design Automobilistico', 'Esposizione speciale dedicata ai prototipi e alle concept car che hanno anticipato il futuro dell''automobile, tra design visionario e innovazione tecnologica.', 'MOSTRA', 5.00, FALSE, 'TUTTI', 6),
('Torino Jazz Festival - Concerti al Parco', 'Rassegna musicale gratuita con concerti jazz all''aperto nel cuore del Parco del Valentino, con artisti italiani e internazionali.', 'CONCERTO', 0.00, FALSE, 'TUTTI', 14),
('CioccolaTò - Festival del Cioccolato', 'Il festival dedicato al cioccolato artigianale torinese, con degustazioni, laboratori e masterclass per grandi e piccini nel cuore del parco.', 'FESTIVAL', 0.00, FALSE, 'TUTTI', 14),
('Visita Guidata ai Giardini Reali', 'Un percorso guidato tra i vialetti storici dei Giardini Reali, alla scoperta della loro storia, delle fontane e delle specie botaniche più rare.', 'VISITA_GUIDATA', 8.00, TRUE, 'TUTTI', 15),
('Laboratorio di Botanica per Bambini', 'Attività didattica dedicata ai più piccoli per scoprire il mondo delle piante attraverso giochi, osservazioni al microscopio e piccoli esperimenti.', 'EVENTO_BAMBINI', 6.00, TRUE, 'BAMBINI', 19),
('Incontro con l''Autore: Storie di Torino', 'Incontro con l''autore per la presentazione del nuovo libro dedicato alla storia e alle leggende della città di Torino.', 'PRESENTAZIONE_LIBRO', 0.00, FALSE, 'ADULTI', 10),
('Gran Galà della Cucina Sabauda', 'Cena degustazione a tema con menu storico rivisitato, abbinamento di vini piemontesi e racconto guidato della tradizione culinaria sabauda.', 'EVENTO_SPECIALE', 95.00, TRUE, 'ADULTI', 20),
('Aperitivo in Musica con Dj Set', 'Serata speciale con musica dal vivo, dj set e proposte gastronomiche ricercate nel cuore della movida di San Salvario.', 'EVENTO_SPECIALE', 15.00, FALSE, 'GIOVANI', 31),
('Conferenza: Alpinismo e Cambiamento Climatico', 'Incontro con alpinisti e ricercatori per discutere degli effetti del cambiamento climatico sui ghiacciai alpini e sul futuro della montagna.', 'CONFERENZA', 0.00, FALSE, 'ADULTI', 7);

INSERT INTO data_evento (evento_id, data_inizio, data_fine, ora_inizio, ora_fine) VALUES
(1, '2026-10-17', '2026-10-17', '20:30:00', '23:00:00'),
(2, '2026-09-13', '2026-09-13', '10:00:00', '12:30:00'),
(3, '2026-11-05', '2026-11-08', '20:00:00', '23:00:00'),
(4, '2026-09-27', '2026-09-27', '15:00:00', '16:30:00'),
(5, '2026-10-01', '2027-01-31', '10:00:00', '18:00:00'),
(6, '2026-09-15', '2026-12-20', '10:00:00', '19:00:00'),
(7, '2026-09-18', '2026-09-20', '19:00:00', '23:30:00'),
(8, '2026-11-20', '2026-11-29', '10:00:00', '22:00:00'),
(9, '2026-09-06', '2026-09-06', '11:00:00', '12:30:00'),
(10, '2026-09-20', '2026-09-20', '15:00:00', '17:00:00'),
(11, '2026-10-08', '2026-10-08', '18:00:00', '19:30:00'),
(12, '2026-12-12', '2026-12-12', '20:00:00', '23:30:00'),
(13, '2026-09-26', '2026-09-26', '19:00:00', '01:00:00'),
(14, '2026-10-22', '2026-10-22', '18:00:00', '20:00:00');

-- =================================================================
-- 8. RECENSIONI
-- =================================================================
INSERT INTO recensioni (voto, commento, id_utente, id_luogo_interesse) VALUES
(5, 'Collezione straordinaria, in particolare la sezione dedicata ai sarcofagi. Da vedere assolutamente.', 5, 1),
(5, 'Il nuovo allestimento è moderno e ben curato, vale ogni euro del biglietto.', 6, 1),
(4, 'Ottimo museo ma nei weekend è molto affollato, meglio prenotare in anticipo.', 7, 1),
(5, 'Ascensore panoramico mozzafiato, vista incredibile su Torino e le Alpi.', 5, 2),
(4, 'Bel museo del cinema, molto interattivo e adatto anche ai bambini.', 8, 2),
(5, 'Palazzo splendido, le sale barocche sono uno spettacolo.', 6, 3),
(4, 'Interessante ma un po piccolo rispetto ad altri musei della città.', 7, 3),
(5, 'La Galleria Sabauda è un gioiello poco conosciuto, consigliatissimo.', 5, 4),
(5, 'Opere contemporanee molto interessanti, personale gentile e disponibile.', 8, 5),
(4, 'Bella collezione di auto storiche, un tuffo nel passato dell''industria italiana.', 6, 6),
(5, 'Vista pazzesca sulla città dal giardino pensile della Pinacoteca.', 7, 8),
(5, 'Parco bellissimo per una passeggiata la domenica mattina, sempre pulito.', 5, 14),
(4, 'Giardini curatissimi, un angolo di pace nel centro città.', 6, 15),
(5, 'Cena eccezionale, il servizio è impeccabile e l''ambiente unico nel suo genere.', 8, 20),
(5, 'La pizza è ottima e il servizio veloce nonostante il locale sia sempre pieno.', 7, 21),
(4, 'Agnolotti al plin da manuale, un classico della tradizione piemontese.', 5, 23),
(5, 'Il bicerin originale è imbattibile, tappa obbligatoria a Torino.', 6, 27),
(4, 'Location storica bellissima, tramezzini ottimi ma prezzi un po alti.', 8, 26),
(3, 'Buona qualità ma prezzi elevati per la zona, tornerei comunque.', 6, 28),
(5, 'Atmosfera elegante e cioccolatini deliziosi, un''esperienza da non perdere.', 7, 30),
(4, 'Cocktail creativi e ottima musica, location perfetta per la serata.', 5, 31);

-- =================================================================
-- 9. PREFERITI (Luoghi ed Eventi)
-- =================================================================
INSERT INTO utente_luoghi_preferiti (id_utente, id_luogo_interesse) VALUES
(5, 1), (5, 2), (5, 14),
(6, 1), (6, 4), (6, 27),
(7, 3), (7, 8), (7, 21),
(8, 20), (8, 26), (8, 31);

INSERT INTO utente_eventi_preferiti (id_utente, id_evento) VALUES
(5, 1), (5, 7),
(6, 3), (6, 8),
(7, 5), (7, 12),
(8, 9), (8, 13);
