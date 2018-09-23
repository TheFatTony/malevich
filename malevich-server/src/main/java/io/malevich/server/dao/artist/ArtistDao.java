package io.malevich.server.dao.artist;

import io.malevich.server.dao.Dao;
import io.malevich.server.entity.ArtistEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArtistDao extends Dao<ArtistEntity, Long> {

    List<ArtistEntity> findAll();

    ArtistEntity find(Long id);
}