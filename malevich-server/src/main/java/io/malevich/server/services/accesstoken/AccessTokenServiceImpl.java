package io.malevich.server.services.accesstoken;

import io.malevich.server.dao.accesstoken.AccessTokenDao;
import io.malevich.server.entity.AccessTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private AccessTokenDao accessTokenDao;


    @Override
    @Transactional(readOnly = true)
    public List<AccessTokenEntity> findAll() {
        return this.accessTokenDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AccessTokenEntity findByToken(String accessToken) {
        return accessTokenDao.findByToken(accessToken);
    }

    @Override
    @Transactional
    public void delete(AccessTokenEntity accessTokenEntity) {
        accessTokenDao.delete(accessTokenEntity);
    }

}
