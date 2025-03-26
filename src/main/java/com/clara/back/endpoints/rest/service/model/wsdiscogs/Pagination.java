package com.clara.back.endpoints.rest.service.model.wsdiscogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pagination {

    @JsonProperty("per_page")
    private int perPage;
    @JsonProperty("pages")
    private int pages;
    @JsonProperty("page")
    private int page;
    @JsonProperty("items")
    private int items;
    @JsonProperty("urls")
    private Urls urls;

}
