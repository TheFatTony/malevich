package io.malevich.server.fabric.services.testdata;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.fabric.model.TestDataTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TestDataTransactionServiceImpl extends GenericComposerServiceImpl<Object> implements TestDataTransactionService {



    public TestDataTransactionServiceImpl() {
        super("TestData");
    }

    @Override
    public void create(Object entity) {
        TestDataTransaction testDataTransaction = new TestDataTransaction();
        testDataTransaction.setTestData(entity.toString());
        doPost(testDataTransaction);
    }

}
