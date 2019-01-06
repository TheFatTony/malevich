package io.malevich.server.services.wishlist;

import io.malevich.server.domain.TraderOrganizationEntity;
import io.malevich.server.domain.WishListEntity;
import io.malevich.server.repositories.wishlist.WishListDao;
import io.malevich.server.services.trader.TraderService;
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
    private TraderService traderService;

    @Override
    @Transactional
    public WishListEntity save(WishListEntity entity) {
        TraderOrganizationEntity traderEntity = traderService.getCurrentTrader();
        if (traderEntity == null)
            return null;

        entity.setTrader(traderEntity);
        return this.wishListDao.save(entity);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<WishListEntity> findAll(Pageable pageable) {
        TraderOrganizationEntity traderEntity = traderService.getCurrentTrader();
        if (traderEntity == null)
            return null;

        return this.wishListDao.findAll(pageable, traderEntity.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.wishListDao.deleteById(id);
    }
}
