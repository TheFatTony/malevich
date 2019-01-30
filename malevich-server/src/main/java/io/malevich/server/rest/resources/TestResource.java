package io.malevich.server.rest.resources;


import io.malevich.server.services.trader.TraderService;
import io.malevich.server.transfer.TraderPersonDto;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;


@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestResource {

    @Autowired
    private TraderService traderService;


    @RequestMapping(value = "/wallet", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> wallet() {
        NetworkParameters params = NetworkParameters.testNet2();
        Wallet wallet = new Wallet(params);
        BlockChain chain = null;
        try {
            chain = new BlockChain(params, wallet, new MemoryBlockStore(params));
            PeerGroup peerGroup = new PeerGroup(params, chain);
            peerGroup.addWallet(wallet);
            peerGroup.start();
            Address a = wallet.currentReceiveAddress();
            ECKey b = wallet.currentReceiveKey();
            Address c = wallet.freshReceiveAddress();

            assert b.toAddress(wallet.getParams()).equals(a);
            assert !c.equals(a);

            return ResponseEntity.ok().body(a.toString());


        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @PreAuthorize("hasRole('ROLE_TRADER')")
//    @RequestMapping(value = "/update", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResponseEntity<Void> update(@RequestBody TraderPersonDto trader) {
//        TraderPersonEntity newTraderEntity = convertToEntity(trader);
//        this.traderService.update(newTraderEntity);
//        return ResponseEntity.ok().build();
//    }

}
