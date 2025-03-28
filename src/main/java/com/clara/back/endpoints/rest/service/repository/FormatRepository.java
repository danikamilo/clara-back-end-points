package com.clara.back.endpoints.rest.service.repository;

import com.clara.back.endpoints.rest.service.model.bd.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Autor Daniel Camilo
 */
@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {

    @Query("SELECT f FROM Format f RIGHT JOIN f.discographytIndex d RIGHT JOIN d.artistIndex a WHERE a.name = :artistName")
    List<Format> findFormatsByArtist(@Param("artistName") String artistName);

    @Query("SELECT f FROM Format f RIGHT JOIN f.discographytIndex d RIGHT JOIN d.artistIndex a")
    List<Format> findDiscographies();
}
