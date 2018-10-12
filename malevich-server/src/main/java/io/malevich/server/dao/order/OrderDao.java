package io.malevich.server.dao.order;

import io.malevich.server.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

  List<OrderEntity> findAll();

}
