package com.clara.back.endpoints.rest.service.service;

import com.clara.back.endpoints.rest.service.dto.AdvancedArtistComparisonDTO;
import com.clara.back.endpoints.rest.service.exceptions.InternalServiceException;
import com.clara.back.endpoints.rest.service.exceptions.NoArgumentsException;
import com.clara.back.endpoints.rest.service.model.bd.Artist;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Autor Daniel Camilo
 */
public interface IArtistsService {

    /**
     * @Autor Daniel Camilo
     * @param artistName
     * @return DiscogsResponse
     */
    public List<Artist> searchArtist(String artistName) throws InternalServiceException;

    /**
     * @Autor Daniel Camilo
     * This method gets the artist’s discography from the database, sorted by release
     * year
     * @return List<Artist>
     */
    public List<Artist> getDiscoGraphies() throws InternalServiceException;

    /**
     * @Autor Daniel Camilo
     * This method compare some atributes from artists and it show them in the response.
     * @param first
     * @param second
     * @param third
     * @return List<AdvancedArtistComparisonDTO>
     */
    public List<AdvancedArtistComparisonDTO> compareArtist(String first, String second, String third)  throws NoArgumentsException, InternalServiceException;
    /**
     * @Autor Daniel Camilo
     * This method compare some atributes from artists and it show them in the response.
     * @param first
     * @param second
     * @return List<AdvancedArtistComparisonDTO>
     */
    public List<AdvancedArtistComparisonDTO> compareArtist(String first, String second)  throws NoArgumentsException, InternalServiceException;

    /**
     * @Autor Daniel Camilo
     * This method compare some atributes from artists and it show them in the response.
     * @param input
     * Any ellemts go here
     * @return List<AdvancedArtistComparisonDTO>
     * @throws NoSuchElementException, NotFoundDataException
     */
    public List<AdvancedArtistComparisonDTO> compareArtist(String input)  throws NoArgumentsException, InternalServiceException;
}
