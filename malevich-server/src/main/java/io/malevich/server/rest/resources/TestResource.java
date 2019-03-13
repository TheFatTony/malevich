package io.malevich.server.rest.resources;


import io.malevich.server.twilio.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestResource {

//    @Autowired
//    private SmsService smsService;
//
//    @RequestMapping(value = "/smsService", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResponseEntity<Void> smsService() {
//        smsService.sendSms("+79258815879", "Привет от malevich.io");
//
//        return ResponseEntity.ok().build();
//    }

}
