package io.malevich.server.repositories.ordertype;

import io.malevich.server.domain.OrderTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTypeDao extends JpaRepository<OrderTypeEntity, String> {

    List<OrderTypeEntity> findAll();
}
