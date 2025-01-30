package com.aidar.tuneflow.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface AudioFileService {
    String storeFile(MultipartFile file);
    InputStream getFile(String fileId);
    void deleteFile(String fileId);
}