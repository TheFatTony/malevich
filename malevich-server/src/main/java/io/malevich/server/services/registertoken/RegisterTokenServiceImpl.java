package io.malevich.server.services.registertoken;

import io.malevich.server.repositories.registertoken.RegisterTokenDao;
import io.malevich.server.domain.RegisterTokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class RegisterTokenServiceImpl implements RegisterTokenService {

    @Autowired
    private RegisterTokenDao registerTokenDao;


    @Override
    @Transactional(readOnly = true)
    public List<RegisterTokenEntity> findAll() {
        return this.registerTokenDao.findAll();
    }

    @Override
    @Transactional
    public RegisterTokenEntity save(RegisterTokenEntity registerTokenEntity) {
        return registerTokenDao.save(registerTokenEntity);
    }

    @Override
    @Transactional
    public void delete(RegisterTokenEntity registerTokenEntity) {
        registerTokenDao.delete(registerTokenEntity);
    }

    @Override
    @Transactional
    public Optional<RegisterTokenEntity> findByToken(String token) {
        return registerTokenDao.findByToken(token);
    }

}
