package com.aidar.tuneflow.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "albums")
public class Album {
    @Id
    private String id;
    private String image;
    private String titre;
    private String artiste;
    private Integer annee;

    @Field("chansons")
    private List<Chanson> chansons = new ArrayList<>();
}




