package com.clara.back.endpoints.rest.service.dto;


import lombok.Data;

import java.util.List;

/**
 * @Autor Daniel Camilo
 *
 */
@Data
public class AdvancedArtistComparisonDTO {

    Long id;
    Long oldestYearRelease;
    Long lastYearRelease;
    Integer releaseNumber;
    Long yearsOfActivity;
    String artistName;
    Integer genreNumbers;
    List<String> genres;
    Integer styleNumbers;
    List<String> styles;

}
