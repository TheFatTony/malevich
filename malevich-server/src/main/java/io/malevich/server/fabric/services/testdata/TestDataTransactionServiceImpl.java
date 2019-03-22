package io.malevich.server.fabric.services.testdata;

import com.yinyang.core.server.fabric.GenericComposerServiceImpl;
import io.malevich.server.domain.OrderEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.fabric.model.OrderConcept;
import io.malevich.server.fabric.model.OrderTransaction;
import io.malevich.server.fabric.model.TestDataTransaction;
import io.malevich.server.services.participant.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.UUID;


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
