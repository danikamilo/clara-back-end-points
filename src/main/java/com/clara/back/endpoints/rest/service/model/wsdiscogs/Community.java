package com.clara.back.endpoints.rest.service.model.wsdiscogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Community {

    @JsonProperty("want")
    private int want;
    @JsonProperty("have")
    private int have;
    // Getters y setters
}
