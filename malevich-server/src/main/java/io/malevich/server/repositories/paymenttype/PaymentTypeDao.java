package io.malevich.server.repositories.paymenttype;

import io.malevich.server.domain.PaymentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaymentTypeDao extends JpaRepository<PaymentTypeEntity, Long> {

  List<PaymentTypeEntity> findAll();

}
