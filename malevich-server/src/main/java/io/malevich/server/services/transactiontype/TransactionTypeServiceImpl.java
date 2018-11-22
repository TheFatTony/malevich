package io.malevich.server.services.transactiontype;

import io.malevich.server.domain.TransactionTypeEntity;
import io.malevich.server.repositories.transactiontype.TransactionTypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {


    private final Map<String, TransactionTypeEntity> values;
    private TransactionTypeDao transactionTypeDao;

    @Autowired
    public TransactionTypeServiceImpl(TransactionTypeDao transactionTypeDao) {
        this.transactionTypeDao = transactionTypeDao;

        values = transactionTypeDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionTypeEntity> findAll() {
        return this.transactionTypeDao.findAll();
    }

    @Override
    public TransactionTypeEntity getAddBalance() {
        return values.get("0001");
    }

    @Override
    public TransactionTypeEntity getCreateArtwork() {
        return values.get("0002");
    }

    @Override
    public TransactionTypeEntity getBid() {
        return values.get("0003");
    }

    @Override
    public TransactionTypeEntity getAsk() {
        return values.get("0004");
    }

    @Override
    public TransactionTypeEntity getBuySell() {
        return values.get("0005");
    }

    @Override
    public TransactionTypeEntity getCancelBid() {
        return values.get("0006");
    }

    @Override
    public TransactionTypeEntity getCancelAsk() {
        return values.get("0007");
    }

    @Override
    public TransactionTypeEntity getReturnBid() {
        return values.get("0008");
    }

    @Override
    public TransactionTypeEntity getReturnAsk() {
        return values.get("0009");
    }

    @Override
    public TransactionTypeEntity getWithdrawBalance() {
        return values.get("0010");
    }

}
