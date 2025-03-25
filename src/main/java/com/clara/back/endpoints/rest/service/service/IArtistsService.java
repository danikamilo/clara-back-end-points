package com.clara.back.endpoints.rest.service.service;

import com.clara.back.endpoints.rest.service.model.bd.Artist;
import com.clara.back.endpoints.rest.service.model.discographies.DiscogsResponse;

import java.util.List;

public interface IArtistsService {

    /**
     * @Autor Daniel Camilo
     * @param artistName
     * @return DiscogsResponse
     */
    public List<Artist> searchArtist(String artistName);

    /**
     * @Auto Daniel Camilo
     * @return List<Artist>
     */
    public List<Artist> getDiscoGraphies();
}
