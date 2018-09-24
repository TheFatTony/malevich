package io.malevich.server.dao.lobstorage;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.LobStorageEntity;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Component
public class JpaLobStorageDao extends JpaDao<LobStorageEntity, Long> implements LobStorageDao {

    public JpaLobStorageDao() {
        super(LobStorageEntity.class);
    }

    @Override
    public LobStorageEntity findByFileId(Long fileId) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<LobStorageEntity> criteriaQuery = builder.createQuery(this.entityClass);


        Root<LobStorageEntity> root = criteriaQuery.from(this.entityClass);
        root.fetch("file", JoinType.INNER);
        Predicate p1 = builder.and(builder.equal(root.get("file"), fileId));

        criteriaQuery.select(root);
        criteriaQuery.where(p1);

        TypedQuery<LobStorageEntity> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }
}