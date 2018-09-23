package io.malevich.server.services.artwork;


import io.malevich.server.entity.ArtworkEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtworkService {

    List<ArtworkEntity> findAll();

    ArtworkEntity find(Long id);
}
