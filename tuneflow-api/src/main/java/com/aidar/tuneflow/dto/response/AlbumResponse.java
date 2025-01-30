package com.aidar.tuneflow.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AlbumResponse {
    private String id;
    private String image;
    private String titre;
    private String artiste;
    private Integer annee;
    private List<ChansonResponse> chansons;
}
