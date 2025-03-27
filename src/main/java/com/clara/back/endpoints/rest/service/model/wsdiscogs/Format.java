package com.clara.back.endpoints.rest.service.model.wsdiscogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;


@Data
public class Format {

    @JsonProperty("genre")
    public String name;
    @JsonProperty("genre")
    public String qty;
    @JsonProperty("genre")
    public String text;
    @JsonProperty("genre")
    public List<String> descriptions;
}
