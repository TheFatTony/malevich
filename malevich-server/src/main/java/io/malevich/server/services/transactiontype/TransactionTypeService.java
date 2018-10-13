package io.malevich.server.services.transactiontype;

import io.malevich.server.entity.TransactionTypeEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface TransactionTypeService {

  List<TransactionTypeEntity> findAll();

}
