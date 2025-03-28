package com.clara.back.endpoints.rest.service.service.impl;

import com.clara.back.endpoints.rest.service.dto.AdvancedArtistComparisonDTO;
import com.clara.back.endpoints.rest.service.dto.ArtistsDTO;
import com.clara.back.endpoints.rest.service.dto.DiscographyDTO;
import com.clara.back.endpoints.rest.service.dto.FormatsDTO;
import com.clara.back.endpoints.rest.service.exceptions.DatabaseOperationException;
import com.clara.back.endpoints.rest.service.exceptions.InternalServiceException;
import com.clara.back.endpoints.rest.service.exceptions.NoArgumentsException;
import com.clara.back.endpoints.rest.service.model.bd.Discography;
import com.clara.back.endpoints.rest.service.model.bd.Artist;
import com.clara.back.endpoints.rest.service.model.bd.Format;
import com.clara.back.endpoints.rest.service.model.wsdiscogs.DiscogsResponse;
import com.clara.back.endpoints.rest.service.model.wsdiscogs.Result;
import com.clara.back.endpoints.rest.service.repository.ArtistRepository;
import com.clara.back.endpoints.rest.service.repository.DiscographyRepositories;
import com.clara.back.endpoints.rest.service.repository.FormatRepository;
import com.clara.back.endpoints.rest.service.service.IDiscographyService;
import com.clara.back.endpoints.rest.service.util.Constants;
import com.clara.back.endpoints.rest.service.util.Validators;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Autor Daniel Camilo
 */
@Service
public class DiscographyServiceImpl implements IDiscographyService {

    @Value("${com.clara.back.endpoints.rest.key}")
    private String apiKey;

    @Value("${com.clara.back.endpoints.rest.secret}")
    private String apiSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscographyRepositories discographyRepositories;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private FormatRepository formatRepository;

    @Value("${com.clara.back.endpoints.rest.service.get.discogs.artist}")
    private String discogsApiURL;

    @Override
    public List<ArtistsDTO> searchALlArtist() throws InternalServiceException {
        List<Artist> listArtist = artistRepository.findAll();
        ArtistsDTO artistsDTO = new ArtistsDTO();
        List<ArtistsDTO> listArtistDTO = new ArrayList<>();
        List<DiscographyDTO> listDiscographyDTO = new ArrayList<>();
        for(Artist artist : listArtist){
            artistsDTO.setName(artist.getName());
            for(Discography discography : artist.getIdIndex()){
                DiscographyDTO dto = new DiscographyDTO();
                dto.setStyle(discography.getStyle());
                dto.setThumb(discography.getThumb());
                dto.setTitle(discography.getTitle());
                dto.setCountry(discography.getCountry());
                dto.setFormat(discography.getFormat());
                dto.setUri(discography.getUri());
                dto.setLabel(discography.getLabel());
                dto.setBarcode(discography.getBarcode());
                dto.setCatno(discography.getCatno());
                dto.setReleaseYear(discography.getReleaseYear());
                dto.setGenre(discography.getGenre());
                dto.setResourceUrl(discography.getResourceUrl());
                dto.setType(discography.getType());
                dto.setCommunity(discography.getCommunity());
                List<FormatsDTO> listFomatsDTO = new ArrayList<>();
                for(Format format : discography.getFormatIndex()){
                    FormatsDTO formatsDTO = new FormatsDTO();
                    formatsDTO.setName(format.getName());
                    formatsDTO.setQty(format.getQty());
                    formatsDTO.setText(format.getText());
                    formatsDTO.setDescriptions(format.getDescriptions());
                    listFomatsDTO.add(formatsDTO);
                }
                dto.setFormats(listFomatsDTO);
                listDiscographyDTO.add(dto);
            }
            artistsDTO.setDiscography(listDiscographyDTO);
            listArtistDTO.add(artistsDTO);
        }
        return listArtistDTO;
    }

    @Override
    public ArtistsDTO searchArtist(String artistName) throws InternalServiceException {
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
        return new ArtistsDTO();
    }

    @Override
    public ArtistsDTO searchAllDiscoGraphies() throws InternalServiceException {
        try{
            return showArtistInfo(formatRepository.findDiscographies());
        } catch (InternalServiceException e) {
            throw new InternalServiceException(Constants.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public List<AdvancedArtistComparisonDTO> compareArtist(String first, String second, String third) throws NoArgumentsException, InternalServiceException {
        Validators.validateInputCompareArtists(first, second);
        try {
            Optional<Artist> firstArtistIndex = artistRepository.findByNameCustom(first);
            Optional<Artist> secondArtistIndex = artistRepository.findByNameCustom(second);
            Optional<Artist> thirdArtistIndex = artistRepository.findByNameCustom(third);
            Validators.validateArtistsBD(firstArtistIndex, secondArtistIndex, thirdArtistIndex);
            List<Discography> listFirstArtist = discographyRepositories.findDiscographiesById(firstArtistIndex.get().getId());
            List<Discography> listSecondArtist = discographyRepositories.findDiscographiesById(secondArtistIndex.get().getId());
            List<Discography> listThirdArtist = discographyRepositories.findDiscographiesById(thirdArtistIndex.get().getId());
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
            Optional<Artist> firstArtistIndex = artistRepository.findByNameCustom(first);
            Optional<Artist> secondArtistIndex = artistRepository.findByNameCustom(second);
            Validators.validateArtistsBD(firstArtistIndex, secondArtistIndex);
            List<Discography> listFirstArtist = discographyRepositories.findDiscographiesById(firstArtistIndex.get().getId());
            List<Discography> listSecondArtist = discographyRepositories.findDiscographiesById(secondArtistIndex.get().getId());
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
        return artistNames.stream().map(this::getAdvancedArtistComparison).flatMap(List::stream).toList();
    }

    private List<AdvancedArtistComparisonDTO> getAdvancedArtistComparison(String artist) throws NoArgumentsException, InternalServiceException {
        try{
            List<AdvancedArtistComparisonDTO> report = new ArrayList<>();
            Optional<Artist> artistIndex = artistRepository.findByNameCustom(artist);
            Validators.validateArtistsBD(artistIndex, artist);
            List<Discography> listArtist = discographyRepositories.findDiscographiesById(artistIndex.get().getId());
            report.add(getAdvancedArtistComparison(listArtist, artist));
            return report;
        }catch (InternalServiceException e){
            throw new InternalServiceException(Constants.ERROR_PROCESSING_COMPARE_ARTIST, e);
        }
    }

    private ArtistsDTO saveDiscographies(DiscogsResponse discogsResponse, String artistName) throws DatabaseOperationException{
        try {
            Optional<Artist> findDBArtistIndex = artistRepository.findByNameCustom(artistName);
            Artist artistIndex;
            if(findDBArtistIndex.isEmpty()) {
                artistIndex = artistRepository.save(new Artist(artistName));
                List<Discography> listDiscographies = convertResultListToArtistList(discogsResponse.getResults(), artistIndex);
                listDiscographies.forEach(artist -> artist.setArtistIndex(artistIndex));
                return mapToArtistsDTO(artistName, listDiscographies);
            }else{
                return showArtistInfo(artistName,formatRepository.findFormatsByArtist(artistName));
            }
        } catch (InternalServiceException e) {
            throw new InternalServiceException(Constants.INTERNAL_SERVER_ERROR, e);
        }
    }

    private ArtistsDTO mapToArtistsDTO(String artistName, List<Discography> discographies) {
        List<DiscographyDTO> discographyDTOs = discographies.stream()
                .map(d -> {
                    DiscographyDTO dto = new DiscographyDTO();
                    dto.setStyle(d.getStyle());
                    dto.setThumb(d.getThumb());
                    dto.setTitle(d.getTitle());
                    dto.setCountry(d.getCountry());
                    dto.setFormat(d.getFormat());
                    dto.setUri(d.getUri());
                    dto.setLabel(d.getLabel());
                    dto.setBarcode(d.getBarcode());
                    dto.setCatno(d.getCatno());
                    dto.setReleaseYear(d.getReleaseYear());
                    dto.setGenre(d.getGenre());
                    dto.setResourceUrl(d.getResourceUrl());
                    dto.setType(d.getType());
                    dto.setCommunity(d.getCommunity());
                    return dto;
                }).toList();
        ArtistsDTO artistsDTO = new ArtistsDTO();
        artistsDTO.setName(artistName);
        artistsDTO.setDiscography(discographyDTOs);
        return artistsDTO;
    }

    private ArtistsDTO showArtistInfo(List<Format> formats){
        String artistName = StringUtils.EMPTY;
        List<DiscographyDTO> discographyDTOS = new ArrayList<>();
        FormatsDTO formatsDTO = new FormatsDTO();
        ArtistsDTO artistsDTO = new ArtistsDTO();
        for(Format format : formats) {
            if (format != null) {
                artistName = format.getDiscographytIndex().getArtistIndex().getName();
                formatsDTO.setName(Optional.ofNullable(format.getName()).orElse(StringUtils.EMPTY));
                formatsDTO.setQty(format.getQty());
                formatsDTO.setText(format.getText());
                formatsDTO.setDescriptions(format.getDescriptions());
                discographyDTOS.add(toDTO(format.getDiscographytIndex(), formatsDTO));
            }
        }
        artistsDTO.setDiscography(discographyDTOS);
        artistsDTO.setName(artistName);
        return artistsDTO;
    }

    private ArtistsDTO showArtistInfo(String artistName, List<Format> listFormats){
        List<DiscographyDTO> discographyDTOS = new ArrayList<>();
        FormatsDTO formatsDTO = new FormatsDTO();
        ArtistsDTO artistsDTO = new ArtistsDTO();
        for(Format format : listFormats) {
            if (format != null) {
                formatsDTO.setName(Optional.ofNullable(format.getName()).orElse(StringUtils.EMPTY));
                formatsDTO.setQty(format.getQty());
                formatsDTO.setText(format.getText());
                formatsDTO.setDescriptions(format.getDescriptions());
                discographyDTOS.add(toDTO(format.getDiscographytIndex(), formatsDTO));
            }
        }
        artistsDTO.setDiscography(discographyDTOS);
        artistsDTO.setName(artistName);
        return artistsDTO;
    }

    private DiscographyDTO toDTO(Discography discography, FormatsDTO formatsDTO){
        DiscographyDTO dto = new DiscographyDTO();
        dto.setFormats(List.of(formatsDTO));
        dto.setStyle(discography.getStyle());
        dto.setThumb(discography.getThumb());
        dto.setTitle(discography.getTitle());
        dto.setCountry(discography.getCountry());
        dto.setFormat(discography.getFormat());
        dto.setUri(discography.getUri());
        dto.setLabel(discography.getLabel());
        dto.setBarcode(discography.getBarcode());
        dto.setCatno(discography.getCatno());
        dto.setReleaseYear(discography.getReleaseYear());
        dto.setGenre(discography.getGenre());
        dto.setResourceUrl(discography.getResourceUrl());
        dto.setType(discography.getType());
        dto.setCommunity(discography.getCommunity());
        return dto;
    }

    private AdvancedArtistComparisonDTO getAdvancedArtistComparison(List<Discography> listArtist, String artistName) throws NoArgumentsException, InternalServiceException {
        AdvancedArtistComparisonDTO advancedArtistComparison = new AdvancedArtistComparisonDTO();
        List<Discography> filteredList = listArtist.stream().filter(artist -> artist.getReleaseYear() != null).toList();
        Validators.validateListAdvancedArtistComparison(filteredList);
        try{
            Optional<Discography> oldestYearRelease = filteredList.stream().min(Comparator.comparing(Discography::getReleaseYear));
            if(oldestYearRelease.isPresent()) {
                advancedArtistComparison.setOldestYearRelease(oldestYearRelease.get().getReleaseYear());
            }
            Optional<Discography> lastYearRelease = filteredList.stream().max(Comparator.comparing(Discography::getReleaseYear));
            if(lastYearRelease.isPresent()) {
                advancedArtistComparison.setLastYearRelease(lastYearRelease.get().getReleaseYear());
                advancedArtistComparison.setReleaseNumber(listArtist.size());
                advancedArtistComparison.setYearsOfActivity(lastYearRelease.get().getReleaseYear() - oldestYearRelease.get().getReleaseYear());
            }
            advancedArtistComparison.setArtistName(artistName);
            List<Discography> filteredListGenres = listArtist.stream().filter(artist -> artist.getGenre() != null).toList();
            List<String> distintGenres = filteredListGenres.stream().map(Discography::getGenre).flatMap(List::stream).distinct().toList();
            advancedArtistComparison.setGenres(distintGenres);
            advancedArtistComparison.setGenreNumbers(distintGenres.size());

            List<Discography> filteredListStyles = listArtist.stream().filter(artist -> artist.getStyle() != null).toList();
            filteredListStyles.stream().map(Discography::getStyle).flatMap(List::stream).distinct();
            advancedArtistComparison.setStyles(distintGenres);
            advancedArtistComparison.setStyleNumbers(distintGenres.size());
            return advancedArtistComparison;
        } catch (InternalServiceException e) {
            throw new InternalServiceException(Constants.INTERNAL_SERVER_ERROR, e);
        }

    }

    private List<Discography> convertResultListToArtistList(List<Result> resultList, Artist artistIndex) {
        List<Discography> listDiscography = new ArrayList<>();
        for (Result result : resultList){
            listDiscography.add(mapResultToArtist(result, artistIndex));
        }
        return listDiscography;
    }

    private Discography mapResultToArtist(Result result, Artist artistIndex) {
        Discography discography = new Discography();
        discography.setStyle(result.getStyle());
        discography.setThumb(result.getThumb());
        discography.setTitle(result.getTitle());
        discography.setCountry(result.getCountry());
        discography.setFormat(result.getFormat());
        discography.setUri(result.getUri());
        if(result.getCommunity() != null)
            discography.setCommunity(result.getCommunity().getHave()+","+result.getCommunity().getWant());

        discography.setLabel(result.getLabel());
        discography.setCatno(result.getCatno());
        discography.setReleaseYear(result.getReleaseYear());
        discography.setGenre(result.getGenre());
        discography.setResourceUrl(result.getResourceUrl());
        discography.setType(result.getType());
        discography.setId(result.getId());
        discography.setArtistIndex(artistIndex);
        discography = discographyRepositories.save(discography);
        List<com.clara.back.endpoints.rest.service.model.bd.Format> listFormatBd = new ArrayList<>();
        if(result.getFormats() != null) {
            List<com.clara.back.endpoints.rest.service.model.wsdiscogs.Format> listFormatWs = result.getFormats();
            listFormatBd = listFormatWs.stream().map(f -> new Format(f.getName(), f.getQty(), f.getText(), String.join(",", f.getDescriptions()))).toList();
            discography.setFormatIndex(listFormatBd);
            Discography finalDiscography = discography;
            listFormatBd.forEach(format -> format.setDiscographytIndex(finalDiscography));
        }
        formatRepository.saveAll(listFormatBd);
        return discography;
    }
}
