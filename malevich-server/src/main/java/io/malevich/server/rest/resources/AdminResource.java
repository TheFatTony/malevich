package io.malevich.server.rest.resources;


import io.malevich.server.scheduling.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminResource {


    @Autowired(required = false)
    private MailQueueTask mailQueueTask;

    @Autowired(required = false)
    private BitcoinBalanceCheck bitcoinBalanceCheck;

    @Autowired(required = false)
    private MarketOrdersCheck marketOrdersCheck;

    @Autowired(required = false)
    private RevolutDepositCheck revolutDepositCheck;

    @Autowired(required = false)
    private SmsQueueTask smsQueueTask;

    @RequestMapping(value = "/scheduling/sendAllMail", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> sendAllMail() {
        mailQueueTask.sendAllMail();

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/scheduling/checkBalance", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> checkBalance() {
        bitcoinBalanceCheck.checkBalance();

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/scheduling/marketOrdersCheck", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> marketOrdersCheck() {
        marketOrdersCheck.checkExecution();

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/scheduling/revolutDepositCheck", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> revolutDepositCheck() {
        revolutDepositCheck.checkTransactions();

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/scheduling/sendAllMessages", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> sendAllMessages() {
        smsQueueTask.sendAllMessages();

        return ResponseEntity.ok().build();
    }

}
