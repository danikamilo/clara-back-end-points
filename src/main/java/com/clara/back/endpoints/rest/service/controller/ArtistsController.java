package com.clara.back.endpoints.rest.service.controller;

import com.clara.back.endpoints.rest.service.dto.AdvancedArtistComparisonDTO;
import com.clara.back.endpoints.rest.service.dto.ArtistsDTO;
import com.clara.back.endpoints.rest.service.service.IDiscographyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Auto Daniel Camilo
 */
@RestController
@RequestMapping("v1/artist/")
@Tag(name = "Artists", description = "Endpoints get info about artist and discographies.")
public class ArtistsController {

    @Autowired
    private IDiscographyService discographyServ;

    @Operation(summary = "find an artist by name.")
    @GetMapping("search-artist/{artistName}/")
    public ArtistsDTO searchArtist(@PathVariable String artistName) {
        return discographyServ.searchArtist(artistName);
    }

    @Operation(summary = "find all discographies.")
    @GetMapping("search-discographies/")
    public ArtistsDTO searchALlDiscoGraphies() {
        return discographyServ.searchAllDiscoGraphies();
    }

    @Operation(summary = "find all discographies.")
    @GetMapping("search-all-artist/")
    public List<ArtistsDTO> searchALlArtist() {
        return discographyServ.searchALlArtist();
    }

    @Operation(summary = "get information of three artists.")
    @GetMapping("compare-artist/first/{first}/second/{second}/third/{third}/")
    public List<AdvancedArtistComparisonDTO> compareArtist(@PathVariable String first, @PathVariable String second, @PathVariable String third) {
        return discographyServ.compareArtist(first, second, third);
    }

    @Operation(summary = "get information of two artists")
    @GetMapping("compare-artist/first/{first}/second/{second}/")
    public List<AdvancedArtistComparisonDTO> compareArtist(@PathVariable String first, @PathVariable String second) {
        return discographyServ.compareArtist(first, second);
    }

    @Operation(summary = "Get information of artists by string separate by comma's (,).")
    @GetMapping("/compare-artist")
    public List<AdvancedArtistComparisonDTO> buy(@RequestParam("artists") String input){
        return discographyServ.compareArtist(input);
    }


}
