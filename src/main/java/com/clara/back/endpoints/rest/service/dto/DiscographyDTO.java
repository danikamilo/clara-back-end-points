package com.clara.back.endpoints.rest.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @Autor Daniel Camilo
 */
@Data
public class DiscographyDTO {

    private List<String> style;
    private String thumb;
    private String title;
    private String country;
    private List<String> format;
    private String uri;
    private List<String> label;
    private List<String> barcode;
    private String catno;
    private Long releaseYear;
    private List<String> genre;
    private String resourceUrl;
    private String type;
    private String community;
    private List<FormatsDTO> formats;
}
