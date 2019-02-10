package io.malevich.server.repositories.bitcointransfers;

import io.malevich.server.domain.BitcoinTransfersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinTransfersDao extends JpaRepository<BitcoinTransfersEntity, Long> {


}
