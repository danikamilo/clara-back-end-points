package com.clara.back.endpoints.rest.service.controller;


import com.clara.back.endpoints.rest.service.dto.AdvancedArtistComparisonDTO;
import com.clara.back.endpoints.rest.service.model.bd.Artist;
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

    @GetMapping("compare-artist/first/{first}/second/{second}/third/{third}/")
    public List<AdvancedArtistComparisonDTO> compareArtist(@PathVariable String first, @PathVariable String second, @PathVariable String third) {
        return artistServ.compareArtist(first, second, third);
    }

    @GetMapping("compare-artist/first/{first}/second/{second}/")
    public List<AdvancedArtistComparisonDTO> compareArtist(@PathVariable String first, @PathVariable String second) {
        return artistServ.compareArtist(first, second);
    }

    @GetMapping("/compare-artist")
    public List<AdvancedArtistComparisonDTO> buy(@RequestParam("artists") String input){
        return artistServ.compareArtist(input);
    }


}
