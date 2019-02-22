package io.malevich.server.services.paymentmethoddepositreference;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodDepositReferenceEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.repositories.paymentmethoddepositreference.PaymentMethodDepositReferenceDao;
import io.malevich.server.rest.resources.ParticipantResource;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PaymentMethodDepositReferenceServiceImpl implements PaymentMethodDepositReferenceService {

    @Autowired
    private PaymentMethodDepositReferenceDao paymentMethodDao;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;


    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodDepositReferenceEntity> findAll() {
        return findAllStream().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentMethodDepositReferenceEntity getOrCreate() {

        PaymentMethodDepositReferenceEntity paymentMethod = findAllStream().findFirst().orElse(null);

        if (paymentMethod != null)
            return paymentMethod;

        ParticipantEntity participantEntity = participantService.getCurrent();
        paymentMethod = new PaymentMethodDepositReferenceEntity();
        paymentMethod.setParticipant(participantEntity);
        paymentMethod.setType(paymentMethodTypeService.getDepositReferenceType());
        paymentMethod.setReference(generateNewReference());

        return save(paymentMethod);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentMethodDepositReferenceEntity findByReference(String reference) {
        return paymentMethodDao.findByReference(reference).orElse(null);
    }

    private String generateNewReference() {
        final Integer length = 16;
        char[] randomString = RandomStringUtils.randomAlphanumeric(length).toUpperCase().toCharArray();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(randomString[i]);

            if (i % 4 == 3)
                sb.append(' ');
        }

        return sb.toString();
    }

    private PaymentMethodDepositReferenceEntity save(PaymentMethodDepositReferenceEntity entity) {
        return paymentMethodDao.save(entity);
    }

    private Stream<PaymentMethodDepositReferenceEntity> findAllStream() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return paymentMethodDao.findByParticipant_IdAndType_Id(
                participantEntity.getId(),
                paymentMethodTypeService.getDepositReferenceType().getId())
                .stream();
    }
}
