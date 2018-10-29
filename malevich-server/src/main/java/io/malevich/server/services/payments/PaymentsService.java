package io.malevich.server.services.payments;

import io.malevich.server.entity.PaymentsEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface PaymentsService {

  List<PaymentsEntity> findAll();

  void insert(PaymentsEntity paymentsEntity);

}
