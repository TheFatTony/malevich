package io.malevich.server.services.artwork;


import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.repositories.artwork.ArtworkDao;
import io.malevich.server.services.file.FileService;
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

    @Autowired
    private FileService fileService;

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
        //TODO remove this stub
        if (artwork.getThumbnail() == null)
            artwork.setThumbnail(fileService.find(1L));
        if (artwork.getImage() == null)
            artwork.setImage(fileService.find(5L));
        return this.artworkDao.save(artwork);
    }

}
