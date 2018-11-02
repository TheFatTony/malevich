package io.malevich.server.services.person;


import io.malevich.server.domain.PersonEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    List<PersonEntity> findAll();

    PersonEntity find(Long id);

    PersonEntity save(PersonEntity personEntity);
}
