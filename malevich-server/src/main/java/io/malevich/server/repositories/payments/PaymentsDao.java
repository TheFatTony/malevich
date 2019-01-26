package io.malevich.server.repositories.payments;

import io.malevich.server.domain.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentsDao extends JpaRepository<PaymentsEntity, Long> {


    List<PaymentsEntity> findAllByParticipant_Id(Long partyId);

}
