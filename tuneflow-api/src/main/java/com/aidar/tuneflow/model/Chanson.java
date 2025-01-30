package com.aidar.tuneflow.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "chansons")
public class Chanson {
    @Id
    private String id;
    private String image;
    private String titre;
    private Integer duree;
    private Integer trackNumber;
    private String description;
    private String categorie;
    private Date dateAjout;
    private String audioFileId;
    private List<Album> album;
}