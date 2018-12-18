package io.malevich.server.services.trader;


import io.malevich.server.domain.*;
import io.malevich.server.repositories.trader.TraderDao;
import io.malevich.server.services.counterparty.CounterpartyService;
import io.malevich.server.services.counterpartytype.CounterpartyTypeService;
import io.malevich.server.services.delayedchange.DelayedChangeService;
import io.malevich.server.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class TraderServiceImpl implements TraderService {

    @Autowired
    private TraderDao traderDao;


    @Override
    @Transactional(readOnly = true)
    public List<TraderEntity> findAll() {
        return traderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TraderEntity find(Long id) {
        return traderDao.findById(id).orElse(null);
    }
}
