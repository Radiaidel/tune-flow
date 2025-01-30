package com.aidar.tuneflow.service.impl;

import com.aidar.tuneflow.dto.request.ChansonRequest;
import com.aidar.tuneflow.dto.response.ChansonResponse;
import com.aidar.tuneflow.exception.ResourceNotFoundException;
import com.aidar.tuneflow.mapper.ChansonMapper;
import com.aidar.tuneflow.model.Album;
import com.aidar.tuneflow.model.Chanson;
import com.aidar.tuneflow.repository.AlbumRepository;
import com.aidar.tuneflow.repository.ChansonRepository;
import com.aidar.tuneflow.service.AudioFileService;
import com.aidar.tuneflow.service.ChansonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.util.Date;

@Service
public class ChansonServiceImpl implements ChansonService {
    private final ChansonRepository chansonRepository;
    private final AlbumRepository albumRepository;
    private final ChansonMapper chansonMapper;
    private final AudioFileService audioFileService;

    public ChansonServiceImpl(ChansonRepository chansonRepository, AlbumRepository albumRepository, ChansonMapper chansonMapper, AudioFileService audioFileService) {
        this.chansonRepository = chansonRepository;
        this.albumRepository = albumRepository;
        this.chansonMapper = chansonMapper;
        this.audioFileService = audioFileService;
    }

    @Override
    public Page<ChansonResponse> getAllChansons(Pageable pageable) {
        return chansonRepository.findAll(pageable).map(chansonMapper::toResponse);
    }

    @Override
    public Page<ChansonResponse> searchChansonsByTitle(String title, Pageable pageable) {
        return chansonRepository.findByTitreContaining(title, pageable).map(chansonMapper::toResponse);
    }

    @Override
    public Page<ChansonResponse> getChansonsByAlbum(String albumId, Pageable pageable) {
        return chansonRepository.findByAlbumId(albumId, pageable).map(chansonMapper::toResponse);
    }

    @Override
    @Transactional
    public ChansonResponse addChanson(ChansonRequest request, MultipartFile audioFile) {
        // Store audio file and get its ID
        String audioFileId = audioFileService.storeFile(audioFile);

        // Convert request to entity
        Chanson chanson = chansonMapper.toEntity(request);
        chanson.setAudioFileId(audioFileId);
        chanson.setDateAjout(new Date());

        // Find and add chanson to the album
        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        album.getChansons().add(chanson);
        albumRepository.save(album);

        // Save and return the chanson response
        return chansonMapper.toResponse(chansonRepository.save(chanson));
    }


    @Override
    @Transactional
    public ChansonResponse updateChanson(String id, ChansonRequest request) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chanson not found"));
        chansonMapper.updateEntityFromRequest(request, chanson);
        return chansonMapper.toResponse(chansonRepository.save(chanson));
    }

    @Override
    @Transactional
    public void deleteChanson(String id) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chanson not found"));
        audioFileService.deleteFile(chanson.getAudioFileId());
        chansonRepository.deleteById(id);
    }
}
