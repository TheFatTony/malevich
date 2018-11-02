package io.malevich.server.services.gender;


import io.malevich.server.repositories.gender.GenderDao;
import io.malevich.server.domain.GenderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class GenderServiceImpl implements GenderService {


    @Autowired
    private GenderDao genderDao;

    protected GenderServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenderEntity> findAll() {
        return this.genderDao.findAll();
    }

}
