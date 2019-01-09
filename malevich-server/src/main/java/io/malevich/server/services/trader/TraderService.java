package io.malevich.server.services.trader;

import io.malevich.server.domain.TraderPersonEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TraderService {

    List<TraderPersonEntity> findAll();

    TraderPersonEntity find(Long id);

    TraderPersonEntity update(TraderPersonEntity trader);

    TraderPersonEntity save(TraderPersonEntity traderEntity);

    TraderPersonEntity getCurrentTrader();
}
