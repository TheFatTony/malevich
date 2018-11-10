package io.malevich.server.services.payments;

import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.exceptions.AccountStateException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PaymentsService {

    List<PaymentsEntity> findAll();

    void insert(PaymentsEntity paymentsEntity) throws AccountStateException;

}
