package io.malevich.server.dao.transaction;

import io.malevich.server.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TransactionDao extends JpaRepository<TransactionEntity, Long> {

  List<TransactionEntity> findAll();

}
