package io.malevich.server.services.bitcointransfers;


import io.malevich.server.domain.BitcoinTransfersEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BitcoinTransfers {

    BitcoinTransfersEntity save(BitcoinTransfersEntity bitcoinTransfersEntity);

    List<BitcoinTransfersEntity> findByStatus(String status);

    List<BitcoinTransfersEntity> findByStatusAndSenderAddress(String status, String senderAddress);
}
