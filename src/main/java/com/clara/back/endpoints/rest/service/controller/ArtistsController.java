package com.clara.back.endpoints.rest.service.controller;


import com.clara.back.endpoints.rest.service.model.bd.Artist;
import com.clara.back.endpoints.rest.service.model.discographies.DiscogsResponse;
import com.clara.back.endpoints.rest.service.service.IArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auto Daniel Camilo
 */
@RestController
@RequestMapping("v1/artist/")
public class ArtistsController {

    @Autowired
    private IArtistsService artistServ;

    @GetMapping("search-artist/{artistName}")
    public List<Artist> searchArtist(@PathVariable String artistName) {
        return artistServ.searchArtist(artistName);
    }

    @GetMapping("get-discographies")
    public List<Artist> getDiscoGraphies() {
        return artistServ.getDiscoGraphies();
    }
}
