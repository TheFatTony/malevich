package io.malevich.server.rest.resources;


import io.malevich.server.scheduling.*;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminResource {


    @Autowired
    private MailQueueTask mailQueueTask;

    @Autowired
    private BitcoinBalanceCheck bitcoinBalanceCheck;

    @Autowired
    private MarketOrdersCheck marketOrdersCheck;

    @Autowired
    private RevolutDepositCheck revolutDepositCheck;

    @Autowired
    private SmsQueueTask smsQueueTask;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/scheduling/sendAllMail", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> sendAllMail() {
        mailQueueTask.sendAllMail();

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/scheduling/checkBalance", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> checkBalance() {
        try {
            bitcoinBalanceCheck.checkBalance();
        } catch (UnreadableWalletException | InterruptedException | InsufficientMoneyException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/scheduling/marketOrdersCheck", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> marketOrdersCheck() {
        marketOrdersCheck.checkExecution();

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/scheduling/revolutDepositCheck", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> revolutDepositCheck() {
        revolutDepositCheck.checkTransactions();

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/scheduling/sendAllMessages", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Void> sendAllMessages() {
        smsQueueTask.sendAllMessages();

        return ResponseEntity.ok().build();
    }

}
