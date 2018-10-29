package io.malevich.server.dao.paymentmethodtype;

import io.malevich.server.entity.PaymentMethodTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaymentMethodTypeDao extends JpaRepository<PaymentMethodTypeEntity, String> {

  List<PaymentMethodTypeEntity> findAll();

}
