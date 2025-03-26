package com.clara.back.endpoints.rest.service.model.bd;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @Autor Daneil Camilo
 */
@Entity
@Table(name = "artist_index")
public class ArtistIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;
    public String name;

    @OneToMany(mappedBy = "artistIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Artist> idIndex;

    public ArtistIndex() {
    }

    public ArtistIndex(String artistName){
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

    public List<Artist> getIdIndex() {
        return idIndex;
    }

    public void setIdIndex(List<Artist> idIndex) {
        this.idIndex = idIndex;
    }
}
