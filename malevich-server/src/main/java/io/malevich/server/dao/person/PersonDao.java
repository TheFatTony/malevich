package io.malevich.server.dao.person;

import io.malevich.server.dao.Dao;
import io.malevich.server.entity.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PersonDao extends Dao<PersonEntity, Long> {

    List<PersonEntity> findAll();

    PersonEntity find(Long id);
}