package com.clara.back.endpoints.rest.service.repository;

import com.clara.back.endpoints.rest.service.model.bd.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Autor Daniel Camilo
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT a FROM Artist a WHERE a.name = :name")
    Optional<Artist> findByNameCustom(@Param("name") String name);

    @Query("SELECT DISTINCT a FROM Artist a LEFT JOIN FETCH a.idIndex d LEFT JOIN FETCH d.formatIndex f WHERE a.name = :artistName")
    List<Artist> findArtistWithDiscographiesAndFormats(@Param("artistName") String artistName);
}
