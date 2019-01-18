package io.malevich.server.fabric.services.payment;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.fabric.model.PaymentTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentTransactionService extends GenericComposerService<PaymentsEntity> {

    List<PaymentTransaction> list();
}
