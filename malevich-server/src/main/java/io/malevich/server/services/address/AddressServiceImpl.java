package io.malevich.server.services.address;


import io.malevich.server.dao.address.AddressDao;
import io.malevich.server.entity.AddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressDao addressDao;

    @Override
    @Transactional(readOnly = true)
    public List<AddressEntity> findAll() {
        return this.addressDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AddressEntity find(Long id) {
        return this.addressDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressEntity> findByTraderId(Long traderId) {
        return null;/* this.addressDao.findByTreaderId(traderId);*/
    }

}
