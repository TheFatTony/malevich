package io.malevich.server.services.paymentmethodaccount;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentMethodAccountEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.revolut.model.CounterpartyModel;
import io.malevich.server.revolut.services.counterparty.CounterpartyBankService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethodaccount.PaymentMethodAccountService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentMethodAccountServiceImpl implements PaymentMethodAccountService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Autowired
    private CounterpartyBankService counterpartyBankService;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodAccountEntity> findAll() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return paymentMethodDao.findByParticipant_IdAndType_Id(participantEntity.getId(), paymentMethodTypeService.getAccountType().getId())
                .stream().map(m -> (PaymentMethodAccountEntity) m).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentMethodAccountEntity save(PaymentMethodAccountEntity paymentMethod) {
        ParticipantEntity participantEntity = participantService.getCurrent();
        paymentMethod.setParticipant(participantEntity);
        paymentMethod.setType(paymentMethodTypeService.getAccountType());

        CounterpartyModel counterpartyModel = counterpartyBankService.create(paymentMethod);
        paymentMethod.setRevolutCounterpartyId(counterpartyModel.getId());
        paymentMethod.setRevolutAccountId(
                counterpartyModel
                        .getAccounts()
                        .stream()
                        .filter(a -> "EUR".equals(a.getCurrency()))
                        .findFirst()
                        .get()
                        .getId()
        );

        return paymentMethodDao.save(paymentMethod);
    }
}
