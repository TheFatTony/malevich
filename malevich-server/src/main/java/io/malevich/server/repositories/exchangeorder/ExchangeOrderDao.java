package io.malevich.server.repositories.exchangeorder;

import io.malevich.server.domain.ExchangeOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExchangeOrderDao extends JpaRepository<ExchangeOrderEntity, Long> {

  List<ExchangeOrderEntity> findAll();


  @Override
  Optional<ExchangeOrderEntity> findById(Long aLong);

  Optional<ExchangeOrderEntity> findByOrderId(String orderId);

}
