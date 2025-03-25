package com.clara.back.endpoints.rest.service.service.impl;

import com.clara.back.endpoints.rest.service.model.bd.Artist;
import com.clara.back.endpoints.rest.service.model.discographies.DiscogsResponse;
import com.clara.back.endpoints.rest.service.model.discographies.Result;
import com.clara.back.endpoints.rest.service.repository.ArtistRepositories;
import com.clara.back.endpoints.rest.service.service.IArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements IArtistsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArtistRepositories artistRepositories;

    private static final String DISCOGS_API_URL = "https://api.discogs.com/database/search?q={query}&key={key}&secret={secret}";
    private static final String API_KEY = "wDCpMzLjEfVaZINjfaJQ";
    private static final String API_SECRET = "OKdclEQQzNutvfkEWVlJiONzdQhFmdIC";

    @Override
    public List<Artist> searchArtist(String artistName) {
        String url = DISCOGS_API_URL.replace("{query}", artistName).replace("{key}", API_KEY).replace("{secret}", API_SECRET);
        return saveDiscographies(restTemplate.getForObject(url, DiscogsResponse.class));
    }

    @Override
    public List<Artist> getDiscoGraphies() {
        return artistRepositories.findAll();
    }

    private List<Artist> saveDiscographies(DiscogsResponse discogsResponse) {
        try {
            return artistRepositories.saveAll(convertResultListToArtistList(discogsResponse.getResults()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Artist> convertResultListToArtistList(List<Result> resultList) {
        return resultList.stream().map(this::mapResultToArtist).collect(Collectors.toList());
    }

    private Artist mapResultToArtist(Result result) {
        Artist artist = new Artist();
        //artist.setStyle(result.getStyle());
        artist.setThumb(result.getThumb());
        artist.setTitle(result.getTitle());
        artist.setCountry(result.getCountry());
        //artist.setFormat(result.getFormat());
        artist.setUri(result.getUri());
        //artist.setCommunity(result.getCommunity());
        //artist.setLabel(result.getLabel());
        artist.setCatno(result.getCatno());
        artist.setReleaseYear(result.getReleaseYear());
        //artist.setGenre(result.getGenre());
        artist.setResourceUrl(result.getResourceUrl());
        artist.setType(result.getType());
        artist.setId(result.getId());
        return artist;
    }
}
