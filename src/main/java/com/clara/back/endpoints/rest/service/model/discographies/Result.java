package com.clara.back.endpoints.rest.service.model.discographies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Result {

    @JsonProperty("style")
    private ArrayList<String> style;
    @JsonProperty("thumb")
    private String thumb;
    @JsonProperty("title")
    private String title;
    @JsonProperty("country")
    private String country;
    @JsonProperty("format")
    private ArrayList<String> format;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("community")
    private Community community;
    @JsonProperty("label")
    private ArrayList<String> label;
    @JsonProperty("catno")
    private String catno;
    @JsonProperty("year")
    private String releaseYear;
    @JsonProperty("genre")
    private ArrayList<String> genre;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Long id;

}
