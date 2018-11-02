package io.malevich.server.services.transactiontype;

import io.malevich.server.repositories.transactiontype.TransactionTypeDao;
import io.malevich.server.domain.TransactionTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {

    @Autowired
    private TransactionTypeDao transactionTypeDao;


    @Override
    @Transactional(readOnly = true)
    public List<TransactionTypeEntity> findAll() {
        return this.transactionTypeDao.findAll();
    }

    @Override
    @Transactional
    public Optional<TransactionTypeEntity> findById(String id) {
        return transactionTypeDao.findById(id);
    }

}
