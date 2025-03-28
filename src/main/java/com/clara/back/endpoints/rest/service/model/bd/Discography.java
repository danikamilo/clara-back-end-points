package com.clara.back.endpoints.rest.service.model.bd;



import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Autor Daneil Camilo
 */

@Entity
@Table(name = "discography")
public class Discography {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idIndex", nullable = false)
    private Artist artistIndex;

    @OneToMany(mappedBy = "discographytIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Format> formatIndex;

    @Column(nullable = true, name = "style", length = 4000)
    private List<String> style = new ArrayList<>();
    @Column(nullable = true)
    private String thumb = StringUtils.EMPTY;
    @Column(nullable = true)
    private String title = StringUtils.EMPTY;
    @Column(nullable = true)
    private String country = StringUtils.EMPTY;
    @Column(nullable = true, name = "format", length = 4000)
    private List<String> format = new ArrayList<>();
    @Column(nullable = true)
    private String uri = StringUtils.EMPTY;
    @Column(nullable = true, name = "label", length = 4000)
    private List<String> label = new ArrayList<>();
    @Column(name = "barcode", length = 4000)
    private List<String> barcode = new ArrayList<>();
    @Column(nullable = true)
    private String catno = StringUtils.EMPTY;
    @Column(nullable = true)
    private Long releaseYear = 0L;
    @Column(name = "genre", length = 4000)
    private List<String> genre = new ArrayList<>();
    @Column(nullable = true)
    private String resourceUrl = StringUtils.EMPTY;
    @Column(nullable = true)
    private String type = StringUtils.EMPTY;
    @Column(nullable = true)
    private String community = StringUtils.EMPTY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artist getArtistIndex() {
        return artistIndex;
    }

    public void setArtistIndex(Artist artistIndex) {
        this.artistIndex = artistIndex;
    }

    public List<Format> getFormatIndex() {
        return formatIndex;
    }

    public void setFormatIndex(List<Format> formatIndex) {
        this.formatIndex = formatIndex;
    }

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(List<String> style) {
        this.style = style;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getFormat() {
        return format;
    }

    public void setFormat(List<String> format) {
        this.format = format;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<String> getBarcode() {
        return barcode;
    }

    public void setBarcode(List<String> barcode) {
        this.barcode = barcode;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno;
    }

    public Long getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }
}
