package io.malevich.server.repositories.person;

import io.malevich.server.domain.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository<PersonEntity, Long> {

}