package io.malevich.server.repositories.transactiongroup;

import io.malevich.server.domain.TransactionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TransactionGroupDao extends JpaRepository<TransactionGroupEntity, Long> {

  List<TransactionGroupEntity> findAll();

}
