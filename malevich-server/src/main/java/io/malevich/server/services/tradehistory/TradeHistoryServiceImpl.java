package io.malevich.server.services.tradehistory;

import io.malevich.server.repositories.tradehistory.TradeHistoryDao;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.TradeHistoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Slf4j
@Service
public class TradeHistoryServiceImpl implements TradeHistoryService {

    @Autowired
    private TradeHistoryDao tradeHistoryDao;


    @Override
    @Transactional(readOnly = true)
    public List<TradeHistoryEntity> findAll() {
        return this.tradeHistoryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TradeHistoryEntity> findAllByArtworkId(Long artworkId) {
        return tradeHistoryDao.findAllByArtworkId(artworkId);
    }

    @Override
    @Transactional
    public TradeHistoryEntity create(OrderEntity askOrder, OrderEntity bidOrder, Double amount) {
        TradeHistoryEntity tradeHistoryEntity = new TradeHistoryEntity();
        tradeHistoryEntity.setQuantity((long) 1);
        tradeHistoryEntity.setAmount(amount);
        tradeHistoryEntity.setArtworkStock(askOrder.getArtworkStock());
        tradeHistoryEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        tradeHistoryEntity.setAskOrder(askOrder);
        tradeHistoryEntity.setBidOrder(bidOrder);
        return tradeHistoryDao.save(tradeHistoryEntity);
    }

}
