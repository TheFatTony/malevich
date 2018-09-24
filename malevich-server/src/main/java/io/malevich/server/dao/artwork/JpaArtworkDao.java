package io.malevich.server.dao.artwork;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.ArtworkEntity;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class JpaArtworkDao extends JpaDao<ArtworkEntity, Long> implements ArtworkDao {


    public JpaArtworkDao() {
        super(ArtworkEntity.class);
    }
    
    @Override
    public List<ArtworkEntity> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<ArtworkEntity> criteriaQuery = builder.createQuery(this.entityClass);

        Root<ArtworkEntity> root = criteriaQuery.from(this.entityClass);
        root.fetch("category", JoinType.INNER);
        root.fetch("thumbnail", JoinType.LEFT);

        criteriaQuery.select(root);

        TypedQuery<ArtworkEntity> typedQuery = this.getEntityManager().createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

}
