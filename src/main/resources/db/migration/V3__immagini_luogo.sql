-- =================================================================
-- IMMAGINI PERSONALIZZATE PER I LUOGHI DI INTERESSE
-- =================================================================

CREATE TABLE immagini_luogo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_luogo_interesse BIGINT NOT NULL,
    file_key VARCHAR(255) NOT NULL,
    ordine INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (id_luogo_interesse)
        REFERENCES luoghi_interesse (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);
