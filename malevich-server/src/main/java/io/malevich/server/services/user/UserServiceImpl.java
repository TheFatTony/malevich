package io.malevich.server.services.user;


import io.malevich.server.repositories.user.UserDao;
import io.malevich.server.domain.AccessTokenEntity;
import io.malevich.server.domain.UserEntity;
import io.malevich.server.services.accesstoken.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccessTokenService accessTokenService;


    protected UserServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        UserDetails ud = this.userDao.findByName(username);
        if (ud == null)
            throw new UsernameNotFoundException("User not found");
        return ud;
    }

    @Transactional
    public UserEntity findUserByAccessToken(String accessTokenString) {
        AccessTokenEntity accessTokenEntity = this.accessTokenService.findByToken(accessTokenString);

        if (null == accessTokenEntity) {
            return null;
        }

        if (accessTokenEntity.isExpired()) {
            this.accessTokenService.delete(accessTokenEntity);
            return null;
        }

        return accessTokenEntity.getUser();
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
