package io.malevich.server.services.bitcointransfers;


import io.malevich.server.domain.BitcoinTransfersEntity;
import io.malevich.server.repositories.bitcointransfers.BitcoinTransfersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class BitcoinTransfersImpl implements BitcoinTransfers {

    @Autowired
    private BitcoinTransfersDao bitcoinTransfersDao;

    protected BitcoinTransfersImpl() {
    }


    @Override
    @Transactional
    public BitcoinTransfersEntity save(BitcoinTransfersEntity bitcoinTransfersEntity) {
        return bitcoinTransfersDao.save(bitcoinTransfersEntity);
    }

    @Override
    public List<BitcoinTransfersEntity> findByStatus(String status) {
        return bitcoinTransfersDao.findByStatus(status);
    }

    @Override
    public List<BitcoinTransfersEntity> findByStatusAndSenderAddress(String status, String senderAddress) {
        return bitcoinTransfersDao.findByStatusAndSenderAddress(status, senderAddress);
    }

}
