package io.malevich.server.services.transaction;

import io.malevich.server.entity.ArtworkStockEntity;
import io.malevich.server.entity.OrderEntity;
import io.malevich.server.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TransactionService {

    List<TransactionEntity> findAll();

    void createArtworkStock(ArtworkStockEntity artworkStockEntity);

    void placeBid(OrderEntity orderEntity);

}
