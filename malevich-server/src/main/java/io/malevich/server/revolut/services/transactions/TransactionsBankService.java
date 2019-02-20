package io.malevich.server.revolut.services.transactions;

import io.malevich.server.revolut.GenericBankService;
import io.malevich.server.revolut.model.TransactionModel;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface TransactionsBankService extends GenericBankService {

    List<TransactionModel> getTransactions(Timestamp from, Timestamp to);
}
