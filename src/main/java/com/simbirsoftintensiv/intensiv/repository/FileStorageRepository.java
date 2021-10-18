package com.simbirsoftintensiv.intensiv.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageRepository {

    void init();

    void save(MultipartFile multipartFile);

    Resource load(String fileName);

    Stream<Path> load();

    void clear();
}
