package com.clara.back.endpoints.rest.service.service.impl;

import com.clara.back.endpoints.rest.service.dto.AdvancedArtistComparisonDTO;
import com.clara.back.endpoints.rest.service.exceptions.DatabaseOperationException;
import com.clara.back.endpoints.rest.service.exceptions.ExternalServiceException;
import com.clara.back.endpoints.rest.service.exceptions.DiscogsException;
import com.clara.back.endpoints.rest.service.model.bd.Artist;
import com.clara.back.endpoints.rest.service.model.bd.ArtistIndex;
import com.clara.back.endpoints.rest.service.model.wsdiscogs.DiscogsResponse;
import com.clara.back.endpoints.rest.service.model.wsdiscogs.Result;
import com.clara.back.endpoints.rest.service.repository.ArtistIndexRepository;
import com.clara.back.endpoints.rest.service.repository.ArtistRepositories;
import com.clara.back.endpoints.rest.service.service.IArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements IArtistsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArtistRepositories artistRepositories;

    @Autowired
    private ArtistIndexRepository artistIndexRepo;

    @Value("${com.clara.back.endpoints.rest.service.get.discogs.artist}")
    private String discogsApiURL;;

    @Value("${com.clara.back.endpoints.rest.key}")
    private String apiKey;

    @Value("${com.clara.back.endpoints.rest.secret}")
    private String apiSecret;


    @Override
    public List<Artist> searchArtist(String artistName) throws DatabaseOperationException, ExternalServiceException {
        try{
            String url = discogsApiURL.replace("{query}", artistName).replace("{key}", apiKey).replace("{secret}", apiSecret);
            return saveDiscographies(restTemplate.getForObject(url, DiscogsResponse.class), artistName);
        } catch (ExternalServiceException e) {
            throw new ExternalServiceException("Error al comunicarse con el servicio externo", e);
        }

    }

    @Override
    public List<Artist> getDiscoGraphies() throws DatabaseOperationException{
        List<Artist> listArtsit = artistRepositories.findAll();
        listArtsit.stream().filter(artist -> artist.getReleaseYear() == null).forEach(artist -> artist.setReleaseYear(0L));
        return listArtsit.stream().sorted(Comparator.comparing(Artist::getReleaseYear, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
    }

    @Override
    public List<AdvancedArtistComparisonDTO> compareArtist(String first, String second, String third) throws DiscogsException , IllegalArgumentException{
        if(first.isBlank() || second.isBlank()){
            throw new DiscogsException("Los nombres de los artistas no pueden estar vacíos.");
        }
        try {
            ArtistIndex firstArtistIndex = artistIndexRepo.findByNameCustom(first);
            ArtistIndex secondArtistIndex = artistIndexRepo.findByNameCustom(second);
            ArtistIndex thirdArtistIndex = artistIndexRepo.findByNameCustom(third);
            List<Artist> listFirstArtist = artistRepositories.findArtistsByArtistIndexId(firstArtistIndex.getId());
            List<Artist> listSecondArtist = artistRepositories.findArtistsByArtistIndexId(firstArtistIndex.getId());
            List<Artist> listThirdArtist = artistRepositories.findArtistsByArtistIndexId(firstArtistIndex.getId());
            List<AdvancedArtistComparisonDTO> report = new ArrayList<>();
            report.add(getAdvancedArtistComparison(listFirstArtist, first));
            report.add(getAdvancedArtistComparison(listSecondArtist, second));
            report.add(getAdvancedArtistComparison(listThirdArtist, third));
            return report;
        }catch (IllegalArgumentException ix){
            throw new DiscogsException("Ha ocurrio realizando la comparacion de los artistas 1", ix);
        }catch (DiscogsException e){
            throw new DiscogsException("Ha ocurrio realizando la comparacion de los artistas", e);
        }
    }

    @Override
    public List<AdvancedArtistComparisonDTO> compareArtist(String first, String second) throws NoSuchElementException{
        if(first.isBlank() || second.isBlank()){
            throw new IllegalArgumentException("Los nombres de los no pueden estar vacíos.");
        }
        ArtistIndex firstArtistIndex = artistIndexRepo.findByNameCustom(first);
        ArtistIndex secondArtistIndex = artistIndexRepo.findByNameCustom(second);
        List<Artist> listFirstArtist = artistRepositories.findArtistsByArtistIndexId(firstArtistIndex.getId());
        List<Artist> listSecondArtist = artistRepositories.findArtistsByArtistIndexId(firstArtistIndex.getId());
        List<AdvancedArtistComparisonDTO> report = new ArrayList<>();
        report.add(getAdvancedArtistComparison(listFirstArtist, first));
        report.add(getAdvancedArtistComparison(listSecondArtist, second));
        return report;
    }

    @Override
    public List<AdvancedArtistComparisonDTO> compareArtist(String input) throws IllegalArgumentException {
        if(input.isBlank())
            throw new IllegalArgumentException("Los parametros no pueden estar vacíos.");

        List<String> artistNames = List.of(input.split(","));
        return artistNames.stream().map(this::getAdvancedArtistComparison)
                .flatMap(List::stream).collect(Collectors.toList());}

    private List<AdvancedArtistComparisonDTO> getAdvancedArtistComparison(String artist){
        List<AdvancedArtistComparisonDTO> report = new ArrayList<>();
        ArtistIndex artistIndex = artistIndexRepo.findByNameCustom(artist);
        List<Artist> listArtist = artistRepositories.findArtistsByArtistIndexId(artistIndex.getId());
        report.add(getAdvancedArtistComparison(listArtist, artist));
        return report;
    }

    private List<Artist> saveDiscographies(DiscogsResponse discogsResponse, String artistName) throws DatabaseOperationException{
        try {
            ArtistIndex artistIndex = artistIndexRepo.save(new ArtistIndex(artistName));
            List<Artist> listArtist = convertResultListToArtistList(discogsResponse.getResults());
            listArtist.forEach(artist -> artist.setArtistIndex(artistIndex));
            return artistRepositories.saveAll(listArtist);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Violación de integridad de datos al guardar discografías", e);
        } catch (JpaSystemException e) {
            throw new DatabaseOperationException("Error del sistema JPA al guardar discografías", e);
        }
    }

    private AdvancedArtistComparisonDTO getAdvancedArtistComparison(List<Artist> listArtist, String artistName) throws NoSuchElementException {
        AdvancedArtistComparisonDTO advancedArtistComparison = new AdvancedArtistComparisonDTO();
        List<Artist> filteredList = listArtist.stream().filter(artist -> artist.getReleaseYear() != null).toList();
        if (filteredList.isEmpty())
            throw new NoSuchElementException("No hay información con año de lanzamiento disponible.");

        Optional<Artist> oldestYearRelease = filteredList.stream().min(Comparator.comparing(Artist::getReleaseYear));
        advancedArtistComparison.setOldestYearRelease(oldestYearRelease.get().getReleaseYear());
        Optional<Artist> lastYearRelease = filteredList.stream().max(Comparator.comparing(Artist::getReleaseYear));
        advancedArtistComparison.setLastYearRelease(lastYearRelease.get().getReleaseYear());
        advancedArtistComparison.setReleaseNumber(listArtist.size());
        advancedArtistComparison.setYearsOfActivity(lastYearRelease.get().getReleaseYear() - oldestYearRelease.get().getReleaseYear());
        advancedArtistComparison.setArtistName(artistName);
        List<Artist> filteredListGenres = listArtist.stream().filter(artist -> artist.getGenre() != null).toList();
        List<String> distintGenres = filteredListGenres.stream().map(Artist::getGenre).flatMap(List::stream).distinct().toList();
        advancedArtistComparison.setGenres(distintGenres);
        advancedArtistComparison.setGenreNumbers(distintGenres.size());

        List<Artist> filteredListStyles = listArtist.stream().filter(artist -> artist.getStyle() != null).toList();
        List<String> distintStyles = filteredListStyles.stream().map(Artist::getStyle).flatMap(List::stream).distinct().toList();
        advancedArtistComparison.setStyles(distintGenres);
        advancedArtistComparison.setStyleNumbers(distintGenres.size());
        return advancedArtistComparison;
    }

    private List<Artist> convertResultListToArtistList(List<Result> resultList) {
        return resultList.stream().map(this::mapResultToArtist).collect(Collectors.toList());
    }

    private Artist mapResultToArtist(Result result) {
        Artist artist = new Artist();
        artist.setStyle(result.getStyle());
        artist.setThumb(result.getThumb());
        artist.setTitle(result.getTitle());
        artist.setCountry(result.getCountry());
        artist.setFormat(result.getFormat());
        artist.setUri(result.getUri());
        //artist.setCommunity(result.getCommunity());
        //artist.setFormats(result.getFormats());
        artist.setLabel(result.getLabel());
        artist.setCatno(result.getCatno());
        artist.setReleaseYear(result.getReleaseYear());
        artist.setGenre(result.getGenre());
        artist.setResourceUrl(result.getResourceUrl());
        artist.setType(result.getType());
        artist.setId(result.getId());
        return artist;
    }
}
