package io.malevich.server.services.tradehistory;

import io.malevich.server.entity.OrderEntity;
import io.malevich.server.entity.TradeHistoryEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface TradeHistoryService {

  List<TradeHistoryEntity> findAll();

  List<TradeHistoryEntity> findAllByArtworkId(Long artworkId);

  TradeHistoryEntity create(OrderEntity askOrder, OrderEntity bidOrder);

}
