package io.malevich.server.services.artist;


import io.malevich.server.dao.artist.ArtistDao;
import io.malevich.server.dao.organization.OrganizationDao;
import io.malevich.server.entity.ArtistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ArtistServiceImpl implements ArtistService {


    @Autowired
    private ArtistDao artistDao;

    protected ArtistServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistEntity> findAll() {
        return this.artistDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistEntity find(Long id) {
        return this.artistDao.find(id);
    }

}
