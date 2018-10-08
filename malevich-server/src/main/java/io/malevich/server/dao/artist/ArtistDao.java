package io.malevich.server.dao.artist;

import io.malevich.server.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistDao extends JpaRepository<ArtistEntity, Long> {

    @Query("select ae from ArtistEntity ae join fetch ae.person left join fetch ae.thumbnail left join fetch ae.image")
    List<ArtistEntity> findAll();

}