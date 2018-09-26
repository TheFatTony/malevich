package io.malevich.server.dao.artwork;

import io.malevich.server.entity.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkDao extends JpaRepository<ArtworkEntity, Long>, CustomArtworkDao {

//    @Query("select ae from ArtworkEntity ae join fetch ae.category left join fetch ae.thumbnail left join fetch ae.image")
//    List<ArtworkEntity> findAll();
}
