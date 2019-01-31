package io.malevich.server.services.paymentmethodbitcoin;

import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentMethodBitcoinEntity;
import io.malevich.server.domain.PaymentMethodEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.paymentmethodbitcoin.PaymentMethodBitcoinService;
import io.malevich.server.services.paymentmethodtype.PaymentMethodTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentMethodBitcoinServiceImpl implements PaymentMethodBitcoinService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentMethodTypeService paymentMethodTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodBitcoinEntity> findAll() {
        ParticipantEntity participantEntity = participantService.getCurrent();
        return paymentMethodDao.findByParticipant_IdAndType_Id(participantEntity.getId(), paymentMethodTypeService.getBitcoinType().getId())
                .stream().map(m -> (PaymentMethodBitcoinEntity) m).collect(Collectors.toList());
    }

    @Override
    public PaymentMethodBitcoinEntity generateBtc() {
        ParticipantEntity participantEntity = participantService.getCurrent();

        Optional<PaymentMethodBitcoinEntity> existing = findAll().stream().filter(m -> paymentMethodTypeService.getBitcoinType().equals(m.getType())).findFirst();

        PaymentMethodBitcoinEntity address;

        if (existing.isPresent()) {
            address = existing.get();
        } else {
            address = new PaymentMethodBitcoinEntity();
            address.setType(paymentMethodTypeService.getBitcoinType());
            address.setParticipant(participantEntity);
        }

        address.setBtcAddress(UUID.randomUUID().toString());

        return paymentMethodDao.save(address);
    }


}
