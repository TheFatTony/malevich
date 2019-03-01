package io.malevich.server.twilio;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${malevich.twilio.account.sid}")
    private String accountSid;


    @Value("${malevich.twilio.auth.token}")
    private String authToken;


    @Value("${malevich.twilio.from.number}")
    private String fromNumber;

    protected SmsServiceImpl() {
    }

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendSms(String toNumber, String body) {
        Message message = Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                body)
                .create();
    }


}
