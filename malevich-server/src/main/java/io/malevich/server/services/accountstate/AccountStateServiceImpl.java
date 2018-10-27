package io.malevich.server.services.accountstate;

import io.malevich.server.dao.accountstate.AccountStateDao;
import io.malevich.server.entity.AccountStateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class AccountStateServiceImpl implements AccountStateService {

    @Autowired
    private AccountStateDao accountStateDao;


    @Override
    @Transactional(readOnly = true)
    public List<AccountStateEntity> findAll() {
        return this.accountStateDao.findAll();
    }

    @Override
    public AccountStateEntity findByArtworkStockAndParty(Long artworkStockId, Long counterpartyId) {
        return accountStateDao.findByArtworkStock_IdAndParty_Id(artworkStockId, counterpartyId);
    }


}
