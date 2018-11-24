package io.malevich.server.fabric.services;

import io.malevich.server.domain.TransactionEntity;
import org.springframework.stereotype.Service;

@Service
public interface ComposerService {

     void submitTransction(TransactionEntity transaction);

}
