package com.aidar.tuneflow.dto.embedded;

import lombok.Data;

@Data
public class ChansonEmbedded {
    private String id;
    private String titre;
    private Integer duree;
    private Integer trackNumber;
}