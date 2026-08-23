package com.quattromoschettieri.itineria.services.documentoService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    @Value("${file.storage.location}")
    private String storageLocation;

    private Path storagePath;

    @PostConstruct
    public void init() {
        storagePath = Paths.get(storageLocation);

        try {
            Files.createDirectories(storagePath);
        } catch (Exception e) {
            throw new RuntimeException("Impossibile creare la cartella di storage", e);
        }
    }
}

//TODO: Implementare metodi per salvare, leggere e cancellare file nella cartella di storage.