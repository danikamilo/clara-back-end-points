package com.clara.back.endpoints.rest.service.service.impl;

import com.clara.back.endpoints.rest.service.dto.AdvancedArtistComparisonDTO;
import com.clara.back.endpoints.rest.service.exceptions.DatabaseOperationException;
import com.clara.back.endpoints.rest.service.exceptions.InternalServiceException;
import com.clara.back.endpoints.rest.service.exceptions.NoArgumentsException;
import com.clara.back.endpoints.rest.service.model.bd.Artist;
import com.clara.back.endpoints.rest.service.model.bd.ArtistIndex;
import com.clara.back.endpoints.rest.service.model.wsdiscogs.DiscogsResponse;
import com.clara.back.endpoints.rest.service.model.wsdiscogs.Result;
import com.clara.back.endpoints.rest.service.repository.ArtistIndexRepository;
import com.clara.back.endpoints.rest.service.repository.ArtistRepositories;
import com.clara.back.endpoints.rest.service.service.IArtistsService;
import com.clara.back.endpoints.rest.service.util.Constants;
import com.clara.back.endpoints.rest.service.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

/**
 * @Autor Daniel Camilo
 */
@Service
public class ArtistServiceImpl implements IArtistsService {

    @Value("${com.clara.back.endpoints.rest.key}")
    private String apiKey;

    @Value("${com.clara.back.endpoints.rest.secret}")
    private String apiSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArtistRepositories artistRepositories;

    @Autowired
    private ArtistIndexRepository artistIndexRepo;

    @Value("${com.clara.back.endpoints.rest.service.get.discogs.artist}")
    private String discogsApiURL;

    @Override
    public List<Artist> searchArtist(String artistName) throws InternalServiceException {
        try{
            Validators.validateInputsearchArtist(artistName);
            String url = discogsApiURL.replace("{query}", artistName).replace("{key}", apiKey).replace("{secret}", apiSecret);
            DiscogsResponse discogsResponse = restTemplate.getForObject(url, DiscogsResponse.class);
            if(discogsResponse != null) {
                return saveDiscographies(discogsResponse, artistName);
            }
        } catch (InternalServiceException e) {
            throw new InternalServiceException(Constants.INTERNAL_SERVER_ERROR, e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Artist> getDiscoGraphies() throws InternalServiceException{
        try{
            List<Artist> listArtsit = artistRepositories.findAll();
            listArtsit.stream().filter(artist -> artist.getReleaseYear() == null).forEach(artist -> artist.setReleaseYear(0L));
            listArtsit.stream().sorted(Comparator.comparing(Artist::getReleaseYear, Comparator.nullsLast(Comparator.reverseOrder()))).forEach(artist -> artist.setArtistIndex(null));
            return listArtsit;
        } catch (InternalServiceException e) {
            throw new InternalServiceException(Constants.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public List<AdvancedArtistComparisonDTO> compareArtist(String first, String second, String third) throws NoArgumentsException, InternalServiceException {
        Validators.validateInputCompareArtists(first, second);
        try {
            Optional<ArtistIndex> firstArtistIndex = artistIndexRepo.findByNameCustom(first);
            Optional<ArtistIndex> secondArtistIndex = artistIndexRepo.findByNameCustom(second);
            Optional<ArtistIndex> thirdArtistIndex = artistIndexRepo.findByNameCustom(third);
            List<Artist> listFirstArtist = artistRepositories.findArtistsByArtistIndexId(firstArtistIndex.get().getId());
            List<Artist> listSecondArtist = artistRepositories.findArtistsByArtistIndexId(secondArtistIndex.get().getId());
            List<Artist> listThirdArtist = artistRepositories.findArtistsByArtistIndexId(thirdArtistIndex.get().getId());
            List<AdvancedArtistComparisonDTO> report = new ArrayList<>();
            report.add(getAdvancedArtistComparison(listFirstArtist, first));
            report.add(getAdvancedArtistComparison(listSecondArtist, second));
            report.add(getAdvancedArtistComparison(listThirdArtist, third));
            return report;
        }catch (InternalServiceException e){
            throw new InternalServiceException(Constants.ERROR_PROCESSING_COMPARE_ARTIST, e);
        }
    }

    @Override
    public List<AdvancedArtistComparisonDTO> compareArtist(String first, String second) throws NoArgumentsException, InternalServiceException {
        Validators.validateInputCompareArtists(first, second);
        try{
            Optional<ArtistIndex> firstArtistIndex = artistIndexRepo.findByNameCustom(first);
            Optional<ArtistIndex> secondArtistIndex = artistIndexRepo.findByNameCustom(second);
            List<Artist> listFirstArtist = artistRepositories.findArtistsByArtistIndexId(firstArtistIndex.get().getId());
            List<Artist> listSecondArtist = artistRepositories.findArtistsByArtistIndexId(secondArtistIndex.get().getId());
            List<AdvancedArtistComparisonDTO> report = new ArrayList<>();
            report.add(getAdvancedArtistComparison(listFirstArtist, first));
            report.add(getAdvancedArtistComparison(listSecondArtist, second));
            return report;
        }catch (InternalServiceException e){
            throw new InternalServiceException(Constants.ERROR_PROCESSING_COMPARE_ARTIST, e);
        }
    }

    @Override
    public List<AdvancedArtistComparisonDTO> compareArtist(String artists) throws NoArgumentsException, InternalServiceException {
        Validators.validateInputCompareArtist(artists);
        List<String> artistNames = List.of(artists.split(","));
        return artistNames.stream().map(this::getAdvancedArtistComparison)
                .flatMap(List::stream).toList();
    }

    private List<AdvancedArtistComparisonDTO> getAdvancedArtistComparison(String artist) throws NoArgumentsException, InternalServiceException {
        try{
            List<AdvancedArtistComparisonDTO> report = new ArrayList<>();
            Optional<ArtistIndex> artistIndex = artistIndexRepo.findByNameCustom(artist);
            List<Artist> listArtist = artistRepositories.findArtistsByArtistIndexId(artistIndex.get().getId());
            report.add(getAdvancedArtistComparison(listArtist, artist));
            return report;
        }catch (InternalServiceException e){
            throw new InternalServiceException(Constants.ERROR_PROCESSING_COMPARE_ARTIST, e);
        }
    }

    private List<Artist> saveDiscographies(DiscogsResponse discogsResponse, String artistName) throws DatabaseOperationException{
        try {
            Optional<ArtistIndex> findDBArtistIndex = artistIndexRepo.findByNameCustom(artistName);
            if(findDBArtistIndex.isEmpty()) {
                ArtistIndex artistIndex = artistIndexRepo.save(new ArtistIndex(artistName));
                List<Artist> listArtist = convertResultListToArtistList(discogsResponse.getResults());
                listArtist.forEach(artist -> artist.setArtistIndex(artistIndex));
                return artistRepositories.saveAll(listArtist);
            }else{
                return convertResultListToArtistList(discogsResponse.getResults());
            }
        } catch (InternalServiceException e) {
            throw new InternalServiceException(Constants.INTERNAL_SERVER_ERROR, e);
        }
    }

    private AdvancedArtistComparisonDTO getAdvancedArtistComparison(List<Artist> listArtist, String artistName) throws NoArgumentsException, InternalServiceException {
        AdvancedArtistComparisonDTO advancedArtistComparison = new AdvancedArtistComparisonDTO();
        List<Artist> filteredList = listArtist.stream().filter(artist -> artist.getReleaseYear() != null).toList();
        Validators.validateListAdvancedArtistComparison(filteredList);
        try{
            Optional<Artist> oldestYearRelease = filteredList.stream().min(Comparator.comparing(Artist::getReleaseYear));
            if(oldestYearRelease.isPresent()) {
                advancedArtistComparison.setOldestYearRelease(oldestYearRelease.get().getReleaseYear());
            }
            Optional<Artist> lastYearRelease = filteredList.stream().max(Comparator.comparing(Artist::getReleaseYear));
            if(lastYearRelease.isPresent()) {
                advancedArtistComparison.setLastYearRelease(lastYearRelease.get().getReleaseYear());
                advancedArtistComparison.setReleaseNumber(listArtist.size());
                advancedArtistComparison.setYearsOfActivity(lastYearRelease.get().getReleaseYear() - oldestYearRelease.get().getReleaseYear());
            }
            advancedArtistComparison.setArtistName(artistName);
            List<Artist> filteredListGenres = listArtist.stream().filter(artist -> artist.getGenre() != null).toList();
            List<String> distintGenres = filteredListGenres.stream().map(Artist::getGenre).flatMap(List::stream).distinct().toList();
            advancedArtistComparison.setGenres(distintGenres);
            advancedArtistComparison.setGenreNumbers(distintGenres.size());

            List<Artist> filteredListStyles = listArtist.stream().filter(artist -> artist.getStyle() != null).toList();
            filteredListStyles.stream().map(Artist::getStyle).flatMap(List::stream).distinct();
            advancedArtistComparison.setStyles(distintGenres);
            advancedArtistComparison.setStyleNumbers(distintGenres.size());
            return advancedArtistComparison;
        } catch (InternalServiceException e) {
            throw new InternalServiceException(Constants.INTERNAL_SERVER_ERROR, e);
        }

    }

    private List<Artist> convertResultListToArtistList(List<Result> resultList) {
        return resultList.stream().map(this::mapResultToArtist).toList();
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
