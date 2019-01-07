package io.malevich.server.services.wishlist;

import io.malevich.server.domain.CounterpartyEntity;
import io.malevich.server.domain.WishListEntity;
import io.malevich.server.repositories.wishlist.WishListDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishListDao wishListDao;

    @Autowired
    private CounterpartyService counterpartyService;

    @Override
    @Transactional
    public WishListEntity save(WishListEntity entity) {
participant
        CounterpartyEntity currentCounterparty = counterpartyService.getCurrent();
        if (currentCounterparty == null)
            return null;

        entity.setCounterparty(currentCounterparty);
        return this.wishListDao.save(entity);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<WishListEntity> findAll(Pageable pageable) {
participant
        CounterpartyEntity currentCounterparty = counterpartyService.getCurrent();
        if (currentCounterparty == null)
            return null;

        return this.wishListDao.findByCounterparty_Id(pageable, currentCounterparty.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.wishListDao.deleteById(id);
    }
}
