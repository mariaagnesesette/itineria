-- =================================================================
-- IMMAGINI PERSONALIZZATE PER GLI EVENTI
-- =================================================================

CREATE TABLE immagini_evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_evento BIGINT NOT NULL,
    file_key VARCHAR(255) NOT NULL,
    ordine INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (id_evento)
        REFERENCES eventi (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);
