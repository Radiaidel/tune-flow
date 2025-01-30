package com.aidar.tuneflow.dto.request;

import lombok.Data;

@Data
public class AlbumRequest {
    private String image;
    private String titre;
    private String artiste;
    private Integer annee;
}