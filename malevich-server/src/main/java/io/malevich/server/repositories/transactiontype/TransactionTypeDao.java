package io.malevich.server.repositories.transactiontype;


import io.malevich.server.domain.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionTypeDao extends JpaRepository<TransactionTypeEntity, String> {


    List<TransactionTypeEntity> findAll();


}
