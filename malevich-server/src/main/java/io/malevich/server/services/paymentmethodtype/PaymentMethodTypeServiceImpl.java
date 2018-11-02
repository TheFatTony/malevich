package io.malevich.server.services.paymentmethodtype;

import io.malevich.server.repositories.paymentmethodtype.PaymentMethodTypeDao;
import io.malevich.server.domain.PaymentMethodTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PaymentMethodTypeServiceImpl implements PaymentMethodTypeService {

    @Autowired
    private PaymentMethodTypeDao paymentMethodTypeDao;


    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodTypeEntity> findAll() {
        return this.paymentMethodTypeDao.findAll();
    }

}
