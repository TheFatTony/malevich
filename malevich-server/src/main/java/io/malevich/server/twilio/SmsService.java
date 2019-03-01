package io.malevich.server.twilio;


import org.springframework.stereotype.Service;

@Service
public interface SmsService {


    void sendSms(String toNumber, String body);
}
