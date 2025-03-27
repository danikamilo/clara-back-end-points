package com.clara.back.endpoints.rest.service.model.wsdiscogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * Daniel Canmilo
 */
@Data
public class DiscogsResponse {

    @JsonProperty("pagination")
    private Pagination pagination;
    @JsonProperty("results")
    private List<Result> results;

}
