package com.clara.back.endpoints.rest.service.model.discographies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DiscogsResponse {

    @JsonProperty("pagination")
    private Pagination pagination;
    @JsonProperty("results")
    private ArrayList<Result> results;
}
