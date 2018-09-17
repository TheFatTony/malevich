package io.malevich.server.dao.user;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class JpaUserDao extends JpaDao<UserEntity, Long> implements UserDao {
    public JpaUserDao() {
        super(UserEntity.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity loadUserByUsername(String username) {
        UserEntity user = this.findByName(username);
        if (null == user) {
            throw new UsernameNotFoundException("The user with name " + username + " was not found");
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByName(String name) {
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<UserEntity> criteriaQuery = builder.createQuery(this.entityClass);

        Root<UserEntity> root = criteriaQuery.from(this.entityClass);
        Path<String> namePath = root.get("name");
        criteriaQuery.where(builder.equal(namePath, name));

        TypedQuery<UserEntity> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        List<UserEntity> users = typedQuery.getResultList();

        if (users.isEmpty()) {
            return null;
        }

        return users.iterator().next();
    }
}
