package com.aidar.tuneflow.controller;


import com.aidar.tuneflow.dto.request.AlbumRequest;
import com.aidar.tuneflow.dto.response.AlbumResponse;
import com.aidar.tuneflow.service.AlbumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlbumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlbumService albumService;

    @Test
    @WithMockUser(roles = "USER")
    void getAllAlbums() throws Exception {
        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setId("1");
        albumResponse.setTitre("Test Album");
        albumResponse.setArtiste("Test Artist");
        albumResponse.setAnnee(2023);

        Page<AlbumResponse> albumPage = new PageImpl<>(List.of(albumResponse));
        when(albumService.getAllAlbums(any(Pageable.class))).thenReturn(albumPage);

        mockMvc.perform(get("/api/user/albums"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].titre").value("Test Album"))
                .andExpect(jsonPath("$.content[0].artiste").value("Test Artist"))
                .andExpect(jsonPath("$.content[0].annee").value(2023));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addAlbum() throws Exception {
        AlbumRequest albumRequest = new AlbumRequest();
        albumRequest.setTitre("New Album");
        albumRequest.setArtiste("New Artist");
        albumRequest.setAnnee(2023);

        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setId("1");
        albumResponse.setTitre("New Album");
        albumResponse.setArtiste("New Artist");
        albumResponse.setAnnee(2023);

        when(albumService.addAlbum(any(AlbumRequest.class))).thenReturn(albumResponse);

        mockMvc.perform(post("/api/admin/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(albumRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.titre").value("New Album"))
                .andExpect(jsonPath("$.artiste").value("New Artist"))
                .andExpect(jsonPath("$.annee").value(2023));
    }
}