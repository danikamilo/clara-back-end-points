package com.clara.back.endpoints.rest.service.repository;

import com.clara.back.endpoints.rest.service.model.bd.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Autor Daniel Camilo
 */
@Repository
public interface ArtistRepositories extends JpaRepository<Artist, Long> {
}
