package io.malevich.server.services.tradehistory;

import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.TradeHistoryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface TradeHistoryService {

    List<TradeHistoryEntity> findAll();

    List<TradeHistoryEntity> findAllByArtworkId(Long artworkId);

    TradeHistoryEntity create(OrderEntity askOrder, OrderEntity bidOrder, Double amount);
}
