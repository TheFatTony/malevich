package io.malevich.server.services.person;


import io.malevich.server.repositories.person.PersonDao;
import io.malevich.server.domain.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class PersonServiceImpl implements PersonService {


    @Autowired
    private PersonDao personDao;

    protected PersonServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonEntity> findAll() {
        return this.personDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PersonEntity find(Long id) {
        return this.personDao.findById(id).get();
    }

    @Override
    @Transactional
    public PersonEntity save(PersonEntity personEntity) {
        return personDao.save(personEntity);
    }

}
