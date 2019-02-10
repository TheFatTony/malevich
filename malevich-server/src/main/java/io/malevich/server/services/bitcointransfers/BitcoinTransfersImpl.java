package io.malevich.server.services.bitcointransfers;


import io.malevich.server.repositories.bitcointransfers.BitcoinTransfersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BitcoinTransfersImpl implements BitcoinTransfers {

    @Autowired
    private BitcoinTransfersDao bitcoinTransfersDao;

    protected BitcoinTransfersImpl() {
    }


}
