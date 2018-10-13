package io.malevich.server.services.transaction;

import io.malevich.server.entity.TransactionEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface TransactionService {

  List<TransactionEntity> findAll();

}
