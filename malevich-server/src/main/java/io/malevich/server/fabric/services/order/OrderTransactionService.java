package io.malevich.server.fabric.services.order;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.fabric.model.OrderTransaction;
import io.malevich.server.fabric.model.PaymentTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderTransactionService extends GenericComposerService<OrderEntity> {

    List<OrderTransaction> list();
}
