package com.aidar.tuneflow.dto.request;

import lombok.Data;

@Data
public class ChansonRequest {
    private String image;
    private String titre;
    private Integer duree;
    private Integer trackNumber;
    private String description;
    private String categorie;
    private String albumId;
}
