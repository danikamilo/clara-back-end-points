package com.clara.back.endpoints.rest.service.util;

import com.clara.back.endpoints.rest.service.exceptions.NoArgumentsException;
import com.clara.back.endpoints.rest.service.model.bd.Discography;
import com.clara.back.endpoints.rest.service.model.bd.Artist;
import java.util.List;
import java.util.Optional;

/**
 * @Autor Daniel Camilo
 * This class contains the method validator of service.
 */
public class Validators {

    public static void validateInputsearchArtist(String arg){
        if(arg.isBlank())
            throw new NoArgumentsException(Constants.VALIDATE_INPUT_SEARCH_ARTIST_MESSAGE);
    }

    public static void validateInputCompareArtists(String first, String second){
        if(first.isBlank() || second.isBlank())
            throw new NoArgumentsException(Constants.VALIDATE_INPUT_COMPARE_ARTIST);

    }

    public static void validateArtistsBD(Optional<Artist> firstArtist, Optional<Artist> secondArtist, Optional<Artist> thirdArtist){
        firstArtist.orElseThrow(() -> new NoArgumentsException(Constants.FIRST_ARTIST_NOT_FOUND_BD ));
        secondArtist.orElseThrow(() -> new NoArgumentsException(Constants.SECOND_ARTIST_NOT_FOUND_BD ));
        thirdArtist.orElseThrow(() -> new NoArgumentsException(Constants.THIRD_ARTIST_NOT_FOUND_BD ));
    }

    public static void validateArtistsBD(Optional<Artist> firstArtist, Optional<Artist> secondArtist){
        firstArtist.orElseThrow(() -> new NoArgumentsException(Constants.FIRST_ARTIST_NOT_FOUND_BD ));
        secondArtist.orElseThrow(() -> new NoArgumentsException(Constants.SECOND_ARTIST_NOT_FOUND_BD ));
    }

    public static void validateArtistsBD(Optional<Artist> artist, String artistName){
        artist.orElseThrow(() -> new NoArgumentsException(Constants.ARTIST_NOT_FOUND_BD +"  "+ artistName));
    }

    public static void validateInputCompareArtist(String input){
        if(input.isBlank())
            throw new NoArgumentsException(Constants.VALIDATE_INPUT_COMPARE_ARTIST_MESSAGE);

    }

    public static void validateListAdvancedArtistComparison(List<Discography> filteredList){
        if (filteredList.isEmpty())
            throw new NoArgumentsException(Constants.VALIDATE_FILTERED_LIST);
    }


}
