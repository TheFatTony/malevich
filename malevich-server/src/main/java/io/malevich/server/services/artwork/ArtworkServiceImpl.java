package io.malevich.server.services.artwork;


import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.repositories.artwork.ArtworkDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class ArtworkServiceImpl implements ArtworkService {

    @Autowired
    private ArtworkDao artworkDao;

    protected ArtworkServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkEntity> findAll() {
        return this.artworkDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ArtworkEntity find(Long id) {
        return this.artworkDao.findById(id).get();
    }

    @Override
    @Transactional
    public ArtworkEntity save(ArtworkEntity artwork) {
        ArtworkEntity savedArtworkEntity = this.artworkDao.save(artwork);
        return savedArtworkEntity;
    }

}
