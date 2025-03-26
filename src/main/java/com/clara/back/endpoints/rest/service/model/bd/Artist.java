package com.clara.back.endpoints.rest.service.model.bd;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Autor Daneil Camilo
 */
@Data
@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idIndex", nullable = false)
    private ArtistIndex artistIndex;
    @Column(name = "style", length = 1000)
    private List<String> style;
    private String thumb;
    private String title;
    private String country;
    @Column(name = "format", length = 1000)
    private List<String> format;
    private String uri;
    @Column(name = "label", length = 1000)
    private List<String> label;
    @Column(name = "barcode", length = 1000)
    private List<String> barcode;
    private String catno;
    private Long releaseYear;
    @Column(name = "genre", length = 1000)
    private List<String> genre;
    private String resourceUrl;
    private String type;

    //public List<Format> formats;
    //private Community community;
}
