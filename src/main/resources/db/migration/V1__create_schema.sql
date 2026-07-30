
CREATE TABLE utenti (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    data_nascita DATE NOT NULL,
    nickname VARCHAR(30),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    ruolo ENUM('USER', 'MANAGER', 'ADMIN') DEFAULT 'user',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE documenti (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('CARTA_IDENTITA', 'PATENTE', 'PASSAPORTO') NOT NULL,
    file_key VARCHAR(255) NOT NULL,    
    codice_identificativo VARCHAR(20) NOT NULL,
    stato ENUM('IN_ATTESA', 'APPROVATO', 'RIFIUTATO', 'SCADUTO') NOT NULL DEFAULT 'IN_ATTESA',    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    id_utente BIGINT NOT NULL,
    FOREIGN KEY (id_utente)
        REFERENCES utenti (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE citta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    regione ENUM('ABRUZZO', 'BASILICATA', 'CALABRIA', 'CAMPANIA', 'EMILIA_ROMAGNA', 'FRIULI_VENEZIA_GIULIA', 'LAZIO', 'LIGURIA', 'LOMBARDIA', 'MARCHE', 'MOLISE', 'PIEMONTE', 'PUGLIA', 'SARDEGNA', 'SICILIA', 'TOSCANA', 'TRENTINO_ALTO_ADIGE', 'UMBRIA', 'VALLE_D_AOSTA', 'VENETO') NOT NULL,
    descrizione VARCHAR(5000) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE luoghi_interesse (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(500) NOT NULL,
    descrizione VARCHAR(5000) NOT NULL,
    tipo ENUM('MUSEO', 'BIBLIOTECA', 'RISTORANTE', 'LOCALE', 'ZONA_VERDE') NOT NULL,
    accessibilita ENUM('COMPLETA', 'PARZIALE', 'LIMITATA', 'NON_ACCESSIBILE', 'NON_SPECIFICATA') DEFAULT 'NON_SPECIFICATA',
    indirizzo VARCHAR(200) NOT NULL,
    sempre_aperto BOOLEAN DEFAULT FALSE,
    link_sito VARCHAR(255),
    numero_telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    id_citta BIGINT NOT NULL,
    FOREIGN KEY (id_citta)
        REFERENCES citta (id)
        ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE orario_apertura (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_luogo_interesse BIGINT NOT NULL,
    giorno ENUM('LUNEDI', 'MARTEDI', 'MERCOLEDI', 'GIOVEDI', 'VENERDI', 'SABATO', 'DOMENICA'),
    apertura TIME,
    chiusura TIME,
    FOREIGN KEY (id_luogo_interesse)
        REFERENCES luoghi_interesse (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE zone_verdi (
    id BIGINT PRIMARY KEY,
    area_mq DECIMAL(8 , 2 ) NOT NULL,
    tipologia ENUM('parco_urbano', 'parco_giochi', 'parco_naturale', 'giardino_pubblico', 'area_cani', 'orto_botanico', 'parco_storico', 'riserva_naturale') NOT NULL,
    dog_friendly BOOLEAN DEFAULT FALSE,
    ristoro BOOLEAN DEFAULT FALSE,
    ciclabile BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id)
        REFERENCES luoghi_interesse (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE musei (
    id BIGINT PRIMARY KEY,
    tipologia ENUM('arte', 'storia', 'scienza', 'archeologia', 'storia_naturale', 'etnografico', 'militare', 'scienze_naturali', 'musica', 'religioso_sacro', 'casa_museo', 'trasporti', 'multidisciplinare') NOT NULL,
    guide_prenotabili BOOLEAN DEFAULT FALSE,
    bar_interno BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id)
        REFERENCES luoghi_interesse (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE fasce_prezzo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE prezzi_musei (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_museo BIGINT NOT NULL,
    id_fascia_prezzo BIGINT NOT NULL,
    prezzo DECIMAL(6 , 2 ) NOT NULL,
    FOREIGN KEY (id_museo)
        REFERENCES musei (id)
        ON DELETE CASCADE,
    FOREIGN KEY (id_fascia_prezzo)
        REFERENCES fasce_prezzo (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    UNIQUE KEY uq_museo_fascia (id_museo , id_fascia_prezzo)
);

CREATE TABLE biblioteca (
    id BIGINT PRIMARY KEY,
    pubblico BOOLEAN DEFAULT FALSE,
    wifi BOOLEAN DEFAULT FALSE,
    area_computer BOOLEAN DEFAULT FALSE,
    area_bambini BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id)
        REFERENCES luoghi_interesse (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE ristorante (
    id BIGINT PRIMARY KEY,
    tipo_cucina ENUM('ITALIANA', 'PIZZERIA', 'GIAPPONESE', 'CINESE', 'VEGETARIANA', 'FAST_FOOD', 'ALTRO'),
    fascia_prezzo ENUM('ECONOMICO', 'MEDIO', 'COSTOSO', 'LUSSO'),
    dog_friendly BOOLEAN DEFAULT FALSE,
    per_celiaci BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id)
        REFERENCES luoghi_interesse (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE locale (
    id BIGINT PRIMARY KEY,
    tipo_locale ENUM('BAR', 'PUB', 'CAFFE', 'COCKTAIL_BAR', 'ENOTECA', 'ALTRO'),
    stile VARCHAR(100),
    atmosfera ENUM('TRANQUILLA', 'INFORMALE', 'ELEGANTE', 'FESTOSA'),
    fascia_prezzo ENUM('ECONOMICO', 'MEDIO', 'ALTO'),
    apertura_serale BOOLEAN DEFAULT FALSE,
    musica BOOLEAN DEFAULT FALSE,
    posti_esterni BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id)
        REFERENCES luoghi_interesse (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE eventi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descrizione VARCHAR(5000) NOT NULL,
    tipologia ENUM('mostra', 'concerto', 'spettacolo_teatrale', 'conferenza', 'laboratorio', 'visita_guidata', 'proiezione', 'festival', 'presentazione_libro', 'workshop', 'evento_bambini', 'evento_speciale') NOT NULL,
    prezzo DECIMAL(10 , 2 ) NOT NULL CHECK (prezzo >= 0),
    prenotabile BOOLEAN DEFAULT TRUE,
    fascia_eta ENUM('tutte_le_eta', '0_5', '6_12', '13_17', '18_25', 'adulti', 'over_65') NOT NULL,
    id_luogo_interesse BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (id_luogo_interesse)
        REFERENCES luoghi_interesse (id)
        ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE data_evento (
    id_data_evento BIGINT AUTO_INCREMENT PRIMARY KEY,
    evento_id BIGINT NOT NULL,
    data_inizio DATE NOT NULL,
    data_fine DATE,
    ora_inizio TIME NOT NULL,
    ora_fine TIME,
    FOREIGN KEY (evento_id)
        REFERENCES eventi (id)
        ON DELETE CASCADE ON UPDATE RESTRICT
); 

CREATE TABLE utente_luoghi_preferiti (
    id_luogo_interesse BIGINT NOT NULL,
    id_utente BIGINT NOT NULL,
    PRIMARY KEY (id_utente , id_luogo_interesse),
    FOREIGN KEY (id_luogo_interesse)
        REFERENCES luoghi_interesse (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    FOREIGN KEY (id_utente)
        REFERENCES utenti (id)
        ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE utente_eventi_preferiti (
    id_evento BIGINT NOT NULL,
    id_utente BIGINT NOT NULL,
    PRIMARY KEY (id_utente , id_evento),
    FOREIGN KEY (id_evento)
        REFERENCES eventi (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    FOREIGN KEY (id_utente)
        REFERENCES utenti (id)
        ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE recensioni (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voto TINYINT NOT NULL CHECK (voto BETWEEN 1 AND 5),
    commento VARCHAR(5000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    id_utente BIGINT,
    id_luogo_interesse BIGINT NOT NULL,
    FOREIGN KEY (id_utente)
        REFERENCES utenti (id)
        ON DELETE SET NULL ON UPDATE RESTRICT,
    FOREIGN KEY (id_luogo_interesse)
        REFERENCES luoghi_interesse (id)
        ON DELETE CASCADE ON UPDATE RESTRICT
);


