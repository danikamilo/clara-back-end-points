package com.clara.back.endpoints.rest.service.model.discographies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Urls {

    @JsonProperty("last")
    private String last;
    @JsonProperty("next")
    private String next;
    // Getters y setters
}
