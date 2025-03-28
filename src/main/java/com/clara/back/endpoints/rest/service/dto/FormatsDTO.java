package com.clara.back.endpoints.rest.service.dto;

import jakarta.persistence.Column;
import lombok.Data;

/**
 * @Autor Daniel Camilo
 */
@Data
public class FormatsDTO {

    public String name;
    public String qty;
    public String text;
    @Column(name = "descriptions", length = 4000)
    public String descriptions;
}
