package io.malevich.server.services.artist;


import io.malevich.server.entity.ArtistEntity;
import io.malevich.server.entity.OrganizationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistService {

    List<ArtistEntity> findAll();

    ArtistEntity find(Long id);
}
