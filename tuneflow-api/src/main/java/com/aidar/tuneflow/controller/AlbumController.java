package com.aidar.tuneflow.controller;

import com.aidar.tuneflow.dto.request.AlbumRequest;
import com.aidar.tuneflow.dto.response.AlbumResponse;
import com.aidar.tuneflow.service.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/user/albums")
    public ResponseEntity<Page<AlbumResponse>> getAllAlbums(
            @PageableDefault(size = 20, sort = "titre") Pageable pageable) {
        return ResponseEntity.ok(albumService.getAllAlbums(pageable));
    }

    @GetMapping("/user/albums/search")
    public ResponseEntity<Page<AlbumResponse>> searchAlbumsByTitle(
            @RequestParam String title,
            @PageableDefault(size = 20, sort = "titre") Pageable pageable) {
        return ResponseEntity.ok(albumService.searchAlbumsByTitle(title, pageable));
    }

    @GetMapping("/user/albums/artist/{artist}")
    public ResponseEntity<Page<AlbumResponse>> searchAlbumsByArtist(
            @PathVariable String artist,
            @PageableDefault(size = 20, sort = "titre") Pageable pageable) {
        return ResponseEntity.ok(albumService.searchAlbumsByArtist(artist, pageable));
    }

    @GetMapping("/user/albums/year/{year}")
    public ResponseEntity<Page<AlbumResponse>> filterAlbumsByYear(
            @PathVariable Integer year,
            @PageableDefault(size = 20, sort = "titre") Pageable pageable) {
        return ResponseEntity.ok(albumService.filterAlbumsByYear(year, pageable));
    }

    @PostMapping("/admin/albums")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlbumResponse> addAlbum(@Valid @RequestBody AlbumRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.addAlbum(request));
    }

    @PutMapping("/admin/albums/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlbumResponse> updateAlbum(
            @PathVariable String id,
            @Valid @RequestBody AlbumRequest request) {
        return ResponseEntity.ok(albumService.updateAlbum(id, request));
    }

    @DeleteMapping("/admin/albums/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAlbum(@PathVariable String id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
