package com.aidar.tuneflow.service.impl;

import com.aidar.tuneflow.dto.request.AlbumRequest;
import com.aidar.tuneflow.dto.response.AlbumResponse;
import com.aidar.tuneflow.exception.ResourceNotFoundException;
import com.aidar.tuneflow.mapper.AlbumMapper;
import com.aidar.tuneflow.model.Album;
import com.aidar.tuneflow.repository.AlbumRepository;
import com.aidar.tuneflow.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

@Service

public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    @Override
    public Page<AlbumResponse> getAllAlbums(Pageable pageable) {
        return albumRepository.findAll(pageable).map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> searchAlbumsByTitle(String title, Pageable pageable) {
        return albumRepository.findByTitreContaining(title, pageable).map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> searchAlbumsByArtist(String artist, Pageable pageable) {
        return albumRepository.findByArtiste(artist, pageable).map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> filterAlbumsByYear(Integer year, Pageable pageable) {
        return albumRepository.findByAnnee(year, pageable).map(albumMapper::toResponse);
    }

    @Override
    @Transactional
    public AlbumResponse addAlbum(AlbumRequest request) {
        Album album = albumMapper.toEntity(request);
        return albumMapper.toResponse(albumRepository.save(album));
    }

    @Override
    @Transactional
    public AlbumResponse updateAlbum(String id, AlbumRequest request) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        albumMapper.updateEntityFromRequest(request, album);
        return albumMapper.toResponse(albumRepository.save(album));
    }

    @Override
    @Transactional
    public void deleteAlbum(String id) {
        albumRepository.deleteById(id);
    }
}
