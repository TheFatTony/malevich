package io.malevich.server.repositories.orderstatus;

import io.malevich.server.domain.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderStatusDao extends JpaRepository<OrderStatusEntity, String> {

  List<OrderStatusEntity> findAll();

}
