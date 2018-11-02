package io.malevich.server.repositories.artist;

import io.malevich.server.domain.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistDao extends JpaRepository<ArtistEntity, Long> {

    @Query("select ae from ArtistEntity ae join fetch ae.person left join fetch ae.thumbnail left join fetch ae.image")
    List<ArtistEntity> findAll();

}