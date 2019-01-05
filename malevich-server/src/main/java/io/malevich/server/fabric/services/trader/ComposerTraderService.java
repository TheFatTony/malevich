package io.malevich.server.fabric.services.trader;

import io.malevich.server.domain.TransactionEntity;
import org.springframework.stereotype.Service;

@Service
public interface ComposerTraderService {

     void submitTransction(TransactionEntity transaction);

}
