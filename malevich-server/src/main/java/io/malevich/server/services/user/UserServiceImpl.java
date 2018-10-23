package io.malevich.server.services.user;


import io.malevich.server.dao.user.UserDao;
import io.malevich.server.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    protected UserServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        return userDao.save(userEntity);
    }

    @Override
    @Transactional
    public UserEntity findByName(String name) {
        return userDao.findByName(name);
    }
}
