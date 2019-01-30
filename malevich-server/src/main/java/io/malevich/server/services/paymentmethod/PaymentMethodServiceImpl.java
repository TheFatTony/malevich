package io.malevich.server.services.paymentmethod;

import io.malevich.server.domain.PaymentMethodCardEntity;
import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.domain.PaymentMethodEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;


    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodEntity> findAll() {
        return paymentMethodDao.findAll();
    }

    @Override
    @Transactional
    public PaymentMethodCardEntity saveCard(PaymentMethodCardEntity paymentMethod) {
        return paymentMethodDao.save(paymentMethod);
    }

}
