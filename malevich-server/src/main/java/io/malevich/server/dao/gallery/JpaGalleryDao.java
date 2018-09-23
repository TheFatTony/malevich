package io.malevich.server.dao.gallery;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.GalleryEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaGalleryDao extends JpaDao<GalleryEntity, Long> implements GalleryDao {


    public JpaGalleryDao() {
        super(GalleryEntity.class);
    }


}
