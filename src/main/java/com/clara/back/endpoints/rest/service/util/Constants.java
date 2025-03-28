package com.clara.back.endpoints.rest.service.util;

/**
 * @Autor Daniel Camilo
 */
public class Constants {

    public static final String VALIDATE_INPUT_SEARCH_ARTIST_MESSAGE = "El parametro no puede ser vacío.";
    public static final String INTERNAL_SERVER_ERROR = "Error interno al procesar la solicitud";
    public static final String VALIDATE_INPUT_COMPARE_ARTIST = "Los nombres de los artistas no pueden estar vacíos.";
    public static final String ERROR_PROCESSING_COMPARE_ARTIST = "Ha ocurrio realizando la comparacion de los artistas";
    public static final String VALIDATE_INPUT_COMPARE_ARTIST_MESSAGE = "Se deben digitar los artistas para realizar la coparación.";
    public static final String VALIDATE_FILTERED_LIST = "No hay información con año de lanzamiento disponible.";
    public static final String RESPONSE_EMPTY = "El servicio no tiene respuesta para esta solicitud";

    public static final String ARTIST_NOT_FOUND_BD = "Nombre de artista no encontrado.";
    public static final String FIRST_ARTIST_NOT_FOUND_BD = "Nombre de primer artista no encontrado.";
    public static final String SECOND_ARTIST_NOT_FOUND_BD = "Nombre de segundo artista no encontrado.";
    public static final String THIRD_ARTIST_NOT_FOUND_BD =  "Nombre de tercer artista no encontrado.";

    private Constants() {
    }
}
