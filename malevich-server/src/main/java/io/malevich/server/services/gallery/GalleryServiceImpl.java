package io.malevich.server.services.gallery;


import io.malevich.server.dao.gallery.GalleryDao;
import io.malevich.server.entity.GalleryEntity;
import io.malevich.server.entity.TraderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class GalleryServiceImpl implements GalleryService {


    @Autowired
    private GalleryDao galleryDao;

    protected GalleryServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryEntity> findAll() {
        return this.galleryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GalleryEntity find(Long id) {
        return this.galleryDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public GalleryEntity findByUserName(String name) {
        return galleryDao.findByUsers_Name(name).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public GalleryEntity getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else return null;
        String username = userDetails.getUsername();
        GalleryEntity galleryEntity = findByUserName(username);
        return galleryEntity;
    }

    @Override
    @Transactional
    public GalleryEntity update(GalleryEntity newEntity) {
        GalleryEntity traderEntity = getCurrent();
        traderEntity.setAddresses(newEntity.getAddresses());
        traderEntity.setDescription(newEntity.getDescription());
        traderEntity.setDescriptionMl(newEntity.getDescriptionMl());
        traderEntity.setOrganization(newEntity.getOrganization());
        traderEntity.setImage(newEntity.getImage());
        traderEntity.setThumbnail(newEntity.getThumbnail());
        return galleryDao.save(traderEntity);
    }

}