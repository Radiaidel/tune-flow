package com.aidar.tuneflow.controller;



import com.aidar.tuneflow.dto.request.ChansonRequest;
import com.aidar.tuneflow.dto.response.ChansonResponse;
import com.aidar.tuneflow.exception.FileSizeLimitExceededException;
import com.aidar.tuneflow.service.ChansonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ChansonController {
    private final ChansonService chansonService;

    public ChansonController(ChansonService chansonService) {
        this.chansonService = chansonService;
    }

    @GetMapping("/user/songs")
    public ResponseEntity<Page<ChansonResponse>> getAllChansons(
            @PageableDefault(size = 20, sort = "titre") Pageable pageable) {
        return ResponseEntity.ok(chansonService.getAllChansons(pageable));
    }

    @GetMapping("/user/songs/search")
    public ResponseEntity<Page<ChansonResponse>> searchChansonsByTitle(
            @RequestParam String title,
            @PageableDefault(size = 20, sort = "titre") Pageable pageable) {
        return ResponseEntity.ok(chansonService.searchChansonsByTitle(title, pageable));
    }

    @GetMapping("/user/songs/album/{albumId}")
    public ResponseEntity<Page<ChansonResponse>> getChansonsByAlbum(
            @PathVariable String albumId,
            @PageableDefault(size = 20, sort = "trackNumber") Pageable pageable) {
        return ResponseEntity.ok(chansonService.getChansonsByAlbum(albumId, pageable));
    }

    @PostMapping(value = "/admin/songs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChansonResponse> addChanson(
            @RequestPart(value = "chanson") String chansonJson,
            @RequestPart(value = "file") MultipartFile file
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ChansonRequest request = mapper.readValue(chansonJson, ChansonRequest.class);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(chansonService.addChanson(request, file));
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Pour le debugging
            throw new RuntimeException("Format de requête invalide : " + e.getMessage());
        }
    }



    @PutMapping("/admin/songs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChansonResponse> updateChanson(
            @PathVariable String id,
            @Valid @RequestBody ChansonRequest request) {
        return ResponseEntity.ok(chansonService.updateChanson(id, request));
    }

    @DeleteMapping("/admin/songs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChanson(@PathVariable String id) {
        chansonService.deleteChanson(id);
        return ResponseEntity.noContent().build();
    }
}
