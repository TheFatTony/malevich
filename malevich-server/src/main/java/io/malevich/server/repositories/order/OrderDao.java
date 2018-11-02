package io.malevich.server.repositories.order;

import io.malevich.server.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAll();

    @Query("select oe from OrderEntity as oe join fetch oe.party as p join p.gallery as g where g.id = :gallery_id")
    List<OrderEntity> findAllPlacedGalleryOrders(@Param("gallery_id") Long galleryId);

    @Query("select oe from OrderEntity as oe join fetch oe.party as p join p.trader as t where t.id = :trader_id")
    List<OrderEntity> findAllPlacedTraderOrders(@Param("trader_id") Long traderId);

    @Query("select oe from OrderEntity as oe join fetch oe.artworkStock as a join a.artwork as t where t.id = :artwork_id order by oe.effectiveDate")
    List<OrderEntity> findAllOrdersByArtworkId(@Param("artwork_id") Long artworkId);

    @Query("select oe from OrderEntity as oe join fetch oe.artworkStock where oe.artworkStock.id = :artworkStock_id and oe.type.id = 'ASK' and oe.amount = :amount")
    OrderEntity findCounterOrder(@Param("artworkStock_id") Long artworkStockId, @Param("amount") Double amount);

}
