package io.malevich.server.services.paymentmethodtype;

import io.malevich.server.dao.paymentmethodtype.PaymentMethodTypeDao;
import io.malevich.server.entity.PaymentMethodTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
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
