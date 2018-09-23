package io.malevich.server.dao.artwork;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.ArtworkEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaArtworkDao extends JpaDao<ArtworkEntity, Long> implements ArtworkDao {


    public JpaArtworkDao() {
        super(ArtworkEntity.class);
    }


}
