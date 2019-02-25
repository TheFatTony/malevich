package io.malevich.server.repositories.revolutwithdrawalhistory;

import io.malevich.server.domain.RevolutWithdrawalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevolutWithdrawalHistoryDao extends JpaRepository<RevolutWithdrawalHistoryEntity, Long> {

}

