package io.malevich.server.dao.artist;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.ArtistEntity;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class JpaArtistDao extends JpaDao<ArtistEntity, Long> implements ArtistDao {


    public JpaArtistDao() {
        super(ArtistEntity.class);
    }

    @Override
    public List<ArtistEntity> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<ArtistEntity> criteriaQuery = builder.createQuery(this.entityClass);

        Root<ArtistEntity> root = criteriaQuery.from(this.entityClass);
        root.fetch("person", JoinType.INNER);
        root.fetch("thumbnail", JoinType.LEFT);

        criteriaQuery.select(root);
        TypedQuery<ArtistEntity> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}