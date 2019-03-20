package io.malevich.server;

import io.malevich.server.fabric.services.testdata.TestDataTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {


    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private TestDataTransactionService testDataTransactionService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            testDataTransactionService.create(activeProfile);
        } catch (Exception e) {
        }
    }
}
