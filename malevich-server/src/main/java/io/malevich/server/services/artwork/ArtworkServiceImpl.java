package io.malevich.server.services.artwork;


import io.malevich.server.dao.artwork.ArtworkDao;
import io.malevich.server.entity.ArtworkEntity;
import io.malevich.server.services.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

        if(artwork.getDescription() == null)
        {
            if(artwork.getDescriptionMl() != null && artwork.getDescriptionMl().containsKey("en"))
                artwork.setDescription(artwork.getDescriptionMl().get("en"));
            else
                artwork.setDescription(artwork.getDescriptionMl().values().stream().findFirst().orElseGet(null));
        }

        if(artwork.getTitle() == null)
        {
            if(artwork.getTitleMl() != null && artwork.getTitleMl().containsKey("en"))
                artwork.setTitle(artwork.getTitleMl().get("en"));
            else
                artwork.setTitle(artwork.getTitleMl().values().stream().findFirst().orElseGet(null));
        }

        //stub
        if(artwork.getThumbnail() == null)
            artwork.setThumbnail(fileService.find(1L));

        //stub
        if(artwork.getImage() == null)
            artwork.setImage(fileService.find(5L));

        return this.artworkDao.save(artwork);
    }

}
