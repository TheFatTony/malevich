package io.malevich.server.services.artwork;


import io.malevich.server.dao.artwork.ArtworkDao;
import io.malevich.server.entity.ArtworkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class ArtworkServiceImpl implements ArtworkService {


    @Autowired
    private ArtworkDao artworkDao;

    protected ArtworkServiceImpl() {
    }

    @Override
    @Transactional
    public List<ArtworkEntity> findAll() {
        return this.artworkDao.findAll();
    }

    @Override
    @Transactional
    public ArtworkEntity find(Long id) {
        return this.artworkDao.find(id);
    }

}
