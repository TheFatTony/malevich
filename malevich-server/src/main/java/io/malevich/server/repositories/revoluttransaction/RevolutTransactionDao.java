package io.malevich.server.repositories.revoluttransaction;

import io.malevich.server.domain.RevolutTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevolutTransactionDao extends JpaRepository<RevolutTransactionEntity, String> {

}
