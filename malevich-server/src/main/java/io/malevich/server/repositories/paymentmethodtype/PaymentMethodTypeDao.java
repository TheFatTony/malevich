package io.malevich.server.repositories.paymentmethodtype;

import io.malevich.server.domain.PaymentMethodTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentMethodTypeDao extends JpaRepository<PaymentMethodTypeEntity, String> {

    List<PaymentMethodTypeEntity> findAll();

}
