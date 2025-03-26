package com.clara.back.endpoints.rest.service.repository;

import com.clara.back.endpoints.rest.service.model.bd.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Autor Daniel Camilo
 */
@Repository
public interface ArtistRepositories extends JpaRepository<Artist, Long> {

    @Query("SELECT a FROM Artist a WHERE a.artistIndex.id = :idIndex")
    List<Artist> findArtistsByArtistIndexId(@Param("idIndex") Long idIndex);

}
