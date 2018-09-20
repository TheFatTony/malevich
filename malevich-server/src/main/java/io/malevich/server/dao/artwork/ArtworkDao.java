package io.malevich.server.dao.artwork;

import io.malevich.server.dao.Dao;
import io.malevich.server.entity.ArtworkEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArtworkDao extends Dao<ArtworkEntity, Long> {

    List<ArtworkEntity> findAll();

    ArtworkEntity find(Long id);
}
