package io.malevich.server.dao.paymentmethod;

import io.malevich.server.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaymentMethodDao extends JpaRepository<PaymentMethodEntity, Long> {

  List<PaymentMethodEntity> findAll();

}
