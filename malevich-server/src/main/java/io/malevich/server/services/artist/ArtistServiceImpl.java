package io.malevich.server.services.artist;


import com.yinyang.core.server.services.file.FileService;
import io.malevich.server.repositories.artist.ArtistDao;
import io.malevich.server.domain.ArtistEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class ArtistServiceImpl implements ArtistService {


    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private FileService fileService;

    protected ArtistServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistEntity> findAll() {
        return this.artistDao.findAll();
    }

    @Override
    @Transactional
    public ArtistEntity save(ArtistEntity artist) {
        //TODO remove this stub
        if (artist.getThumbnail() == null)
            artist.setThumbnail(fileService.find(4L));
        if (artist.getImage() == null)
            artist.setImage(fileService.find(6L));
        return artistDao.save(artist);
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistEntity find(Long id) {
        return this.artistDao.findById(id).get();
    }

}
