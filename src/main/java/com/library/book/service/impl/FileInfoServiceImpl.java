package com.library.book.service.impl;

import com.library.book.exception.NotFoundException;
import com.library.book.exception.StorageException;
import com.library.book.model.FileInfo;
import com.library.book.repository.FileInfoRepository;
import com.library.book.service.FileInfoService;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileInfoRepository fileInfoRepository;
    private final String FILE_PATH = "src/main/resources/uploads/";
    private final Path rootLocation;

    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
        this.rootLocation = Paths.get(FILE_PATH);
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public FileInfo uploadFile(MultipartFile file, String filename, String originalFilename) {
        if (file.isEmpty()) {
            throw new StorageException("File empty");
        }
        FileInfo fileInfo = new FileInfo();
        try (InputStream inputStream = file.getInputStream()) {
            fileInfo.setFileSize(fileInfo.getFileSize());
            fileInfo.setFilename(filename);
            fileInfo.setOriginalFilename(originalFilename);
            Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            fileInfoRepository.save(fileInfo);
            logger.info("file info save");
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return fileInfo;
    }

    @Override
    public byte[] loadAsResource(String filename) {
        try {
            Path path = rootLocation.resolve(filename).normalize();
            return Files.readAllBytes(path);
        } catch (MalformedURLException e) {
            throw new NotFoundException("Could not read file: " + filename, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
