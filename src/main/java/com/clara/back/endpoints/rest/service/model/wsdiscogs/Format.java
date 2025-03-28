package com.clara.back.endpoints.rest.service.model.wsdiscogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * @Autor Daniel Camilo
 */
@Data
public class Format {

    @JsonProperty("name")
    public String name;
    @JsonProperty("qty")
    public String qty;
    @JsonProperty("text")
    public String text;
    @JsonProperty("descriptions")
    public List<String> descriptions;

    // Constructor
    public Format(String name, String qty, String text, List<String> descriptions) {
        this.name = name;
        this.qty = qty;
        this.text = text;
        this.descriptions = descriptions;
    }
}
