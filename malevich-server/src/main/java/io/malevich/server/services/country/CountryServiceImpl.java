package io.malevich.server.services.country;


import io.malevich.server.dao.country.CountryDao;
import io.malevich.server.entity.CountryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CountryServiceImpl implements CountryService {


    @Autowired
    private CountryDao countryDao;

    @Override
    @Transactional(readOnly = true)
    public List<CountryEntity> findAll() {
        return this.countryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CountryEntity find(Long id) {
        return this.countryDao.findById(id).get();
    }

}
