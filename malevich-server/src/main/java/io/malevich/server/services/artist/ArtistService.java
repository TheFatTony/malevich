package io.malevich.server.services.artist;


import io.malevich.server.domain.ArtistEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistService {

    List<ArtistEntity> findAll();

    ArtistEntity find(Long id);
}
