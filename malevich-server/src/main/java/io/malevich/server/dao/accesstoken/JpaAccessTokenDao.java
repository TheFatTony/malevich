package io.malevich.server.dao.accesstoken;

import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.AccessTokenEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class JpaAccessTokenDao extends JpaDao<AccessTokenEntity, Long> implements AccessTokenDao {
	public JpaAccessTokenDao() {
		super(AccessTokenEntity.class);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = NoResultException.class)
	public AccessTokenEntity findByToken(String accessTokenString) {
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AccessTokenEntity> query = builder.createQuery(this.entityClass);
		Root<AccessTokenEntity> root = query.from(this.entityClass);
		query.where(builder.equal(root.get("token"), accessTokenString));

		try {
			return this.getEntityManager().createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
