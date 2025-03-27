package com.clara.back.endpoints.rest.service.util;

import com.clara.back.endpoints.rest.service.exceptions.NoArgumentsException;
import com.clara.back.endpoints.rest.service.model.bd.Artist;
import java.util.List;

/**
 * @Autor Daniel Camilo
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

    public static void validateInputCompareArtist(String input){
        if(input.isBlank())
            throw new NoArgumentsException(Constants.VALIDATE_INPUT_COMPARE_ARTIST_MESSAGE);

    }

    public static void validateListAdvancedArtistComparison(List<Artist> filteredList){
        if (filteredList.isEmpty())
            throw new NoArgumentsException(Constants.VALIDATE_FILTERED_LIST);
    }


}
