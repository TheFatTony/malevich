package io.malevich.server.services.transaction;

import io.malevich.server.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TransactionService {

    List<TransactionEntity> findAll();

    void createArtworkStock(ArtworkStockEntity artworkStockEntity);

    void placeAsk(OrderEntity orderEntity);

    void addAccountStates(PaymentsEntity paymentsEntity);

    void placeBid(OrderEntity orderEntity);

    void cancelBid(OrderEntity orderEntity);

    void cancelAsk(OrderEntity orderEntity);

    void buySell(TradeHistoryEntity tradeHistoryEntity);
}
