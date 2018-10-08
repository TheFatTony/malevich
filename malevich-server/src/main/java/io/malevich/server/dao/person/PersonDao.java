package io.malevich.server.dao.person;

import io.malevich.server.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository<PersonEntity, Long> {

}