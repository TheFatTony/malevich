package io.malevich.server.services.parameter;

import io.malevich.server.domain.ParameterEntity;
import io.malevich.server.repositories.parameter.ParameterDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterDao parameterDao;

    @Override
    public List<ParameterEntity> findAll() {
        return parameterDao.findAll();
    }
}
