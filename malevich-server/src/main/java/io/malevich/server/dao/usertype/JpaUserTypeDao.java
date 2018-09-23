package io.malevich.server.dao.usertype;

import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.UserTypeEntity;
import org.springframework.stereotype.Component;


@Component
public class JpaUserTypeDao extends JpaDao<UserTypeEntity, Long> implements UserTypeDao {

    public JpaUserTypeDao() {
        super(UserTypeEntity.class);
    }

}
