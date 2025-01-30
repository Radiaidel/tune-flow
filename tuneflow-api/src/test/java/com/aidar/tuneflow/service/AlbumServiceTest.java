package com.aidar.tuneflow.service;


import com.aidar.tuneflow.dto.request.AlbumRequest;
import com.aidar.tuneflow.dto.response.AlbumResponse;
import com.aidar.tuneflow.mapper.AlbumMapper;
import com.aidar.tuneflow.model.Album;
import com.aidar.tuneflow.repository.AlbumRepository;
import com.aidar.tuneflow.service.impl.AlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private AlbumMapper albumMapper;

    @InjectMocks
    private AlbumServiceImpl albumService;

    private Album album;
    private AlbumRequest albumRequest;
    private AlbumResponse albumResponse;

    @BeforeEach
    void setUp() {
        album = new Album();
        album.setId("1");
        album.setTitre("Test Album");
        album.setArtiste("Test Artist");
        album.setAnnee(2023);

        albumRequest = new AlbumRequest();
        albumRequest.setTitre("Test Album");
        albumRequest.setArtiste("Test Artist");
        albumRequest.setAnnee(2023);

        albumResponse = new AlbumResponse();
        albumResponse.setId("1");
        albumResponse.setTitre("Test Album");
        albumResponse.setArtiste("Test Artist");
        albumResponse.setAnnee(2023);
    }

    @Test
    void getAllAlbums() {
        Page<Album> albumPage = new PageImpl<>(List.of(album));
        when(albumRepository.findAll(any(Pageable.class))).thenReturn(albumPage);
        when(albumMapper.toResponse(any(Album.class))).thenReturn(albumResponse);

        Page<AlbumResponse> result = albumService.getAllAlbums(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(albumResponse, result.getContent().get(0));
    }

    @Test
    void addAlbum() {
        when(albumMapper.toEntity(any(AlbumRequest.class))).thenReturn(album);
        when(albumRepository.save(any(Album.class))).thenReturn(album);
        when(albumMapper.toResponse(any(Album.class))).thenReturn(albumResponse);

        AlbumResponse result = albumService.addAlbum(albumRequest);

        assertNotNull(result);
        assertEquals(albumResponse, result);
        verify(albumRepository, times(1)).save(any(Album.class));
    }

    @Test
    void updateAlbum() {
        when(albumRepository.findById(anyString())).thenReturn(Optional.of(album));
        when(albumRepository.save(any(Album.class))).thenReturn(album);
        when(albumMapper.toResponse(any(Album.class))).thenReturn(albumResponse);

        AlbumResponse result = albumService.updateAlbum("1", albumRequest);

        assertNotNull(result);
        assertEquals(albumResponse, result);
        verify(albumRepository, times(1)).save(any(Album.class));
    }

    @Test
    void deleteAlbum() {
        albumService.deleteAlbum("1");
        verify(albumRepository, times(1)).deleteById("1");
    }
}