package com.aidar.tuneflow.service.impl;

import com.aidar.tuneflow.exception.FileSizeLimitExceededException;
import com.aidar.tuneflow.exception.ResourceNotFoundException;
import com.aidar.tuneflow.exception.UnsupportedFileTypeException;
import com.aidar.tuneflow.service.AudioFileService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AudioFileServiceImpl implements AudioFileService {
    private final GridFsTemplate gridFsTemplate;

    @Override
    public String storeFile(MultipartFile file) {
        try {
            // Vérification de la taille
            if (file.getSize() > 15 * 1024 * 1024) {
                throw new FileSizeLimitExceededException("File size exceeds 15MB limit");
            }

            // Vérification plus robuste du type de fichier
            String contentType = file.getContentType();
            if (contentType == null ||
                    !(contentType.equals("audio/mpeg") ||
                            contentType.equals("audio/wav") ||
                            contentType.equals("audio/ogg") ||
                            contentType.equals("application/octet-stream"))) {
                throw new UnsupportedFileTypeException("Unsupported file type. Only MP3, WAV, and OGG are allowed.");
            }

            // Vérification de l'extension du fichier
            String filename = file.getOriginalFilename();
            if (filename != null) {
                String extension = filename.toLowerCase();
                if (!(extension.endsWith(".mp3") ||
                        extension.endsWith(".wav") ||
                        extension.endsWith(".ogg"))) {
                    throw new UnsupportedFileTypeException("Invalid file extension. Only .mp3, .wav, and .ogg are allowed.");
                }
            }

            ObjectId fileId = gridFsTemplate.store(
                    file.getInputStream(),
                    filename,
                    contentType
            );

            return fileId.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public InputStream getFile(String fileId) {
        try {
            GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
            if (file == null) {
                throw new ResourceNotFoundException("File not found with ID: " + fileId);
            }
            return gridFsTemplate.getResource(file).getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error while retrieving the file.", e);
        }
    }

    @Override
    public void deleteFile(String fileId) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(fileId)));
    }
}
