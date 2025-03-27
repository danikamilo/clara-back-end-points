package com.clara.back.endpoints.rest.service.repository;

import com.clara.back.endpoints.rest.service.model.bd.ArtistIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * @Autor Daniel Camilo
 */
@Repository
public interface ArtistIndexRepository extends JpaRepository<ArtistIndex, Long> {

    @Query("SELECT a FROM ArtistIndex a WHERE a.name = :name")
    Optional<ArtistIndex> findByNameCustom(@Param("name") String name);
}
