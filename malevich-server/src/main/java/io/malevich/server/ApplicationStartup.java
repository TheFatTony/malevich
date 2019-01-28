package io.malevich.server;

import io.malevich.server.fabric.services.testdata.TestDataTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private TestDataTransactionService testDataTransactionService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            testDataTransactionService.create(new Object());
        } catch (Exception e) {
        }
    }
}
