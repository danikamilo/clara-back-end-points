package com.clara.back.endpoints.rest.service.model.bd;

import jakarta.persistence.*;
import java.util.List;

/**
 * @Autor Daneil Camilo
 */
@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "artistIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Discography> idIndex;

    public Artist() {
    }

    public Artist(String artistName){
        this.name = artistName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Discography> getIdIndex() {
        return idIndex;
    }

    public void setIdIndex(List<Discography> idIndex) {
        this.idIndex = idIndex;
    }
}
