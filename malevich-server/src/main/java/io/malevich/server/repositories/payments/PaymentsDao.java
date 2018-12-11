package io.malevich.server.repositories.payments;

import io.malevich.server.domain.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentsDao extends JpaRepository<PaymentsEntity, Long> {

    @Query("select pe from PaymentsEntity pe where pe.party.trader.id =:id")
    List<PaymentsEntity> findAll(@Param("id") Long id);

    @Query("select pe from PaymentsEntity pe where pe.id=:id")
    PaymentsEntity findBy(@Param("id") Long id);
}
