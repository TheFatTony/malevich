package io.malevich.server.services.transactiontype;

import io.malevich.server.dao.transactiontype.TransactionTypeDao;
import io.malevich.server.entity.TransactionTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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
