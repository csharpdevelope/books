package com.library.book.service;

import com.library.book.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileInfoService {

    void init();

    FileInfo uploadFile(MultipartFile file, String filename, String originalFilename);

    byte[] loadAsResource(String filename);
}
