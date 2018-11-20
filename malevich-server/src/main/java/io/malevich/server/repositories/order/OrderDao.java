package io.malevich.server.repositories.order;

import io.malevich.server.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAll();

    @Query("select oe from OrderEntity as oe where oe.status.id = 'OPEN'")
    List<OrderEntity> findAllOpen();

    @Query("select oe from OrderEntity as oe where oe.status.id = 'OPEN' and oe.expirationDate <= :date")
    List<OrderEntity> findOldOpen(@Param("date") Timestamp date);

    @Query("select oe from OrderEntity as oe where oe.party.id = :party_id and oe.status.id = 'OPEN'")
    List<OrderEntity> findAllPlacedOrdersByParty(@Param("party_id") Long partyId);

    @Query("select oe from OrderEntity as oe join fetch oe.party as p join p.gallery as g where g.id = :gallery_id and oe.status.id = 'OPEN'")
    List<OrderEntity> findAllPlacedGalleryOrders(@Param("gallery_id") Long galleryId);

    @Query("select oe from OrderEntity as oe join fetch oe.party as p join p.trader as t where t.id = :trader_id and oe.status.id = 'OPEN'")
    List<OrderEntity> findAllPlacedTraderOrders(@Param("trader_id") Long traderId);

    @Query("select oe from OrderEntity as oe where oe.artworkStock.id = :artwork_stock_id and oe.status.id = 'OPEN' order by oe.effectiveDate")
    List<OrderEntity> findAllOrdersByArtworkStockId(@Param("artwork_stock_id") Long artworkStockId);

}
