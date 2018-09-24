package io.malevich.server.dao.gallery;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.GalleryEntity;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class JpaGalleryDao extends JpaDao<GalleryEntity, Long> implements GalleryDao {


    public JpaGalleryDao() {
        super(GalleryEntity.class);
    }


    @Override
    public List<GalleryEntity> findAll() {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<GalleryEntity> criteriaQuery = builder.createQuery(this.entityClass);

        Root<GalleryEntity> root = criteriaQuery.from(this.entityClass);
        root.fetch("organization", JoinType.INNER);
        root.fetch("thumbnail", JoinType.LEFT);

        criteriaQuery.select(root);
        TypedQuery<GalleryEntity> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
