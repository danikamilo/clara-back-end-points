package com.clara.back.endpoints.rest.service.model.bd;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Autor Daniel Camilo
 */

@Entity
@Table(name = "format")
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idArtist", nullable = false)
    public Discography discographytIndex;

    @Column(nullable = true, name = "name")
    public String name = StringUtils.EMPTY;
    @Column(nullable = true)
    public String qty = StringUtils.EMPTY;
    @Column(nullable = true)
    public String text = StringUtils.EMPTY;
    @Column(nullable = true, name = "descriptions", length = 4000)
    public String descriptions = StringUtils.EMPTY;

    public Format() {
    }

    public Format(String name, String qty, String text, String descriptions) {
        this.name = name;
        this.qty = qty;
        this.text = text;
        this.descriptions = descriptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Discography getDiscographytIndex() {
        return discographytIndex;
    }

    public void setDiscographytIndex(Discography discographytIndex) {
        this.discographytIndex = discographytIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
