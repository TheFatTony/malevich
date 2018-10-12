package io.malevich.server.services.transaction;

import io.malevich.server.dao.transaction.TransactionDao;
import io.malevich.server.entity.TransactionEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionDao transactionDao;


  @Override
  @Transactional(readOnly = true)
  public List<TransactionEntity> findAll() {
        return this.transactionDao.findAll();
  }

}
