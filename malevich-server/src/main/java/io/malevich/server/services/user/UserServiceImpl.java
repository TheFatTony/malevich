package io.malevich.server.services.user;


import io.malevich.server.dao.accesstoken.AccessTokenDao;
import io.malevich.server.dao.user.UserDao;
import io.malevich.server.entity.AccessTokenEntity;
import io.malevich.server.entity.UserEntity;
import io.malevich.server.rest.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private JWTUtil jwtUtil;


    protected UserServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return this.userDao.loadUserByUsername(username);
    }

    @Override
    @Transactional
    public UserEntity findUserByAccessToken(String accessTokenString) {
        AccessTokenEntity accessTokenEntity = this.accessTokenDao.findByToken(accessTokenString);

        if (null == accessTokenEntity) {
            return null;
        }

        if (accessTokenEntity.isExpired()) {
            this.accessTokenDao.delete(accessTokenEntity);
            return null;
        }

        return accessTokenEntity.getUser();
    }

    @Override
    @Transactional
    public AccessTokenEntity createAccessToken(UserEntity user) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(user, jwtUtil.generateToken(user.getUsername()));
        return accessTokenEntity;
    }

    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }
}
