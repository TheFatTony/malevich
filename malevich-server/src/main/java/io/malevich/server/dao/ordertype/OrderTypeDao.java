package io.malevich.server.dao.ordertype;

import io.malevich.server.entity.OrderTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTypeDao extends JpaRepository<OrderTypeEntity, Long> {

    List<OrderTypeEntity> findAll();
}
