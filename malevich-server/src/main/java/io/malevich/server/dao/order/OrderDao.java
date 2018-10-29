package io.malevich.server.dao.order;

import io.malevich.server.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAll();

    //  @Query("select oe from OrderEntity as oe join fetch oe.party as p join p.trader as t join p.gallery as g where g.id = :gallery_id")
    @Query("select oe from OrderEntity as oe")
    List<OrderEntity> findAllPlacedTraderOrders(@Param("gallery_id") Long galleryId);

}
