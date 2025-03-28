package com.clara.back.endpoints.rest.service.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
/**
 * @Autor Daniel Camilo
 */

class ArtistsControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8081/v1/artist";
    }

    @Test
    public void testSearchArtist_ValidArtist() {
        given()
                .pathParam("artistName", "Beatles")
                .when()
                .get("/search-artist/{artistName}/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testSearchArtist_EmptyResponse() {
        given()
                .pathParam("artistName", "UnknownArtist34")
                .when()
                .get("/search-artist/{artistName}/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testSearchArtist_InvalidRequest() {
        given()
                .when()
                .get("/search-artist/")
                .then()
                .statusCode(404);
    }

    // Pruebas para getDiscoGraphies
    @Test
    public void testGetDiscographies_Success() {
        given()
                .when()
                .get("/search-discographies/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetDiscographies_NotEmpty() {
        given()
                .when()
                .get("/search-discographies")
                .then()
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetDiscographies_InvalidEndpoint() {
        given()
                .when()
                .get("/search-discographies-wrong")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCompareArtist_ThreeArtists() {
        given()
                .pathParam("first", "nirvana")
                .pathParam("second", "sting")
                .pathParam("third", "ktm")
                .when()
                .get("/compare-artist/first/{first}/second/{second}/third/{third}/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testCompareArtist_TwoArtists() {
        given()
                .pathParam("first", "nirvana")
                .pathParam("second", "ktm")
                .when()
                .get("/compare-artist/first/{first}/second/{second}/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
    @Test
    public void testCompareArtist_InvalidArtists() {
        given()
                .pathParam("first", "Unknown1")
                .pathParam("second", "Unknown2")
                .when()
                .get("/compare-artist/first/{first}/second/{second}/")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCompareArtist_WithQueryParam() {
        given()
                .queryParam("artists", "ktm,nirvana,sting")
                .when()
                .get("/compare-artist")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
