package io.malevich.server.services.sms;


import org.springframework.stereotype.Service;

@Service
public interface SmsService {


    void sendSms(String toNumber, String body);
}
