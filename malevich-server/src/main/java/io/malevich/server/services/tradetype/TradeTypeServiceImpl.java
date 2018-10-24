package io.malevich.server.services.tradetype;

import io.malevich.server.dao.tradetype.TradeTypeDao;
import io.malevich.server.entity.TradeTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class TradeTypeServiceImpl implements TradeTypeService {

  @Autowired
  private TradeTypeDao tradeTypeDao;


  @Override
  @Transactional(readOnly = true)
  public List<TradeTypeEntity> findAll() {
        return this.tradeTypeDao.findAll();
  }

  @Override
  public Optional<TradeTypeEntity> findById(String id) {
    return tradeTypeDao.findById(id);
  }

}
