package io.malevich.server.services.exchangeorder;

import io.malevich.server.domain.ExchangeOrderEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface ExchangeOrderService {

  List<ExchangeOrderEntity> findAll();

}
