package io.malevich.server.dao.person;


import io.malevich.server.dao.JpaDao;
import io.malevich.server.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaPersonDao extends JpaDao<PersonEntity, Long> implements PersonDao {


    public JpaPersonDao() {
        super(PersonEntity.class);
    }


}