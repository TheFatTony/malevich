package io.malevich.server.services.paymenttype;

import io.malevich.server.domain.PaymentTypeEntity;
import io.malevich.server.repositories.paymenttype.PaymentTypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final Map<String, PaymentTypeEntity> values;

    @Autowired
    private PaymentTypeDao paymentTypeDao;

    @Autowired
    public PaymentTypeServiceImpl(PaymentTypeDao paymentTypeDao) {
        this.paymentTypeDao = paymentTypeDao;

        values = paymentTypeDao.findAll().stream().collect(Collectors.toMap(i -> i.getId(), i -> i));
    }
    
    
    @Override
    public List<PaymentTypeEntity> findAll() {
        return paymentTypeDao.findAll();
    }

    @Override
    public PaymentTypeEntity getPaymentType() {
        return values.get("IN");
    }

    @Override
    public PaymentTypeEntity getWithdrawalType() {
        return values.get("OUT");
    }

    @Override
    public PaymentTypeEntity invert(PaymentTypeEntity paymentTypeEntity){
        if(paymentTypeEntity == null)
            return null;

        if("IN".equals(paymentTypeEntity.getId()))
            return getWithdrawalType();

        if("OUT".equals(paymentTypeEntity.getId()))
            return getPaymentType();

        return null;
    }

    @Override
    public Map<String, PaymentTypeEntity> getValues() {
        return values;
    }
}
