package com.clara.back.endpoints.rest.service.dto;


import lombok.Data;

import java.util.List;

/**
 * @Autor Daniel Camilo
 */
@Data
public class ArtistsDTO {

    private String name;
    private List<DiscographyDTO> discography;
}
