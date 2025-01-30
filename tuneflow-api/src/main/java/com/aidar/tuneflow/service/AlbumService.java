package com.aidar.tuneflow.service;

import com.aidar.tuneflow.dto.request.AlbumRequest;
import com.aidar.tuneflow.dto.response.AlbumResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlbumService {
    Page<AlbumResponse> getAllAlbums(Pageable pageable);
    Page<AlbumResponse> searchAlbumsByTitle(String title, Pageable pageable);
    Page<AlbumResponse> searchAlbumsByArtist(String artist, Pageable pageable);
    Page<AlbumResponse> filterAlbumsByYear(Integer year, Pageable pageable);
    AlbumResponse addAlbum(AlbumRequest request);
    AlbumResponse updateAlbum(String id, AlbumRequest request);
    void deleteAlbum(String id);
}








