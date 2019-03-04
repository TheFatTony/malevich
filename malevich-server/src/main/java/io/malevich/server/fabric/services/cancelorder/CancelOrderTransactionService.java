package io.malevich.server.fabric.services.cancelorder;

import com.yinyang.core.server.fabric.GenericComposerService;
import io.malevich.server.domain.OrderEntity;
import org.springframework.stereotype.Service;

@Service
public interface CancelOrderTransactionService extends GenericComposerService<OrderEntity> {

}
