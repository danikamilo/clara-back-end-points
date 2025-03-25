package com.clara.back.endpoints.rest.service.model.bd;


import com.clara.back.endpoints.rest.service.model.discographies.Community;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Autor Daneil Camilo
 */
@Data
@Entity
@Table(name = "artist")
public class Artist {

    private String style;
    private String thumb;
    private String title;
    private String country;
    private String format;
    private String uri;
    //private Community community;
    private String label;
    private String catno;
    private String releaseYear;
    private String genre;
    private String resourceUrl;
    private String type;
    @Id
    @Column(name = "id")
    private Long id;

}
