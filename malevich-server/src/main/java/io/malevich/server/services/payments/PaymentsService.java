package io.malevich.server.services.payments;

import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.fabric.model.BalanceHistoryAsset;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public interface PaymentsService {

    List<PaymentsEntity> findOwnPayments();

    List<BalanceHistoryAsset> findOwnPayments1();

    List<PaymentsEntity> findAllByParticipant(Long participantId);

    void insert(PaymentsEntity paymentsEntity);

    void insertAdmin(PaymentsEntity paymentsEntity);

    PaymentsEntity getPayments(Long id);

    ResponseEntity<byte[]> createFop(PaymentsEntity entity);

    PaymentsEntity save(PaymentsEntity paymentEntity);
}

