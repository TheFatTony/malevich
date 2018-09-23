package io.malevich.server.dao.artist;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.ArtistEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaArtistDao extends JpaDao<ArtistEntity, Long> implements ArtistDao {


    public JpaArtistDao() {
        super(ArtistEntity.class);
    }


}