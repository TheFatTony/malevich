package io.malevich.server.dao.artwork;

import io.malevich.server.entity.ArtworkEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomArtworkDaoImpl implements CustomArtworkDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ArtworkEntity> findAll() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<ArtworkEntity> criteriaQuery = builder.createQuery(ArtworkEntity.class);

        Root<ArtworkEntity> root = criteriaQuery.from(ArtworkEntity.class);
        root.fetch("category", JoinType.INNER);
        root.fetch("thumbnail", JoinType.LEFT);
        root.fetch("image", JoinType.LEFT);

        criteriaQuery.select(root);

        TypedQuery<ArtworkEntity> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}
