package com.clara.back.endpoints.rest.service.repository;

import com.clara.back.endpoints.rest.service.model.bd.Discography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Autor Daniel Camilo
 */
@Repository
public interface DiscographyRepositories extends JpaRepository<Discography, Long> {

    @Query("SELECT a FROM Discography a WHERE a.artistIndex.id = :idIndex")
    List<Discography> findDiscographiesById(@Param("idIndex") Long idIndex);

}
