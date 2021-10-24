package com.simbirsoftintensiv.intensiv.service;

import com.simbirsoftintensiv.intensiv.controller.user.LoginController;
import com.simbirsoftintensiv.intensiv.repository.FileStorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service("fileStorageService")
public class FileStorageService implements FileStorageRepository {
    static final Logger log =
            LoggerFactory.getLogger(FileStorageService.class);
    private final Path path =  Paths.get(System.getProperty("user.dir") + "/fileStorage");

    @Override
    public void init() {
        try {
            Files.createDirectory(path);
            log.info("Инициализация каталога " + path);
        } catch (IOException e) {
            log.error("Невозможно инициализировать каталог "+ path);
            throw new RuntimeException("Cold not initialize folder for upload");
        }
    }

    @Override
    public void save(MultipartFile multipartFile) {
        if (!Files.exists(this.path)) {
             this.init();
        }
        try {
            Files.copy(multipartFile.getInputStream(), this.path.resolve(multipartFile.getOriginalFilename()));
            log.info("Сохраняем файл" + multipartFile.getOriginalFilename());
        } catch (IOException e) {
            log.error("Невозможно сохранить файл" + multipartFile.getOriginalFilename());
            throw new RuntimeException("Could not store this file. Error" + e.getMessage());
        }
    }

    @Override
    public Resource load(String fileName) {
        Path file = path.resolve(fileName);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                log.info("Читаем файл из хранилища");
                return resource;
            } else {
                throw new RuntimeException("Could not read this file");
            }
        } catch (MalformedURLException e) {
            log.error("Ошибка чтения файла");
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    @Override
    public Stream<Path> load() {
        try {
            return Files.walk(this.path,1)
                    .filter(path -> !path.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            log.error("Невозможно загрузить файл");
            throw new RuntimeException("Could not load the files");
        }
    }

    @Override
    public void clear() {
        FileSystemUtils.deleteRecursively(path.toFile());
    }
}
