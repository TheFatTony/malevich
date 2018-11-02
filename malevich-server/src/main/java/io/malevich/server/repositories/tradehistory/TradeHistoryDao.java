package io.malevich.server.repositories.tradehistory;

import io.malevich.server.domain.TradeHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TradeHistoryDao extends JpaRepository<TradeHistoryEntity, Long> {

    List<TradeHistoryEntity> findAll();

    @Query("select the from TradeHistoryEntity as the where the.artworkStock.id = :artwork_id order by the.effectiveDate")
    List<TradeHistoryEntity> findAllByArtworkId(@Param("artwork_id") Long artworkId);


}
