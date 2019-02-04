package io.malevich.server.services.paymentmethodcard;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodCardEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethodaccount.PaymentMethodAccountService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentMethodCardServiceImpl implements PaymentMethodCardService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodCardEntity> findAll() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return paymentMethodDao.findByParticipant_IdAndType_Id(participantEntity.getId(), paymentMethodTypeService.getCardType().getId())
                .stream().map(m -> (PaymentMethodCardEntity) m).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentMethodCardEntity save(PaymentMethodCardEntity paymentMethod) {
        ParticipantEntity participantEntity = participantService.getCurrent();
        paymentMethod.setParticipant(participantEntity);
        paymentMethod.setType(paymentMethodTypeService.getCardType());
        return paymentMethodDao.save(paymentMethod);
    }
}

