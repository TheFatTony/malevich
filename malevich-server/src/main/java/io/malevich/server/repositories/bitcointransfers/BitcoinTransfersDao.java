package io.malevich.server.repositories.bitcointransfers;

import io.malevich.server.domain.BitcoinTransfersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BitcoinTransfersDao extends JpaRepository<BitcoinTransfersEntity, Long> {

    List<BitcoinTransfersEntity> findByStatus(String status);

    List<BitcoinTransfersEntity> findByStatusAndSenderAddress(String status, String senderAddress);

}
