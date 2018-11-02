package io.malevich.server.services.paymentmethod;

import io.malevich.server.repositories.paymentmethod.PaymentMethodDao;
import io.malevich.server.domain.PaymentMethodEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;


    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodEntity> findAll() {
        return this.paymentMethodDao.findAll();
    }

}
