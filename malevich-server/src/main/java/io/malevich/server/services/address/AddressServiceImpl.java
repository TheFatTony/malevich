package io.malevich.server.services.address;


import io.malevich.server.repositories.address.AddressDao;
import io.malevich.server.domain.AddressEntity;
import io.malevich.server.services.trader.TraderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressDao addressDao;

    @Autowired
    private TraderService traderService;

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

//    @Override
//    @Transactional(readOnly = true)
//    public List<AddressEntity> findByTraderId(Long traderId) {
//        return this.addressDao.findByTreaderId(traderId);
//    }


//    @Override
//    @Transactional
//    public AddressEntity create(AddressEntity addressEntity) {
    // TODO restore
    //  TraderEntity trader = traderService.getCurrentTrader();
    //  addressEntity.setTrader(trader);
//        return this.addressDao.save(addressEntity);
//    }

}
