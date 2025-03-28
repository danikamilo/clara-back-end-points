package com.clara.back.endpoints.rest.service.model.wsdiscogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * @Autor Daniel Camilo
 */
@Data
public class Result {

    @JsonProperty("style")
    private List<String> style;
    @JsonProperty("thumb")
    private String thumb;
    @JsonProperty("title")
    private String title;
    @JsonProperty("country")
    private String country;
    @JsonProperty("format")
    private List<String> format;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("community")
    private Community community;
    @JsonProperty("label")
    public List<String> label;
    @JsonProperty("barcode")
    public List<String> barcode;
    @JsonProperty("genre")
    public List<String> genre;
    @JsonProperty("catno")
    private String catno;
    @JsonProperty("year")
    private Long releaseYear;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("formats")
    public List<Format> formats;

}
