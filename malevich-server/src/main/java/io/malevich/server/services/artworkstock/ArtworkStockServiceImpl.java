package io.malevich.server.services.artworkstock;

import io.malevich.server.dao.artworkstock.ArtworkStockDao;
import io.malevich.server.entity.ArtworkStockEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class ArtworkStockServiceImpl implements ArtworkStockService {

  @Autowired
  private ArtworkStockDao artworkStockDao;


  @Override
  @Transactional(readOnly = true)
  public List<ArtworkStockEntity> findAll() {
        return this.artworkStockDao.findAll();
  }

}
