package io.malevich.server.services.tradetype;

import io.malevich.server.domain.TradeTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface TradeTypeService {

    List<TradeTypeEntity> findAll();

    TradeTypeEntity getGtc();
}
