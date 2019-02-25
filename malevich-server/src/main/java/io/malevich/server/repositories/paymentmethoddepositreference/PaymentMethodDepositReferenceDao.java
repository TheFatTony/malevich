package io.malevich.server.repositories.paymentmethoddepositreference;

import io.malevich.server.domain.PaymentMethodDepositReferenceEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentMethodDepositReferenceDao extends JpaRepository<PaymentMethodDepositReferenceEntity, Long> {

    List<PaymentMethodDepositReferenceEntity> findAll();

    List<PaymentMethodDepositReferenceEntity> findByParticipant_Id(Long participantId);

    List<PaymentMethodDepositReferenceEntity> findByParticipant_IdAndType_Id(Long participantId, String typeId);

    Optional<PaymentMethodDepositReferenceEntity> findByReference(String reference);

}
