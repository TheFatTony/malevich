package io.malevich.server.services.paymentmethodtype;

import io.malevich.server.repositories.paymentmethodtype.PaymentMethodTypeDao;
import io.malevich.server.domain.PaymentMethodTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class PaymentMethodTypeServiceImpl implements PaymentMethodTypeService {

    private final Map<String, PaymentMethodTypeEntity> values;

    private PaymentMethodTypeDao paymentMethodTypeDao;

    @Autowired
    public PaymentMethodTypeServiceImpl(PaymentMethodTypeDao paymentMethodTypeDao) {
        this.paymentMethodTypeDao = paymentMethodTypeDao;

        values = paymentMethodTypeDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }


    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodTypeEntity> findAll() {
        return this.paymentMethodTypeDao.findAll();
    }

    @Override
    public PaymentMethodTypeEntity getAccountType(){return values.get("ACC");}

    @Override
    public PaymentMethodTypeEntity getCardType(){return values.get("CRD");}

    @Override
    public PaymentMethodTypeEntity getBitcoinType(){return values.get("BTC");}

    @Override
    public PaymentMethodTypeEntity getDepositReferenceType(){return values.get("REF");}

}
