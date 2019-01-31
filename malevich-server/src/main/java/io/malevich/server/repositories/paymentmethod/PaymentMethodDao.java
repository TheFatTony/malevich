package io.malevich.server.repositories.paymentmethod;

import io.malevich.server.domain.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentMethodDao extends JpaRepository<PaymentMethodEntity, Long> {

    List<PaymentMethodEntity> findAll();

    List<PaymentMethodEntity> findByParticipant_Id(Long participantId);

    List<PaymentMethodEntity> findByParticipant_IdAndType_Id(Long participantId, String typeId);

}
