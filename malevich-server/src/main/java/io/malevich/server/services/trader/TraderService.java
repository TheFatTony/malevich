package io.malevich.server.services.trader;

import io.malevich.server.domain.TraderEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface TraderService {

    List<TraderEntity> findAll();

    TraderEntity find(Long id);

    TraderEntity findByUserName(String name);

    TraderEntity update(TraderEntity trader);

    TraderEntity save(TraderEntity traderEntity);

    TraderEntity getCurrentTrader();
}
