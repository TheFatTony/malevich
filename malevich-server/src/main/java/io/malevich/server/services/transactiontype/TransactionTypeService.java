package io.malevich.server.services.transactiontype;

import io.malevich.server.domain.TransactionTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface TransactionTypeService {

    List<TransactionTypeEntity> findAll();

    Optional<TransactionTypeEntity> findById(String id);
}
