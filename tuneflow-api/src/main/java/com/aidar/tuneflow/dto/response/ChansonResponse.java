package com.aidar.tuneflow.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ChansonResponse {
    private String id;
    private String image;
    private String titre;
    private Integer duree;
    private Integer trackNumber;
    private String description;
    private String categorie;
    private Date dateAjout;
}