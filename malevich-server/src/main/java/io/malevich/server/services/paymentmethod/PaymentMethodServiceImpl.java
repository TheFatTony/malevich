package io.malevich.server.services.paymentmethod;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodEntity> findAll() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return paymentMethodDao.findByParticipant_Id(participantEntity.getId());
    }

    @Override
    @Transactional
    public PaymentMethodEntity save(PaymentMethodEntity paymentMethod) {
        ParticipantEntity participantEntity = participantService.getCurrent();
        paymentMethod.setParticipant(participantEntity);
        return paymentMethodDao.save(paymentMethod);
    }

    @Override
    public PaymentMethodEntity generateBtc() {
        ParticipantEntity participantEntity = participantService.getCurrent();

        Optional<PaymentMethodEntity> existing = findAll().stream().filter(m -> paymentMethodTypeService.getBitcoinType().equals(m.getType())).findFirst();

        PaymentMethodBitcoinEntity address;

        if(existing.isPresent()){
            address = (PaymentMethodBitcoinEntity)existing.get();
        } else{
            address = new PaymentMethodBitcoinEntity();
            address.setType(paymentMethodTypeService.getBitcoinType());
            address.setParticipant(participantEntity);
        }

        address.setBtcAddress(UUID.randomUUID().toString());

        return paymentMethodDao.save(address);
    }

}
