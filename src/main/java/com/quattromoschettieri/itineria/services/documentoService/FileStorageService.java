package com.quattromoschettieri.itineria.services.documentoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public String save(MultipartFile file) {

        try {
            Path documentiPath = storagePath.resolve("documenti");

            Files.createDirectories(documentiPath);

            String originalName = file.getOriginalFilename();

            String extension = "";

            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID() + extension;

            Path filePath = documentiPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            return "documenti/" + fileName;

        } catch (IOException e) {
                throw new RuntimeException("Impossibile salvare il file", e);
        }
    }

    public Resource read(String fileKey) {
        try {
            Path filePath = storagePath.resolve(fileKey).normalize();

            if (!filePath.startsWith(storagePath.normalize())) {
                throw new RuntimeException("Percorso file non valido");
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("File non trovato o non leggibile");
            }

            return resource;

        } catch (IOException e) {
            throw new RuntimeException("Impossibile leggere il file", e);
        }
    }

    public boolean exists(String fileKey) {
        Path filePath = storagePath.resolve(fileKey).normalize();

        if (!filePath.startsWith(storagePath.normalize())) {
            return false;
        }

        return Files.exists(filePath);
    }

    public void delete(String fileKey) {
        try {
            Path filePath = storagePath.resolve(fileKey).normalize();

            if (!filePath.startsWith(storagePath.normalize())) {
                throw new RuntimeException("Percorso file non valido");
            }

            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Impossibile eliminare il file", e);
        }
    }
}
