package io.malevich.server.services.tradetype;

import io.malevich.server.entity.TradeTypeEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface TradeTypeService {

  List<TradeTypeEntity> findAll();

}
