package io.malevich.server.dao.artwork;

import io.malevich.server.entity.ArtworkEntity;

import java.util.List;

public interface CustomArtworkDao {
    List<ArtworkEntity> findAll();
}
