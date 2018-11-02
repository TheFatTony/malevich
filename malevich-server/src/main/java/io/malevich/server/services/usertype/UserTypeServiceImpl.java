package io.malevich.server.services.usertype;

import io.malevich.server.repositories.usertype.UserTypeDao;
import io.malevich.server.domain.UserTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class UserTypeServiceImpl implements UserTypeService {

    @Autowired
    private UserTypeDao userTypeDao;


    @Override
    @Transactional(readOnly = true)
    public List<UserTypeEntity> findAll() {
        return this.userTypeDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserTypeEntity getOne(Long id) {
        return userTypeDao.getOne(id);
    }

}
